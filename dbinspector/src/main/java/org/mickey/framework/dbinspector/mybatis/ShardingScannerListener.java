package org.mickey.framework.dbinspector.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.database.Table;
import org.mickey.framework.dbinspector.DbInspectListener;
import org.mickey.framework.dbinspector.mybatis.sharding.table.po.ShardingMapping;

import javax.sql.DataSource;
import java.util.List;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
public class ShardingScannerListener implements DbInspectListener {
    @Override
    public void beforeScan(List<String> packagesToScan) {
        packagesToScan.add(ShardingMapping.class.getPackage().getName());
    }

    @Override
    public void afterScan(List<Table> tables) {

    }

    @Override
    public void beforeInspectOnDataSource(DataSource dataSource, List<Table> jpaTables) throws Exception {

    }

    @Override
    public void afterInspectOnDataSource(DataSource dataSource, List<Table> jpaTables) throws Exception {

    }
}
