package com.taimeitech.framework.common.datasource.meta.impl;

import com.taimeitech.framework.common.CopyOnWriteMap;
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

import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.taimeitech.framework.common.datasource.meta.impl.ZKDbMetaInfoProvider.VALUE_CHARSET;

public class RoutingResultProvider {
	private static Logger logger = LoggerFactory.getLogger(RoutingResultProvider.class);

	private PathChildrenCache pathChildrenCache;
	private String rootPath;
	private CuratorFramework _zkClient;

	//本地的路由Map, routing_value -> routing_result
	private CopyOnWriteMap<String, ZNode<ZKDbMetaInfoProvider.RoutingResult>> routingMap = new CopyOnWriteMap<>();

	public RoutingResultProvider(CuratorFramework client, String rootPath) {
		this.pathChildrenCache = new PathChildrenCache(client, rootPath, true);
		this.rootPath = rootPath;
		this._zkClient = client;
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
					case CHILD_UPDATED:
						ZNode<ZKDbMetaInfoProvider.RoutingResult> zNode = buildRoutingResult(event.getData());
						routingMap.put(ZKPaths.getNodeFromPath(event.getData().getPath()),zNode);
						break;
					case CHILD_REMOVED:
						routingMap.remove(ZKPaths.getNodeFromPath(event.getData().getPath()));
						logger.warn("routing data {} removed", ZKPaths.getNodeFromPath(event.getData().getPath()));
						break;
					case INITIALIZED:
						logger.info("SIGNAL ZK routing INITIALIZED");
						break;
				}
				logger.info("SIGNAL ZK {},{}", event.getType(), event.getData() == null ? "" : event.getData().getPath());
			}
		});
		pathChildrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
		loadRoutingRule();
		logger.info("started");
	}


	/**
	 * 加载数据库路由信息配置
	 * 数据库路由信息存储如下
	 * /RDS/routing/{datasource name}/{routing key name}/ (need to make sure this exists) in devops
	 * --------------------------------------------------routing_value_1-> rw group id,auto or manual
	 * routing_value_2, rw group id
	 */
	private void loadRoutingRule() {
		List<ChildData> currentData = pathChildrenCache.getCurrentData();
		if (CollectionUtils.isNotEmpty(currentData)) {
			for (ChildData routingNode : currentData) {
				ZNode<ZKDbMetaInfoProvider.RoutingResult> zNode = buildRoutingResult(routingNode);
				if (zNode != null) {
					routingMap.put(ZKPaths.getNodeFromPath(routingNode.getPath()), zNode);
					logger.info("rw group for {} is {}", ZKPaths.getNodeFromPath(routingNode.getPath()), zNode.getData().getGroupId());
				}
			}
		} else {
			logger.info("no routing info for {}", ZKPaths.getNodeFromPath(rootPath));
		}
	}

	private ZNode<ZKDbMetaInfoProvider.RoutingResult> buildRoutingResult(ChildData childData) {
		ZKDbMetaInfoProvider.RoutingResult routingResult = null;
		Stat stat = childData.getStat();
		byte[] bytes = childData.getData();
		if (bytes != null) {
			try {
				routingResult = SerializeUtils.fromJson(new String(bytes, VALUE_CHARSET), ZKDbMetaInfoProvider.RoutingResult.class);
			} catch (UnsupportedEncodingException e) {
				logger.error("UnsupportedEncodingException", e);
			}
		}
		return routingResult == null ? null : new ZNode<>(stat, routingResult);
	}

	public CopyOnWriteMap<String, ZNode<ZKDbMetaInfoProvider.RoutingResult>> getRoutingMap() {
		return routingMap;
	}
}
