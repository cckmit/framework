package org.mickey.framework.core.mybatis.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.mickey.framework.common.SpringUtils;
import org.mickey.framework.common.database.Column;
import org.mickey.framework.common.database.Table;
import org.mickey.framework.common.po.CommonPo;
import org.mickey.framework.common.util.BatchUtils;
import org.mickey.framework.common.util.ReflectionUtils;
import org.mickey.framework.core.mybatis.BatchOperationException;
import org.mickey.framework.core.mybatis.BatchType;
import org.mickey.framework.core.mybatis.sharding.ShardingManager;
import org.mickey.framework.core.mybatis.sharding.table.po.IdMapping;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@SuppressWarnings("Duplicates")
@Intercepts(
        {
                @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        }
)
public class SimpleBatchInterceptor extends BaseBatchInterceptor {

    public SimpleBatchInterceptor() {
    }

    public SimpleBatchInterceptor(int batchSize) {
        super(batchSize);
    }

    @Override
    protected int doBatchInsert(Executor executor, List poList, Table table) throws SQLException {
        if (table.isSharding()) {
            Map<String, List> groupedMap = new HashMap<>();
            Set<String> shardingValues = new HashSet<>();
            List<Column> shardingColumns = table.getColumns().stream().filter(Column::isSharding).sorted().collect(Collectors.toList());
            if (CollectionUtils.isEmpty(shardingColumns)) {
                throw new RuntimeException("sharding columns is empty");
            }
            for (Object po : poList) {
                List<String> values = new ArrayList<>();
                shardingColumns.forEach(column -> values.add(String.valueOf(ReflectionUtils.getFieldValue(po, column.getJavaName()))));
                String shardingValue = getShardingValue(values);
                shardingValues.add(shardingValue);
                groupedMap.computeIfAbsent(shardingValue, k -> new ArrayList()).add(po);
            }
            Map<String, String> tableNameMap = getShardingManager().getShardingTableNameByValues(table, shardingValues);

            Map<String, List<String>> reversedMap = new HashMap<>();
            tableNameMap.forEach((k, v) -> {
                reversedMap.computeIfAbsent(v, t -> new ArrayList<>()).add(k);
            });

            int total = 0;
            List<IdMapping> idMappings = new ArrayList<>(poList.size());
            for (Map.Entry<String, List<String>> entry : reversedMap.entrySet()) {
                String tableName = entry.getKey();
                List<String> values = entry.getValue();
                List oneTableBatchList = new ArrayList();
                values.forEach(v -> {
                    List list = groupedMap.get(v);
                    for (Object po : list) {
                        IdMapping idMapping = new IdMapping();
                        idMapping.setShardingTableName(tableName);
                        idMapping.setTableName(table.getSqlName());
                        idMapping.setShardingValue(v);
                        idMapping.setShardingKey(shardingColumns.stream().map(Column::getSqlName).collect(Collectors.joining(",")));
                        idMapping.setId((String) ReflectionUtils.getFieldValue(po, "id"));
                        idMappings.add(idMapping);
                        oneTableBatchList.add(po);
                    }
                });
                total += doOneTableBatchInsert(executor, oneTableBatchList, table, tableName);
            }
            //保存Id映射
            getShardingManager().saveIdMappings(idMappings.toArray(new IdMapping[idMappings.size()]));
            return total;
        } else {
            return doOneTableBatchInsert(executor, poList, table, table.getSqlName());
        }
    }

    private int doOneTableBatchInsert(Executor executor, List poList, Table table, String taleName) throws SQLException {
        StringBuilder builder = new StringBuilder(table.getColumns().size() * 20 + 3);
        String sql = buildInsertSql(taleName, table.getColumns(), builder);

        List<List> batches = BatchUtils.sliceBatch(poList, batchSize);
        int rows = 0;
        long start = System.currentTimeMillis();
        if (log.isDebugEnabled()) {
            log.debug("batch insert log: {} start ", sql);
        }
        Connection conn = executor.getTransaction().getConnection();
        for (List batch : batches) {
            rows = rows + doOneBatchInsert(conn, batch, table.getColumns(), sql);
        }
        if (log.isDebugEnabled()) {
            log.debug("batch insert done log, time :{}, rows {}, sql {}, ", System.currentTimeMillis() - start, rows, sql);
        }
        return rows;
    }

    @Override
    protected int doBatchUpdateSelective(Executor executor, List poList, Table table, Set<String> includeColumns) throws SQLException {
        sortPoList(poList);
        if (table.isSharding()) {
            Map<String, Object> objectMap = new HashMap<>();
            Set<String> idSet = new HashSet<>();
            for (Object po : poList) {
                String id = (String) ReflectionUtils.getFieldValue(po, "id");
                idSet.add(id);
                objectMap.put(id, po);
            }
            Map<String, String> tableNameMap = getShardingManager().getShardingTableNameByIds(table, idSet);
            if (tableNameMap == null || tableNameMap.isEmpty()) {
                throw new RuntimeException("invalid update list,because it's id hasn't sharding");
            }
            Map<String, List<String>> reversedMap = new HashMap<>();
            tableNameMap.forEach((k, v) -> {
                reversedMap.computeIfAbsent(v, t -> new ArrayList<>()).add(k);
            });
            int total = 0;
            for (Map.Entry<String, List<String>> entry : reversedMap.entrySet()) {
                String tableName = entry.getKey();
                List<String> values = entry.getValue();
                List oneTableBatchList = values.stream().map(objectMap::get).collect(Collectors.toList());
                total += doOneTableUpdateSelective(executor, oneTableBatchList, table, tableName, includeColumns);
            }
            return total;
        } else {
            return doOneTableUpdateSelective(executor, poList, table, table.getSqlName(), includeColumns);
        }
    }

    private void sortPoList(List poList) {
        if (poList != null) {
            poList.sort((o1, o2) -> {
                if (o1 instanceof CommonPo && o2 instanceof CommonPo) {
                    return ((CommonPo) o1).getId().compareTo(((CommonPo) o2).getId());
                } else {
                    return o1.hashCode() - o2.hashCode();
                }
            });
        }
    }

    @Override
    protected int doBatchUpdate(Executor executor, List poList, Table table, Set<String> columns) throws SQLException {
        sortPoList(poList);
        if (table.isSharding()) {
            Map<String, Object> objectMap = new HashMap<>();
            Set<String> idSet = new HashSet<>();
            for (Object po : poList) {
                String id = (String) ReflectionUtils.getFieldValue(po, "id");
                idSet.add(id);
                objectMap.put(id, po);
            }
            Map<String, String> tableNameMap = getShardingManager().getShardingTableNameByIds(table, idSet);
            if (tableNameMap == null || tableNameMap.isEmpty()) {
                throw new RuntimeException("invalid update list,because it's id hasn't sharding");
            }
            Map<String, List<String>> reversedMap = new HashMap<>();
            tableNameMap.forEach((k, v) -> {
                reversedMap.computeIfAbsent(v, t -> new ArrayList<>()).add(k);
            });
            int total = 0;
            for (Map.Entry<String, List<String>> entry : reversedMap.entrySet()) {
                String tableName = entry.getKey();
                List<String> values = entry.getValue();
                List oneTableBatchList = values.stream().map(objectMap::get).collect(Collectors.toList());
                total += doOneTableUpdate(executor, oneTableBatchList, table, tableName, columns);
            }
            return total;
        } else {
            return doOneTableUpdate(executor, poList, table, table.getSqlName(), columns);
        }
    }

    private int doOneTableUpdateSelective(Executor executor, List poList, Table table, String tableName, Set<String> includeColumns) throws SQLException {
        StringBuilder builder = new StringBuilder((table.getColumns().size() * 20 + 3) * batchSize * 3); //random guess, better than nothing
        List<List> batches = BatchUtils.sliceBatch(poList, batchSize);
        Connection conn = executor.getTransaction().getConnection();
        int rows = 0;
        for (List batch : batches) {
            try (Statement statement = conn.createStatement()) {
                for (Object o : batch) {
                    CommonPo po = (CommonPo) o;
                    if (po.getId() == null) {
                        throw new BatchOperationException("id is null when update");
                    }
                    String sql = buildUpdateSelective(table.getColumns(), builder, tableName, po, table, includeColumns);
                    if (log.isDebugEnabled()) {
                        log.debug("simple batch update selective {} ", sql);
                    }
                    builder.setLength(0);
                    statement.addBatch(sql);
                }
                int[] ints = statement.executeBatch();
                rows = rows + Arrays.stream(ints).sum();
            }
        }
        return rows;
    }


    private int doOneTableUpdate(Executor executor, List poList, Table table, String tableName, Set<String> includeColumns) throws SQLException {
        StringBuilder builder = new StringBuilder((table.getColumns().size() * 20 + 3) * batchSize * 3); //random guess, better than nothing
        List<List> batches = BatchUtils.sliceBatch(poList, batchSize);
        Connection conn = executor.getTransaction().getConnection();
        int rows = 0;
        for (List batch : batches) {
            try (Statement statement = conn.createStatement()) {
                for (Object o : batch) {
                    CommonPo po = (CommonPo) o;
                    if (po.getId() == null) {
                        throw new BatchOperationException("id is null when update");
                    }
                    String sql = buildUpdate(table.getColumns(), builder, tableName, po, table, includeColumns);
                    if (log.isDebugEnabled()) {
                        log.debug("simple batch update selective {} ", sql);
                    }
                    builder.setLength(0);
                    statement.addBatch(sql);
                }
                int[] ints = statement.executeBatch();
                rows = rows + Arrays.stream(ints).sum();
            }
        }
        return rows;
    }

    @Override
    protected boolean shouldApply(Invocation invocation, Triple<BatchType, List, Table> context) {
        BatchType batchType = context.getLeft();
        Table right = context.getRight();
        switch (batchType) {
            case BatchInsert:
                return true;
            case BatchUpdateSelective:
                return true;
            case BatchUpdate:
                return true;
            case None:
            default:
                return false;
        }
    }

    @Override
    public String getName() {
        return "SimpleBatchInterceptor";
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private String getShardingValue(List<String> shardingValues) {
        return shardingValues.stream().reduce((r, s) -> r + "|" + s).orElse(null);
    }

    private ShardingManager getShardingManager() {
        return SpringUtils.getBean(ShardingManager.class);
    }
}
