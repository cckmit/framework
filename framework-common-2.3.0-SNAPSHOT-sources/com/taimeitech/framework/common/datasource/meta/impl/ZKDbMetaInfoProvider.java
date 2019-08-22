package com.taimeitech.framework.common.datasource.meta.impl;

import com.taimeitech.framework.common.CopyOnWriteMap;
import com.taimeitech.framework.common.datasource.meta.*;
import com.taimeitech.framework.common.datasource.readwriteisolate.ReadWriteStrategy;
import com.taimeitech.framework.util.SerializeUtils;
import com.taimeitech.framework.util.StringUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


//todo , lock load balancer on rw group
public class ZKDbMetaInfoProvider implements DbMetaInfoProvider, InitializingBean, DisposableBean {

	public static final String VALUE_CHARSET = "UTF-8";
	private static Logger logger = LoggerFactory.getLogger(ZKDbMetaInfoProvider.class);


	private RWGroupProvider rwGroupProvider;

	private RoutingResultProvider routingResultProvider;
	//数据库名称
	private String databaseName;
	//
	private String routingKey = "TM-Header-TenantId";
	//zkClient
	private CuratorFramework _zkClient;
	//锁
	private Lock lock = new ReentrantLock();

	public ZKDbMetaInfoProvider(String databaseName, String routingKey, CuratorFramework _zkClient) {
		rwGroupProvider = new RWGroupProvider(_zkClient, DATASOURCE_PATH + "/datasource/" + databaseName);
		routingResultProvider = new RoutingResultProvider(_zkClient, DATASOURCE_PATH + "/routingRule/" + databaseName + "/" + routingKey);
		this.databaseName = databaseName;
		this.routingKey = routingKey;
		this._zkClient = _zkClient;
	}


	@Override
	public DataSourceMetaInfo getDatabaseMeta(String routingValue, ReadWriteStrategy strategy) {
		RoutingResult routingResult = routingMap().get(routingValue) == null ? allocateNewGroup(routingValue) : routingMap().get(routingValue).getData();
		ReadWriteGroup rwGroup = null;
		if (routingResult != null) {
			String groupId = routingResult.getGroupId();
			ZNode<ReadWriteGroup> rwGroupNode = rwGroupMap().get(groupId);
			if (rwGroupNode == null) {
				//it's a new group
				rwGroup = doReadWriteGroup(groupId);
				if (rwGroup == null) {
					logger.warn("cannot find rw group {}", groupId);
					return null;
				}
			} else {
				rwGroup = rwGroupNode.getData();
			}
		} else {
			logger.warn("unable to allocate routing result {}", routingValue);
			return null;
		}
		return strategy == ReadWriteStrategy.READ ? rwGroup.nextReader() : rwGroup.getWriter();
	}

	@Override
	public List<DataSourceMetaInfo> getAllDataSourceMetaInfo() {
		List<DataSourceMetaInfo> result = new ArrayList<>();
		CopyOnWriteMap<String, ZNode<ReadWriteGroup>> rwGroupMap = rwGroupProvider.getRwGroupMap();
		for (ZNode<ReadWriteGroup> value : rwGroupMap.values()) {
			ReadWriteGroup group = value.getData();
			if (group.getWriter() != null) {
				result.add(group.getWriter());
			}
			if (!CollectionUtils.isEmpty(group.getReaderBalancer().getMap())) {
				for (Weighted<DataSourceMetaInfo> weighted : group.getReaderBalancer().getMap().values()) {
					if (weighted.getItem() != null) {
						result.add(weighted.getItem());
					}
				}
			}
		}
		return result;
	}

	/**
	 * 自动分配一个组
	 *
	 * @param routingValue
	 */
	private RoutingResult allocateNewGroup(String routingValue) {
		//try to check again, in case we have a network issue
		//basically a double check locking using zk as the lock
		//check if routing exit
		//get lock, check again, if still not exit, allocate, release lock, work 99.999% perhaps
		String routingPath = routingParentPath() + "/" + routingValue;
		try {
			lock.lock();
			Stat stat = _zkClient.checkExists().forPath(routingPath);
			RoutingResult routingResult = null;
			if (stat == null) {
				InterProcessMutex mutex = new InterProcessMutex(_zkClient, DATASOURCE_PATH + "/lock/routing/" + routingKey + "/" + routingValue);
				int counter = 3; //retry 3 times
				while (true) {
					boolean acquired = mutex.acquire(60, TimeUnit.SECONDS);
					try {
						if (acquired) {
							//check if exits, if not set
							stat = _zkClient.checkExists().forPath(routingPath);
							if (stat == null) {
								ReadWriteGroup next = groupBalancer().next();
								Assert.isTrue(next != null, "no group available");
								routingResult = new RoutingResult(next.getId(), AllocateType.AUTO);
								_zkClient.create().withMode(CreateMode.PERSISTENT).forPath(routingPath, SerializeUtils.toJson(routingResult).getBytes(VALUE_CHARSET));
								stat = _zkClient.checkExists().forPath(routingPath);
								routingMap().put(routingValue, new ZNode<>(stat, routingResult));
								return routingResult;
							} else {
								stat = new Stat();
								byte[] bytes = _zkClient.getData().storingStatIn(stat).forPath(routingPath);
								routingResult = SerializeUtils.fromJson(new String(bytes, VALUE_CHARSET), RoutingResult.class);
								if (routingResult == null) {
									stat = null;
									logger.warn("unable to de-serialize {}", new String(bytes, VALUE_CHARSET));
									return null;
								}
							}
						} else {
							//didn't get lock
							//check if exits, if not , check counter, if != 0, continue
							stat = _zkClient.checkExists().forPath(routingPath);
							if (stat == null) {
								if (counter == 0) {
									logger.warn("unable to allocate rw group after retry for {}", routingValue);
									return null;
								} else {
									counter--;
									logger.warn("routing counter {} for value {}", counter, routingValue);
									continue;
								}
							} else {
								stat = new Stat();
								byte[] bytes = _zkClient.getData().storingStatIn(stat).forPath(routingPath);
								routingResult = SerializeUtils.fromJson(new String(bytes, VALUE_CHARSET), RoutingResult.class);
								if (routingResult == null) {
									stat = null;
									logger.warn("unable to de-serialize {}", new String(bytes, VALUE_CHARSET));
									return null;
								}
							}
						}

					} finally {
						if (acquired) {
							mutex.release();
						}
					}
				}
			} else {
				stat = new Stat();
				byte[] bytes = _zkClient.getData().storingStatIn(stat).forPath(routingPath);
				routingResult = SerializeUtils.fromJson(new String(bytes, VALUE_CHARSET), RoutingResult.class);
				if (routingResult == null) {
					logger.warn("unable to de-serialize {}", new String(bytes, VALUE_CHARSET));
					return null;
				}
			}
			Assert.notNull(routingResult, "routingResult cannot be null");
			Assert.notNull(stat, "stat cannot be null");
			routingMap().put(routingValue, new ZNode<>(stat, routingResult));//update result
			return routingResult;
		} catch (Exception e) {
			logger.error("error allocate", e);
			throw new RuntimeException(e);
		} finally {
			lock.unlock();
		}
	}

	private ReadWriteGroup doReadWriteGroup(String groupId) {
		if (!rwGroupMap().containsKey(groupId)) {
			//we have a network delay, another java process allocate this value to a new group, we do a force read
			String groupPath = rwGroupParentPath() + "/" + groupId;
			try {
				ZNode<ReadWriteGroup> node = rwGroupProvider.buildReadWriteGroup(groupPath);
				if (node == null) return null;
				if (rwGroupMap().containsKey(groupId)) {
					return rwGroupMap().get(groupId).getData();
				} else {
					rwGroupMap().put(groupId, node);
					return node.getData();
				}
			} catch (Exception e) {
				logger.error("", e);
				return null;
			}
		} else {
			return rwGroupMap().get(groupId).getData();
		}
	}

	private String routingParentPath() {
		return DATASOURCE_PATH + "/routingRule/" + databaseName + "/" + routingKey;
	}

	private String rwGroupParentPath() {
		return DATASOURCE_PATH + "/datasource/" + databaseName;
	}

	private Map<String, ZNode<ReadWriteGroup>> rwGroupMap() {
		return rwGroupProvider.getRwGroupMap();
	}

	private Map<String, ZNode<ZKDbMetaInfoProvider.RoutingResult>> routingMap() {
		return routingResultProvider.getRoutingMap();
	}


	private SmoothBalancer<String, ReadWriteGroup> groupBalancer() {
		return rwGroupProvider.getGroupBalancer();
	}


	public String getDatabaseName() {
		return databaseName;
	}

	@Override
	public void destroy() throws Exception {

	}


	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.isTrue(_zkClient != null, "zk client cannot be null");
		Assert.isTrue(StringUtil.isNotBlank(databaseName), "org.mickey.framework.org.mickey.framework.dbinspector.common.database name cannot be empty");
		Assert.isTrue(StringUtil.isNotBlank(routingKey), "routing key name cannot be empty");

		Assert.isTrue(_zkClient.getState() == CuratorFrameworkState.STARTED, "zk client must be started");
		rwGroupProvider.start();
		routingResultProvider.start();
	}

	public static class RoutingResult {
		private String groupId;
		private AllocateType allocateType;

		public RoutingResult() {
		}

		public RoutingResult(String groupId, AllocateType allocateType) {
			this.groupId = groupId;
			this.allocateType = allocateType;
		}

		public String getGroupId() {
			return groupId;
		}

		public void setGroupId(String groupId) {
			this.groupId = groupId;
		}

		public AllocateType getAllocateType() {
			return allocateType;
		}

		public void setAllocateType(AllocateType allocateType) {
			this.allocateType = allocateType;
		}
	}


}
