package com.taimeitech.framework.common.cache;

import java.util.Map;

public interface CacheFacade {
	void setMap(String cacheKey, Map<String, String> shardingMap);

	String getField(String cacheKey, String shardingTableName);
}
