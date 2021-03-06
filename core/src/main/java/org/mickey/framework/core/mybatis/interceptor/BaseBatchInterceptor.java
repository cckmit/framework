package org.mickey.framework.core.mybatis.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.plugin.Invocation;
import org.mickey.framework.common.database.Table;
import org.mickey.framework.common.po.AbstractCommonPo;
import org.mickey.framework.core.mybatis.BatchOperation;
import org.mickey.framework.core.mybatis.BatchOperationException;
import org.mickey.framework.core.mybatis.BatchType;
import org.mickey.framework.dbinspector.common.ORMapping;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public abstract class BaseBatchInterceptor extends BasePreProcessInterceptor<Triple<BatchType, List, Table>> implements BatchOperation {


    protected int batchSize = 500;

    public BaseBatchInterceptor() {
    }

    protected BaseBatchInterceptor(int batchSize) {
        this.batchSize = batchSize;
    }

    @Override
    protected Triple<BatchType, List, Table> preProcess(Invocation invocation) {
        BatchType batchType = getBatchType(invocation);
        if (batchType == BatchType.None) {
            return Triple.of(batchType, null, null);
        }
        Object[] args = invocation.getArgs();
        Object parameterMap = args[1];
        if (!(parameterMap instanceof HashMap)) {
            throw new RuntimeException("mybatis parameterMap is not a hash map");
        }
        HashMap parameterHashMap = (HashMap) parameterMap;
        Object parameter = parameterHashMap.get("list");
        if (!(parameter instanceof List)) {
            throw new RuntimeException("parameter of batch insert is not a list");
        }
        List poList = (List) parameter;
        if (CollectionUtils.isEmpty(poList)) {
            return Triple.of(batchType, poList, null);
        }
        Object firstPo = poList.get(0);
        if (!(firstPo instanceof AbstractCommonPo)) {
            throw new RuntimeException("batch insert doesn't contain a BasePO class");
        }
        AbstractCommonPo commonPO = (AbstractCommonPo) poList.get(0);
        Table table = ORMapping.get(commonPO.getClass());
        if (table == null) {
            throw new RuntimeException("not a jpa standard class:" + commonPO.getClass().getName());
        }
        return Triple.of(batchType, poList, table);
    }


    @Override
    protected Object doIntercept(Invocation invocation, Triple<BatchType, List, Table> context) throws Throwable {
        Object[] args = invocation.getArgs();
        Object parameterMap = args[1];
        Executor executor = (Executor) invocation.getTarget();
        return doBatch(executor, context, (HashMap) parameterMap);
    }


    private int doBatch(Executor executor, Triple<BatchType, List, Table> context, HashMap parameterMap) throws SQLException {
        BatchType batchType = context.getLeft();
        List poList = context.getMiddle();
        if (CollectionUtils.isEmpty(poList)) {
            return 0;
        }
        Table table = context.getRight();

        if (CollectionUtils.isEmpty(table.getColumns())) {
            throw new RuntimeException("table " + table.getJavaName() + " doesn't have allColumns");
        }

        int rows = 0;
        try {
            switch (batchType) {
                case BatchInsert:
                    rows = doBatchInsert(executor, poList, table);
                    break;
                case BatchUpdateSelective:
                    Object includeColumns = parameterMap.get("includeColumns");
                    if (!(includeColumns instanceof Set)) {
                        throw new BatchOperationException("includeColumns is not a list");
                    }
                    rows = doBatchUpdateSelective(executor, poList, table, (Set<String>) includeColumns);
                    break;
                case BatchUpdate:
                    Object columns = parameterMap.get("includeColumns");
                    if (!(columns instanceof Set)) {
                        throw new BatchOperationException("columns is not a list");
                    }
                    //TODO
                    rows = doBatchUpdate(executor, poList, table, (Set<String>) columns);
                    break;
            }
        } finally {
            executor.clearLocalCache();
        }

        return rows;
    }

    /**
     * ????????????
     *
     * @param executor
     * @param poList
     * @param table
     * @return
     * @throws SQLException
     */
    protected abstract int doBatchInsert(Executor executor, List poList, Table table) throws SQLException;

    /**
     * ????????????
     *
     * @param executor
     * @param poList
     * @param table
     * @param includeColumns
     * @return
     * @throws SQLException
     */
    protected abstract int doBatchUpdateSelective(Executor executor, List poList, Table table, Set<String> includeColumns) throws SQLException;


    /**
     * ????????????
     *
     * @param executor
     * @param poList
     * @param table
     * @param columns
     * @return
     * @throws SQLException
     */
    protected abstract int doBatchUpdate(Executor executor, List poList, Table table, Set<String> columns) throws SQLException;

}
