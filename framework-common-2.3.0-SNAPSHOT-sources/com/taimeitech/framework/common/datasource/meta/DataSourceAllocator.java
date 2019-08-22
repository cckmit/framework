package com.taimeitech.framework.common.datasource.meta;

import com.taimeitech.framework.common.datasource.readwriteisolate.ReadWriteStrategy;

import java.util.List;

/**
 * @author thomason
 * @version 1.0
 * @since 2017/12/7 上午9:58
 */
public interface DataSourceAllocator {
	DataSourceMetaInfo allocate(String routingKey, ReadWriteStrategy readWriteStrategy, List<DataSourceMetaInfo> selectList);
}
