package com.taimeitech.framework.common.datasource.meta.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.taimeitech.framework.common.CopyOnWriteMap;
import com.taimeitech.framework.common.datasource.meta.DataSourceMetaInfo;
import com.taimeitech.framework.common.datasource.meta.ReadWriteGroup;
import com.taimeitech.framework.common.datasource.meta.SmoothBalancer;
import com.taimeitech.framework.common.datasource.meta.ZNode;
import com.taimeitech.framework.util.SerializeUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static com.taimeitech.framework.common.datasource.meta.impl.ZKDbMetaInfoProvider.VALUE_CHARSET;

public class RWGroupProvider {

	private static Logger logger = LoggerFactory.getLogger(RWGroupProvider.class);

	private PathChildrenCache pathChildrenCache;

	// rw-group id -> rw-group
	private CopyOnWriteMap<String, ZNode<ReadWriteGroup>> rwGroupMap = new CopyOnWriteMap<>();
	private SmoothBalancer<String, ReadWriteGroup> groupBalancer = new SmoothBalancer<>();

	private String rootPath;
	private CuratorFramework _zkClient;

	public RWGroupProvider(CuratorFramework client, String rootPath) {
		this.pathChildrenCache = new PathChildrenCache(client, rootPath, true);
		_zkClient = client;
		this.rootPath = rootPath;
	}

	public void start() throws Exception {
		pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
			@Override
			public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
				String path = null;
				switch (event.getType()) {
					case CONNECTION_SUSPENDED:
					case CONNECTION_RECONNECTED:
					case CONNECTION_LOST:
						break;
					case CHILD_ADDED:
						path = event.getData().getPath();
						ZNode<ReadWriteGroup> addNode = buildReadWriteGroup(path);
						rwGroupMap.put(addNode.getData().getId(), addNode);
						groupBalancer.add(addNode.getData().getId(), addNode.getData(), addNode.getData().getWeight());
						break;
					case CHILD_UPDATED:
						path = event.getData().getPath();
						ZNode<ReadWriteGroup> updateNode = buildReadWriteGroup(path);
						rwGroupMap.put(updateNode.getData().getId(), updateNode);
						groupBalancer.update(updateNode.getData().getId(), updateNode.getData(), updateNode.getData().getWeight());
						break;
					case CHILD_REMOVED:
						path = event.getData().getPath();
						String rwId = ZKPaths.getNodeFromPath(path);
						rwGroupMap.remove(rwId);
						groupBalancer.remove(rwId);
						break;
					case INITIALIZED:
						logger.info("SIGNAL ZK RWGroup INITIALIZED");
						break;
				}
				logger.info("SIGNAL ZK {},{}", event.getType(), event.getData() == null ? "" : event.getData().getPath());
			}
		});
		pathChildrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
		initRWGroup();
		logger.info("started");
	}

	/**
	 * /RDS/datasource/{datasource-name}/ (need to make sure this exists) in devops
	 * ---------------------------------rw-1/writer
	 * -------------------------------------/reader, a list
	 * ---------------------------------rw-2/writer
	 * -------------------------------------/reader, a list
	 * ....
	 *
	 * @return
	 */
	private List<DataSourceMetaInfo> initRWGroup() {
		List<ChildData> currentData = pathChildrenCache.getCurrentData();
		List<DataSourceMetaInfo> toInit = new ArrayList<>();
		try {
			if (CollectionUtils.isNotEmpty(currentData)) {
				for (ChildData rwNode : currentData) {
					ZNode<ReadWriteGroup> groupNode = buildReadWriteGroup(rwNode.getPath());
					rwGroupMap.put(groupNode.getData().getId(), groupNode);
					groupBalancer.add(groupNode.getData().getId(), groupNode.getData(), groupNode.getData().getWeight());
				}
			} else {
				logger.warn("no rw group found for {}", ZKPaths.getNodeFromPath(rootPath));
			}
		} catch (Exception e) {
			logger.error("error loading datasource info from zk", e);
			throw new RuntimeException(e);
		}
		return toInit;
	}

	public ZNode<ReadWriteGroup> buildReadWriteGroup(String groupPath) throws Exception {
		String rwId = ZKPaths.getNodeFromPath(groupPath);
		ReadWriteGroup readWriteGroup = new ReadWriteGroup(rwId);
		Stat stat = _zkClient.checkExists().forPath(groupPath);
		ZNode<ReadWriteGroup> groupNode = new ZNode<>(stat, readWriteGroup);
		if (_zkClient.checkExists().forPath(groupPath + "/" + "writer") != null) {
			byte[] writerBytes = _zkClient.getData().forPath(groupPath + "/" + "writer");
			if (writerBytes != null) {
				String dataSourceMetaInfo = new String(writerBytes, VALUE_CHARSET);
				DataSourceMetaInfo dsInfo = SerializeUtils.fromJson(dataSourceMetaInfo, DataSourceMetaInfo.class);
				Assert.isTrue(dsInfo != null, "dataSourceMetaInfo de-Serialize fail" + dataSourceMetaInfo);
//							toInit.add(dsInfo);
				readWriteGroup.setWriter(dsInfo);
				logger.info("get rw group {}, writer , {}", rwId, dataSourceMetaInfo);
			} else {
				logger.warn("no writer found for group {}", rwId);
//							throw new RuntimeException("no writer found for group " + rwId);
			}
		} else {
			logger.warn("no writer found for group {}", rwId);
//						throw new RuntimeException("no writer found for group " + rwId);
		}
		if (_zkClient.checkExists().forPath(groupPath + "/" + "reader") != null) {
			byte[] readersBytes = _zkClient.getData().forPath(groupPath + "/" + "reader");
			if (readersBytes != null) {
				String readersInfo = new String(readersBytes, VALUE_CHARSET);
				List<DataSourceMetaInfo> dsInfos = SerializeUtils.fromJson(readersInfo, new TypeReference<List<DataSourceMetaInfo>>() {
				});
				Assert.isTrue(dsInfos != null, "dataSourceMetaInfo de-Serialize fail" + readersInfo);
				for (DataSourceMetaInfo readInfo : dsInfos) {
//								toInit.add(readInfo);
					readWriteGroup.addReader(readInfo);
				}
				logger.info("get rw group {}, readers , {}", rwId, readersInfo);
			} else {
				logger.warn("no reader found for group {}", rwId);
			}
		} else {
			logger.warn("no reader found for group {}", rwId);
		}
		return groupNode;
	}

	public CopyOnWriteMap<String, ZNode<ReadWriteGroup>> getRwGroupMap() {
		return rwGroupMap;
	}

	public SmoothBalancer<String, ReadWriteGroup> getGroupBalancer() {
		return groupBalancer;
	}
}
