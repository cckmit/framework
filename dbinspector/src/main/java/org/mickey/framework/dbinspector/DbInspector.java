package org.mickey.framework.dbinspector;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mickey.framework.common.datasource.RoutingDataSource;
import org.mickey.framework.common.zookeeper.ZkDistributeLock;
import org.mickey.framework.dbinspector.common.ORMapping;
import org.mickey.framework.dbinspector.common.ThreadPoolExecutor;
import org.mickey.framework.dbinspector.dialect.Dialect;
import org.mickey.framework.dbinspector.dialect.DialectFactory;
import org.mickey.framework.dbinspector.processor.DdlProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Data
public class DbInspector {
    private static final String RESOURCE_PATTERN = "/**/*.class";
    private static final String PACKAGE_INFO_SUFFIX = ".package-info";

    private final Dialect dialect;
    private DdlProcessor ddlProcessor;
    @Autowired
    private DataSource dataSource;
    @Autowired(required = false)
    private ZkDistributeLock zkDistributeLock;
    @Autowired(required = false)
    private List<DbInspectListener> listenerList;
    private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    private boolean enabled = false;
    private boolean async = false;
    private List<String> packagesToScan;

    public DbInspector() {
        this.dialect = DialectFactory.getDialect("mysql");
    }

    @PostConstruct
    public void inspect() {
        if (!enabled) {
            return;
        }
        if (CollectionUtils.isEmpty(packagesToScan)) {
            return;
        }
        List<String> packages = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(packagesToScan)) {
            packages.addAll(packagesToScan);
        }
        if (zkDistributeLock != null) {
            String lockPath = StringUtils.replace(StringUtils.join(packagesToScan, ","), ",", "_");
            zkDistributeLock.accept(lockPath, p -> doInspect(packages));
        } else {
            doInspect(packages);
        }
    }

    public void doInspect(List<String> packages) {
        List<org.mickey.framework.common.database.Table> tables = scanJPATables(packages);
        if (CollectionUtils.isEmpty(tables)){return;}
        if (dataSource instanceof RoutingDataSource) {
            List<DataSource> dataSources = ((RoutingDataSource) dataSource).getAllDataSource();
            if (CollectionUtils.isEmpty(dataSources)) {return;}
            if (async) {
                dataSources.forEach(ds -> CompletableFuture.runAsync(() -> {
                    try {
                        executeOnOneDataSource(tables, dataSource);
                    } catch (Throwable e) {
                        log.error(e.getMessage(), e);
                    }
                }));
            } else {
                try {
                    ThreadPoolExecutor dataSourceExecutor = ThreadPoolExecutor.newThreadPool(dataSources.size());
                    try {
                        CompletableFuture.allOf(dataSources.stream().map(ds -> CompletableFuture.runAsync(() -> {
                            try {
                                executeOnOneDataSource(tables, ds);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        },dataSourceExecutor)).toArray(CompletableFuture[]::new)).get();
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                    dataSourceExecutor.destroy();
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        else {
            if (async) {
                CompletableFuture.runAsync(() -> {
                    try {
                        executeOnOneDataSource(tables, dataSource);
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                });
            } else {
                try {
                    executeOnOneDataSource(tables, dataSource);
                } catch (Throwable e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
    }

    private List<org.mickey.framework.common.database.Table> scanJPATables(List<String> packages) {
        if (CollectionUtils.isNotEmpty(listenerList)) {
            for (DbInspectListener listener : listenerList) {
                listener.beforeScan(packages);
            }
        }
        Set<String> classNames = scanPackages(packages);
        List<org.mickey.framework.common.database.Table> jpaTables = new ArrayList<>();
        for (String className : classNames) {
            try {
                Class<?> clazz = Class.forName(className);
                org.mickey.framework.common.database.Table jpaTable = ORMapping.get(clazz);
                if (jpaTable == null) {
                    continue;
                }
                jpaTables.add(jpaTable);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        if (CollectionUtils.isNotEmpty(listenerList)) {
            for (DbInspectListener listener : listenerList) {
                listener.afterScan(jpaTables);
            }
        }
        return jpaTables;
    }

    private void executeOnOneDataSource(List<org.mickey.framework.common.database.Table> jpaTables, DataSource dataSource) throws Exception {
        if (CollectionUtils.isNotEmpty(listenerList)) {
            for (DbInspectListener listener : listenerList) {
                listener.beforeInspectOnDataSource(dataSource, jpaTables);
            }
        }
        int size = jpaTables.size();
        CountDownLatch latch = new CountDownLatch(size);
        List<String> ddlList = new CopyOnWriteArrayList<>();
        ThreadPoolExecutor tableThreadExecutor = ThreadPoolExecutor.newThreadPool(size);
        //按照数据库的表的个数，创建对应个数的线程池
        for (org.mickey.framework.common.database.Table table : jpaTables) {
            tableThreadExecutor.execute(() -> {
                String javaName = table.getJavaName();
                try (Connection connection = dataSource.getConnection()) {
                    connection.setAutoCommit(false);
                    System.out.println(String.format("table : %s , is sharding : %s ;", table.getSqlName(), table.isSharding()));
                    if (table.isSharding()) {
                        for (int i = 1; i <= table.getShardingCount(); i++) {
                            org.mickey.framework.common.database.Table shardingTable = new org.mickey.framework.common.database.Table();
                            shardingTable.setSqlName(table.getSqlName() + "_" + i);
                            shardingTable.setCatalog(table.getCatalog());
                            shardingTable.setSchema(table.getSchema());
                            shardingTable.setComment(table.getComment());
                            shardingTable.setDatabase(table.getDatabase());
                            shardingTable.setJavaName(javaName);
                            shardingTable.setSimpleJavaName(table.getSimpleJavaName());
                            shardingTable.setOwnerSynonymName(table.getOwnerSynonymName());
                            shardingTable.setSharding(table.isSharding());
                            shardingTable.setShardingCount(table.getShardingCount());
                            if (table.getColumns() != null) {
                                shardingTable.setColumns(table.getColumns().stream().map(c -> {
                                    org.mickey.framework.common.database.Column column = new org.mickey.framework.common.database.Column(shardingTable);
                                    column.copyFrom(c);
                                    return column;
                                }).collect(Collectors.toList()));
                            }
                            if (table.getIndices() != null) {
                                shardingTable.setIndices(table.getIndices().stream().map(idx -> {
                                    org.mickey.framework.common.database.Index index = new org.mickey.framework.common.database.Index(table);
                                    index.setTable(shardingTable);
                                    index.setColumnList(idx.getColumnList());
                                    index.setName(idx.getName());
                                    index.setUnique(idx.isUnique());
                                    return index;
                                }).collect(Collectors.toList()));
                            }
                            processOneTable(connection, shardingTable, ddlList);
                        }
                    } else {
                        processOneTable(connection, table, ddlList);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("error process table:" + table.toString(), e);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        //销毁此线程池
        tableThreadExecutor.destroy();

        //找到处理类
        if (CollectionUtils.isNotEmpty(ddlList) && ddlProcessor != null) {
            try (Connection connection = dataSource.getConnection()) {
                ddlProcessor.execute(connection, ddlList);
            }
        }
        if (CollectionUtils.isNotEmpty(listenerList)) {
            for (DbInspectListener listener : listenerList) {
                listener.afterInspectOnDataSource(dataSource, jpaTables);
            }
        }
    }

    private void processOneTable(Connection connection, org.mickey.framework.common.database.Table table, List<String> ddlList) throws Exception {
        DatabaseResolver resolver = DatabaseResolver.getInstance();
        String tableName = table.getSqlName();
        Table dbTable = resolver.getTable(connection, tableName);
        List<org.mickey.framework.common.database.Column> tableColumns = table.getColumns() == null ? Collections.EMPTY_LIST : table.getColumns();
        List<org.mickey.framework.common.database.Index> indexList = table.getIndices() == null ? Collections.EMPTY_LIST : table.getIndices();
        if (dbTable == null) {
            String ddl = dialect.buildCreateTableClause(table);
            ddlList.add(ddl);
            if (CollectionUtils.isNotEmpty(indexList)) {
                indexList.forEach(index -> {
                    String indexClause = dialect.buildIndexClause(index);
                    ddlList.add(indexClause);
                });
            }
        } else {
            List<Column> dbColumns = dbTable.getColumns() == null ? Collections.EMPTY_LIST : dbTable.getColumns();
            tableColumns.forEach(poColumn -> {
                Column dbColumn = dbColumns.stream().filter(r -> r.getSqlName().equals(poColumn.getSqlName())).findAny().orElse(null);
                if (dbColumn == null) {
                    String addColumnClause = dialect.buildAddColumnClause(poColumn);
                    ddlList.add(addColumnClause);
                } else {
                    //todo check is upgradable, compare and upgrade
//					int dbType = dbColumn.getSqlType();
//					int poType = poColumn.getSqlType();
//					System.out.println("tableName:" + tableName);
//					System.out.println("dbColumn:" + dbColumn.getSqlName() + " poColumn:" + poColumn.getSqlName());
//					System.out.println("dbType:" + dbType + " poType:" + poType);
                }
            });
            List<Index> dbIndices = dbTable.getIndices() == null ? Collections.EMPTY_LIST : dbTable.getIndices();
            indexList.forEach(index -> {
                boolean hasIndex = dbIndices.stream().anyMatch(r -> r.getName().equals(index.getName()));
                if (!hasIndex) {
                    ddlList.add(dialect.buildIndexClause(index));
                }
            });
        }
    }

    protected Set<String> scanPackages(List<String> packages) {
        Set<String> classNames = new TreeSet<>();
        if (packages != null) {
            try {
                for (String pkg : packages) {
                    String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                            ClassUtils.convertClassNameToResourcePath(pkg) + RESOURCE_PATTERN;
                    Resource[] resources = this.resourcePatternResolver.getResources(pattern);
                    MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);
                    for (Resource resource : resources) {
                        if (resource.isReadable()) {
                            MetadataReader reader = readerFactory.getMetadataReader(resource);
                            String className = reader.getClassMetadata().getClassName();
                            if (className.endsWith(PACKAGE_INFO_SUFFIX)) {
                                continue;
                            }
                            AnnotationMetadata annotationMetadata = reader.getAnnotationMetadata();
                            boolean hasAnnotation = annotationMetadata.hasAnnotation(javax.persistence.Table.class.getName());
                            if (!hasAnnotation) {
                                continue;
                            }
                            classNames.add(reader.getClassMetadata().getClassName());
                        }
                    }
                }
            } catch (IOException ex) {
                throw new RuntimeException("Failed to scan classpath for unlisted classes", ex);
            }
        }
        return classNames;
    }
}
