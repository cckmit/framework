package org.mickey.framework.dbinspector;

import org.mickey.framework.common.database.Table;

import javax.sql.DataSource;
import java.util.List;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
public interface DbInspectListener {
    /**
     * 扫描包之前运行的方法
     * @param packagesToScan
     */
    void beforeScan(List<String> packagesToScan);

    /**
     * 扫描包之后运行的方法
     * @param tables
     */
    void afterScan(List<Table> tables);

    /**
     * 扫描执行前运行的方法
     * 注意：
     * 如果是集群部署模式下，由于使用了分布式锁，只有一个实例会执行此方法
     * 如果此方法抛出异常，不会继续
     * 如果使用了 dataSource 的 getConnection 方法，确保执行完成后，关闭连接，防止出现连接池泄露
     *
     * @param dataSource
     * @param jpaTables
     * @throws Exception
     */
    void beforeInspectOnDataSource(DataSource dataSource, List<Table> jpaTables) throws Exception;

    /**
     * 扫面完成且执行完数据库更新后执行的方法
     * 注意：
     * 如果是集群部署模式下，由于使用了分布式锁，只有一个实例会执行此方法
     * 此方法如果抛出异常，在 async=false 情况下，会阻止程序正常启动
     * 如果使用了 dataSource 的 getConnection 方法，确保执行完成后，关闭链接，防止出现连接池泄露
     *
     * @param dataSource
     * @param jpaTables
     * @throws Exception
     */
    void afterInspectOnDataSource(DataSource dataSource, List<Table> jpaTables) throws Exception;
}
