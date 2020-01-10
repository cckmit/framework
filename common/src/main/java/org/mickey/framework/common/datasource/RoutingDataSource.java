package org.mickey.framework.common.datasource;


import org.mickey.framework.common.datasource.meta.DataSourceMetaInfo;

import javax.sql.DataSource;
import java.util.List;

/**
 * 数据源路由
 *
 * @author mickey
 * 23/07/2019
 */
public interface RoutingDataSource extends DataSource {
    /**
     * 获取所有的数据源
     * @return List<DataSource>
     */
    List<DataSource> getAllDataSource();

    /**
     * 根据meta info获取数据源
     * @param dataSourceMetaInfo meta info
     * @return 数据源
     */
    DataSource getDataSource(DataSourceMetaInfo dataSourceMetaInfo);

    /**
     * 获取路由值
     * @return 路由字符串
     */
    String getRoutingValue();

    /**
     * 是否读写隔离
     * @return Boolean
     */
    boolean isReadWriteIsolate();
}
