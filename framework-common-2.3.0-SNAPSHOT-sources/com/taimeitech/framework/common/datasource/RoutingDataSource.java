package com.taimeitech.framework.common.datasource;

import com.taimeitech.framework.common.datasource.meta.DataSourceMetaInfo;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author thomason
 * @version 1.0
 * @since 2018/1/23 下午4:15
 */
public interface RoutingDataSource extends DataSource {
	List<DataSource> getAllDataSource();

	DataSource getDataSource(DataSourceMetaInfo databaseMeta);

	String getRoutingValue();

	boolean isReadWriteIsolate();
}
