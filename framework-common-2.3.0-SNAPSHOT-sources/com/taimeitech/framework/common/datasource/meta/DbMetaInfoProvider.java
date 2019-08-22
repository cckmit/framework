package com.taimeitech.framework.common.datasource.meta;


import com.taimeitech.framework.common.datasource.readwriteisolate.ReadWriteStrategy;

import java.util.List;

/**
 * @author Thomason
 * @version 1.0
 * @since 13-2-25 下午3:27
 */

public interface DbMetaInfoProvider {

	String DATASOURCE_PATH = "/RDS";

	/**
	 * 获取数据库信息
	 * 根据当前上下文中读写数据库的标记获取数据源配置
	 *
	 * @param routingValue      路由Key
	 * @param readWriteStrategy 读写类型
	 * @return 数据源元信息
	 */
	DataSourceMetaInfo getDatabaseMeta(String routingValue, ReadWriteStrategy readWriteStrategy);


	/**
	 * 获取所有的数据源信息
	 *
	 * @return
	 */
	List<DataSourceMetaInfo> getAllDataSourceMetaInfo();
}
