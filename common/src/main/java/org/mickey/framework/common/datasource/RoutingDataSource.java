package org.mickey.framework.common.datasource;


import org.mickey.framework.common.datasource.meta.DataSourceMetaInfo;

import javax.sql.DataSource;
import java.util.List;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
public interface RoutingDataSource extends DataSource {
    List<DataSource> getAllDataSource();

    DataSource getDataSource(DataSourceMetaInfo dataSourceMetaInfo);

    String getRoutingValue();

    boolean isReadWriteIsolate();
}
