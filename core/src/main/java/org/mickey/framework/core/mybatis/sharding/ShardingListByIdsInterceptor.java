package org.mickey.framework.core.mybatis.sharding;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.mickey.framework.common.SpringUtils;
import org.mickey.framework.common.database.Table;
import org.mickey.framework.common.util.BatchUtils;
import org.mickey.framework.common.util.StringUtil;
import org.mickey.framework.core.mybatis.BatchOperation;
import org.mickey.framework.core.mybatis.BatchType;
import org.mickey.framework.core.mybatis.MybatisUtils;
import org.mickey.framework.core.mybatis.interceptor.BasePreProcessInterceptor;
import org.mickey.framework.dbinspector.common.ORMapping;
import org.springframework.util.Assert;

import java.util.*;

import static org.mickey.framework.core.mybatis.MybatisSqlBuilder.BLANK;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Intercepts(
        {
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        }
)
public class ShardingListByIdsInterceptor extends BasePreProcessInterceptor<Pair<BatchType, Table>> implements BatchOperation {

    //to avoid max_allowed_packet overflow
    private final int batchSize = 2;

    @Override
    protected Object doIntercept(Invocation invocation, Pair<BatchType, Table> context) throws Throwable {
        BatchType left = context.getLeft();
        boolean includeAll = left == BatchType.BatchGetByIdList;
        Table table = context.getRight();
        final Executor executor = (Executor) invocation.getTarget();
        final Object[] args = invocation.getArgs();
        final MappedStatement ms = (MappedStatement) args[0];
        RowBounds rowBounds = (RowBounds) args[2];
        ResultHandler resultHandler = (ResultHandler) args[3];
        Object obj = getObjFromMyBatisArgs(args, "idList");
        if (!(obj instanceof List)) {
            throw new RuntimeException("idList is not type List");
        }
        List<String> idList = (List<String>) obj;
        if (CollectionUtils.isEmpty(idList)) {
            return new ArrayList<>();
        }
        if (idList.size() < batchSize) {
            return queryOneBatch(table, executor, ms, rowBounds, resultHandler, idList, includeAll);
        }
        List<List> lists = BatchUtils.sliceBatch(idList, batchSize);
        ArrayList result = new ArrayList<>(idList.size());
        for (List batches : lists) {
            List batchResult = queryOneBatch(table, executor, ms, rowBounds, resultHandler, batches, includeAll);
            result.addAll(batchResult);
        }
        return result;
    }

    private List queryOneBatch(Table table, Executor executor, MappedStatement ms, RowBounds rowBounds, ResultHandler resultHandler, List<String> idList, boolean includeAll) throws java.sql.SQLException {
        Map<String, List<String>> shardingTableToIdsMap = new HashMap<>();
        ShardingManager shardingManager = getShardingManager();

        Map<String, String> idToTables = shardingManager.getShardingTableNameByIds(table, new HashSet<>(idList));
        for (Map.Entry<String, String> entry : idToTables.entrySet()) {
            String id = entry.getKey();
            String shardingTableName = entry.getValue();
            if (StringUtil.isBlank(id)) {
                log.warn("id is blank ");
                continue;
            }
            if (StringUtil.isBlank(shardingTableName)) {
                log.warn("id {} 's sharding table name is  blank ", id);
                continue;
            }
            List<String> ids = new ArrayList<>();
            if (shardingTableToIdsMap.containsKey(shardingTableName)) {
                ids = shardingTableToIdsMap.get(shardingTableName);
            } else {
                shardingTableToIdsMap.put(shardingTableName, ids);
            }
            ids.add(id);
        }

        String newLine = System.getProperty("line.separator");
//		Map<String, String> newParamMap = new HashMap<>();
        StringBuilder builder = new StringBuilder();
        int counter = 0;
        for (Map.Entry<String, List<String>> entry : shardingTableToIdsMap.entrySet()) {
            List<String> value = entry.getValue();
            if (value.size() == 0) {
                continue;
            }
            String shardingTableName = entry.getKey();
            String ids = concatIds(value);
            processOneShard(builder, table, shardingTableName, ids, includeAll);
//			newParamMap.put(shardingTableName + "_ids", ids);
            counter++;
            if (counter != shardingTableToIdsMap.size()) {
                builder.append(newLine);
                builder.append("union all").append(newLine);
            }
        }
        if (counter == 0) {
            return new ArrayList<>();
        }
        SqlSource sqlSource = new RawSqlSource(ms.getConfiguration(), builder.toString(), Map.class);
        MappedStatement newMs = MybatisUtils.copyFromMappedStatement(ms, "ShardingListByIds", sqlSource);
        return executor.query(newMs, new HashMap<>(), rowBounds, resultHandler);
    }

    private void processOneShard(StringBuilder builder, Table table, String shardingTableName, String ids, boolean includeAll) {
        builder.append("select");
        builder.append(BLANK);
        table.getColumns().forEach(column -> {
            builder.append(BLANK).append(column.getSqlName()).append(BLANK).append("as").append(BLANK).append(column.getJavaName());
            builder.append(",");
        });
        builder.deleteCharAt(builder.length() - 1);
        builder.append(BLANK);
        builder.append("from");
        builder.append(BLANK);
        builder.append(shardingTableName);
        builder.append(BLANK).append("where").append(BLANK);
        builder.append("id in (").append(ids).append(")");
        builder.append(BLANK);
        if (!includeAll) {
            builder.append("and is_deleted = 0").append(BLANK);
        }
    }


    @Override
    protected Pair<BatchType, Table> preProcess(Invocation invocation) {
        BatchType batchType = getBatchType(invocation);
        if (batchType == BatchType.None) {
            return Pair.of(batchType, null);
        }
        final Object[] args = invocation.getArgs();
        Object clazz = getObjFromMyBatisArgs(args, "clazz");
        Assert.notNull(clazz, "clazz for listByIds cannot be null");
        Table table = ORMapping.get((Class<?>) clazz);
        Assert.notNull(table, "table cannot be found for listByIds " + ((Class<?>) clazz).getName());
        return Pair.of(batchType, table);
    }

    @Override
    protected boolean shouldApply(Invocation invocation, Pair<BatchType, Table> context) {
        BatchType left = context.getLeft();
        switch (left) {
            case BatchGetList:
            case BatchGetByIdList:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String getName() {
        return "ShardingListByIdsInterceptor";
    }

    @Override
    public void setProperties(Properties properties) {
    }

    private ShardingManager getShardingManager() {
        return SpringUtils.getBean("defaultShardingManager");
    }

    private String concatIds(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String element : list) {
            sb.append("'").append(element).append("',");
        }
        if (list.size() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
