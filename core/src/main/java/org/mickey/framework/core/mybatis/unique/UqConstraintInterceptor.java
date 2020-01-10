package org.mickey.framework.core.mybatis.unique;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.mickey.framework.common.database.Column;
import org.mickey.framework.common.database.Table;
import org.mickey.framework.common.database.UqConstraint;
import org.mickey.framework.common.po.AbstractCommonPo;
import org.mickey.framework.common.util.BatchUtils;
import org.mickey.framework.common.util.ReflectionUtils;
import org.mickey.framework.common.util.StringUtil;
import org.mickey.framework.core.mybatis.BatchOperation;
import org.mickey.framework.core.mybatis.UpdateType;
import org.mickey.framework.core.mybatis.interceptor.BaseMiddleInterceptor;
import org.mickey.framework.dbinspector.common.ORMapping;
import org.springframework.util.Assert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@SuppressWarnings("SqlDialectInspection")
@Intercepts(
        {
                @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        }
)
public class UqConstraintInterceptor extends BaseMiddleInterceptor<Pair<UpdateType, Table>> implements BatchOperation {

    private static final String Insert_Sql = "_framework_unique (unique_value, index_name, data_id) VALUES (?,?,?)";
    private static final String Update_Sql = "_framework_unique set unique_value=? where data_id=? and index_name=?";
    private static final String Delete_Sql = "_framework_unique where data_id=? and index_name=?";
    //todo make this configurable
    private String defaultNullValue = "__$default$__#null#__";
    private int batchSize = 500;

    /**
     * integrity check, no support for DeleteByCondition, and DeleteByCriteria
     * integrity check, selective and update selective , must include all columns for all unique constraints or zero column
     * insert, then insert one row
     * update, update selective, update one row
     * delete, real delete from unique table
     * always use prepare statement
     *
     * @param invocation call invocation.proceed if need to fo to next intercept other wise return int or any non-proxy
     * @param context
     * @return
     * @throws Throwable
     */
    @Override
    protected Object doIntercept(Invocation invocation, Pair<UpdateType, Table> context) throws Throwable {
        UpdateType left = context.getLeft();
        Table right = context.getRight();
        if (left == UpdateType.DeleteByCondition || left == UpdateType.DeleteByCriteria) {
            throw new NotImplementedException("DeleteByCondition and DeleteByCriteria for unique constraint are not supported at this moment.");
        }
        if (left == UpdateType.Update || left == UpdateType.UpdateSelective || left == UpdateType.BatchUpdateSelective) {
            checkUpdateIntegrity(left, right, invocation);
        }
        try {
            switch (left) {
                case Insert:
                    doInsert(invocation, context);
                    break;
                case Update:
                case UpdateSelective:
                    doUpdate(invocation, context);
                    break;
                case Delete:
                    doDelete(invocation, context);
                    break;
                case BatchInsert:
                    doBatchInsert(invocation, context);
                    break;
                case BatchUpdateSelective:
                    doBatchUpdate(invocation, context);
                    break;
            }
        } catch (SQLException ex) {

            String message = ex.getMessage();
            Optional<UqConstraint> realCause = findUqCause(message, right);
            if (!realCause.isPresent()) {
                throw ex;
            } else {
                throw new DuplicateKeyUqConstraintException(realCause.get().getName(), ex);
            }

        }
        return null;
    }

    private Optional<UqConstraint> findUqCause(String message, Table table) {
        Map<String, UqConstraint> uqConstraints = table.getUqConstraintMap();
        if (uqConstraints.size() == 0) {
            return Optional.ofNullable(null);
        }
        for (UqConstraint uqConstraint : uqConstraints.values()) {
            Pattern pattern = uqConstraint.getPattern();
            if (pattern == null) {
                continue;
            }
            Matcher matcher = pattern.matcher(message);
            if (matcher.find()) {
                return Optional.ofNullable(uqConstraint);
            }
        }
        return Optional.ofNullable(null);
    }

    private void doBatchUpdate(Invocation invocation, Pair<UpdateType, Table> context) throws SQLException, UniqueConstaintCustomExecution {
        final Object[] args = invocation.getArgs();
        Object argMap = args[1];
        List poList = getPoListFromMyBatisArgs(args);
        if (poList == null) {
            throw new RuntimeException("batch update polist is null");
        }
        Object includedColumns = getObjFromMyBatisArgs(args, "includeColumns");
        List<String> columnsToSelect = includedColumns == null ? new ArrayList<>() : (List<String>) includedColumns;
        List<List> batches = BatchUtils.sliceBatch(poList, batchSize);
        Executor executor = (Executor) invocation.getTarget();
        Connection conn = executor.getTransaction().getConnection();
        for (List batch : batches) {
            doUqUpdateOneBatch(conn, batch, context, columnsToSelect);
        }
    }


    private void doBatchInsert(Invocation invocation, Pair<UpdateType, Table> context) throws SQLException, UniqueConstaintCustomExecution {
        final Object[] args = invocation.getArgs();
        Object argMap = args[1];
        List poList = getPoListFromMyBatisArgs(args);
        if (poList == null) {
            throw new RuntimeException("batch insert polist is null");
        }
        List<List> batches = BatchUtils.sliceBatch(poList, batchSize);
        Executor executor = (Executor) invocation.getTarget();
        Connection conn = executor.getTransaction().getConnection();
        for (List batch : batches) {
            doUqInsertOneBatch(conn, batch, context);
        }
    }

    private void doUqUpdateOneBatch(Connection conn, List poList, Pair<UpdateType, Table> context, List<String> columnsToSelect) throws SQLException, UniqueConstaintCustomExecution {
        Table right = context.getRight();
        Map<String, UqConstraint> uqConstraints = right.getUqConstraintMap();
        for (Map.Entry<String, UqConstraint> entry : uqConstraints.entrySet()) {
            try (PreparedStatement ps = conn.prepareStatement("update " + right.getSqlName() + Update_Sql)) {
                UqConstraint uqConstraint = entry.getValue();
                for (Object o : poList) {
                    AbstractCommonPo po = (AbstractCommonPo) o;
                    Assert.notNull(po.getId(), "po id cannot be null");
                    if (uqConstraint.isCustom()
                            ||
                            appliedConstraintSelectiveColumnSize(columnsToSelect, uqConstraint, po) == uqConstraint.getColumns().size()) {
                        ps.setString(1, getUCValue(po, uqConstraint));
                        ps.setString(3, uqConstraint.getName());
                        ps.setString(2, po.getId());
                        ps.addBatch();
                    } else {
                        if (log.isDebugEnabled()) {
                            log.debug("batch selective no apply for constraint {}, class {}, id {}", uqConstraint.getName(), po.getClass(), po.getId());
                        }
                    }
                }
                int[] resultArr = ps.executeBatch();
                int sum = 0;
                if (resultArr != null) {
                    for (int i : resultArr) {
                        sum = sum + i;
                    }
                }
                if (log.isDebugEnabled()) {
                    log.debug("batch uq update for {}, rows {}", uqConstraint.getName(), sum);
                }
            }
        }
    }

    @SuppressWarnings("Duplicates")
    private void doUqInsertOneBatch(Connection conn, List poList, Pair<UpdateType, Table> context) throws SQLException, UniqueConstaintCustomExecution {
        Table right = context.getRight();
        Map<String, UqConstraint> uqConstraints = right.getUqConstraintMap();
        for (Map.Entry<String, UqConstraint> entry : uqConstraints.entrySet()) {
            try (PreparedStatement ps = conn.prepareStatement("insert " + right.getSqlName() + Insert_Sql)) {
                UqConstraint uqConstraint = entry.getValue();
                for (Object o : poList) {
                    AbstractCommonPo po = (AbstractCommonPo) o;
                    Assert.notNull(po.getId(), "po id cannot be null");
                    ps.setString(1, getUCValue(po, uqConstraint));
                    ps.setString(2, uqConstraint.getName());
                    ps.setString(3, po.getId());
                    ps.addBatch();
                }
                int[] resultArr = ps.executeBatch();
                int sum = 0;
                if (resultArr != null) {
                    for (int i : resultArr) {
                        sum = sum + i;
                    }
                }
                if (log.isDebugEnabled()) {
                    log.debug("batch uq insert for {}, rows {}", uqConstraint.getName(), sum);
                }
            }
        }
    }

    private void doDelete(Invocation invocation, Pair<UpdateType, Table> context) throws SQLException {
        final Object[] args = invocation.getArgs();
        Object idObj = getObjFromMyBatisArgs(args, "id");
        Assert.notNull(idObj, "delete id cannot be null");
        Assert.isTrue(idObj instanceof String, "delete id is not a string");
        Table right = context.getRight();
        Executor executor = (Executor) invocation.getTarget();
        Connection conn = executor.getTransaction().getConnection();
        Map<String, UqConstraint> uqConstraints = right.getUqConstraintMap();
        int rows = 0;
        try (PreparedStatement ps = conn.prepareStatement("delete from " + right.getSqlName() + Delete_Sql)) {
            for (Map.Entry<String, UqConstraint> entry : uqConstraints.entrySet()) {
                UqConstraint uqConstraint = entry.getValue();
                ps.setString(2, uqConstraint.getName());
                ps.setString(1, (String) idObj);
                ps.addBatch();
            }
            int[] ints = ps.executeBatch();
            rows = rows + Arrays.stream(ints).sum();
        }
        if (log.isDebugEnabled()) {
            log.debug("simple uq delete rows {}", rows);
        }
    }


    private void doUpdate(Invocation invocation, Pair<UpdateType, Table> context) throws SQLException, UniqueConstaintCustomExecution {
        final Object[] args = invocation.getArgs();
        AbstractCommonPo po = getPoFromMyBatisArgs(args);
        Object includedColumns = getObjFromMyBatisArgs(args, "columns");
        List<String> columnsToSelect = includedColumns == null ? new ArrayList<>() : (List<String>) includedColumns;
        Table right = context.getRight();
        UpdateType left = context.getLeft();
        Executor executor = (Executor) invocation.getTarget();
        Connection conn = executor.getTransaction().getConnection();
        Map<String, UqConstraint> uqConstraints = right.getUqConstraintMap();
        int rows = 0;
        try (PreparedStatement ps = conn.prepareStatement("update " + right.getSqlName() + Update_Sql)) {
            for (Map.Entry<String, UqConstraint> entry : uqConstraints.entrySet()) {
                UqConstraint uqConstraint = entry.getValue();
                Assert.notNull(po.getId(), "po id cannot be null");

                if (uqConstraint.isCustom()
                        || (left == UpdateType.Update && appliedConstraintUpdateColumnSize(columnsToSelect, uqConstraint) == uqConstraint.getColumns().size())
                        || (left == UpdateType.UpdateSelective && appliedConstraintSelectiveColumnSize(columnsToSelect, uqConstraint, po) == uqConstraint.getColumns().size())) {
                    ps.setString(1, getUCValue(po, uqConstraint));
                    ps.setString(3, uqConstraint.getName());
                    ps.setString(2, po.getId());
                    ps.addBatch();
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("no apply for constraint {}, class {}, id {}", uqConstraint.getName(), po.getClass(), po.getId());
                    }
                }
            }
            int[] ints = ps.executeBatch();
            rows = rows + Arrays.stream(ints).sum();
        }
        if (log.isDebugEnabled()) {
            log.debug("simple uq update rows {}", rows);
        }
    }


    private void doInsert(Invocation invocation, Pair<UpdateType, Table> context) throws SQLException, UniqueConstaintCustomExecution {
        final Object[] args = invocation.getArgs();
        AbstractCommonPo po = getPoFromMyBatisArgs(args);
        Table right = context.getRight();
        Executor executor = (Executor) invocation.getTarget();
        Connection conn = executor.getTransaction().getConnection();
        Map<String, UqConstraint> uqConstraints = right.getUqConstraintMap();
        int rows = 0;
        try (PreparedStatement ps = conn.prepareStatement("insert " + right.getSqlName() + Insert_Sql)) {
            for (Map.Entry<String, UqConstraint> entry : uqConstraints.entrySet()) {
                UqConstraint uqConstraint = entry.getValue();
                Assert.notNull(po.getId(), "po id cannot be null");
                ps.setString(1, getUCValue(po, uqConstraint));
                ps.setString(2, uqConstraint.getName());
                ps.setString(3, po.getId());
                ps.addBatch();
            }
            int[] ints = ps.executeBatch();
            rows = rows + Arrays.stream(ints).sum();
        }
        if (log.isDebugEnabled()) {
            log.debug("simple uq insert rows {}", rows);
        }
    }

    //todo complete
    @SuppressWarnings({"unchecked", "ConstantConditions"})
    private void checkUpdateIntegrity(UpdateType updateType, Table table, Invocation invocation) throws UniqueConstaintUpdateIntegrityCheckException {
        final Object[] args = invocation.getArgs();
        Object argMapObj = args[1];
        Map<String, UqConstraint> uqConstraints = table.getUqConstraintMap();
        if (updateType == UpdateType.Update) {
            Object columns = getObjFromMyBatisArgs(args, "columns");
            if (CollectionUtils.isEmpty((List) columns)) {
                return; //全部更新
            }
            //检查是否所有的约束字段都被更新或都没有被更新
            for (UqConstraint uqConstraint : uqConstraints.values()) {
                checkOneConstraintForUpdate((List<String>) columns, uqConstraint);
            }
            return;
        }

        if (updateType == UpdateType.UpdateSelective) {
            Object includedColumns = getObjFromMyBatisArgs(args, "columns");
            List<String> columnsToSelect = includedColumns == null ? new ArrayList<>() : (List<String>) includedColumns;
            AbstractCommonPo po = getPoFromMyBatisArgs(args);
            if (po == null) {
                return;
            }
            for (UqConstraint uqConstraint : uqConstraints.values()) {
                //检查是否所有的约束字段都被更新或都没有被更新
                checkOneConstraintForUpdateSelective(columnsToSelect, uqConstraint, po);
            }
        }

        if (updateType == UpdateType.BatchUpdateSelective) {
            Object includedColumns = getObjFromMyBatisArgs(args, "includeColumns");
            List<String> columnsToSelect = includedColumns == null ? new ArrayList<>() : (List<String>) includedColumns;
            List poList = getPoListFromMyBatisArgs(args);
            if (poList == null) {
                return;
            }
            for (Object o : poList) {
                AbstractCommonPo po = (AbstractCommonPo) o;
                for (UqConstraint uqConstraint : uqConstraints.values()) {
                    checkOneConstraintForUpdateSelective(columnsToSelect, uqConstraint, po);
                }
            }
        }

    }


    private long appliedConstraintSelectiveColumnSize(List<String> columnsToSelect, UqConstraint uqConstraint, AbstractCommonPo po) {
        TreeSet<Column> uqConstraintColumns = uqConstraint.getColumns();
        long count = uqConstraintColumns.stream().filter(c -> columnsToSelect.contains(c.getJavaName()) || ReflectionUtils.getFieldValue(po, c.getJavaName()) != null).count();
        return count;
    }

    private long appliedConstraintUpdateColumnSize(List<String> columnsToSelect, UqConstraint uqConstraint) {
        TreeSet<Column> uqConstraintColumns = uqConstraint.getColumns();
        if (CollectionUtils.isEmpty(columnsToSelect)) {
            return uqConstraintColumns.size(); //不指定，全更新
        }
        long count = uqConstraintColumns.stream().filter(c -> columnsToSelect.contains(c.getJavaName())).count();
        return count;
    }

    private void checkOneConstraintForUpdateSelective(List<String> columnsToSelect, UqConstraint uqConstraint, AbstractCommonPo po) throws UniqueConstaintUpdateIntegrityCheckException {
        if (uqConstraint.isCustom()) {
            return;
        }
        long count = appliedConstraintSelectiveColumnSize(columnsToSelect, uqConstraint, po);
        if (!(count == 0 || count == uqConstraint.getColumns().size())) { //如果都被更新或者都没有被更新则抛出异常
            throw new UniqueConstaintUpdateIntegrityCheckException("唯一约束字段更新不完全 " + uqConstraint.getName());
        }
    }

    private void checkOneConstraintForUpdate(List<String> columns, UqConstraint uqConstraint) throws UniqueConstaintUpdateIntegrityCheckException {
        if (uqConstraint.isCustom()) {
            return;
        }
        long count = appliedConstraintUpdateColumnSize(columns, uqConstraint);
        if (!(count == 0 || count == uqConstraint.getColumns().size())) { //如果都被更新或者都没有被更新则抛出异常
            throw new UniqueConstaintUpdateIntegrityCheckException("唯一约束字段更新不完全 " + uqConstraint.getName());
        }
    }

    public String getUCValue(AbstractCommonPo po, UqConstraint uqConstraint) throws UniqueConstaintCustomExecution {
        if (uqConstraint.isCustom()) {
            String ucValue;
            try {
                ucValue = uqConstraint.getLambda().apply(po);
            } catch (Throwable cause) {
                throw new UniqueConstaintCustomExecution("Uq Custom execution error", cause);
            }
            //check null
            String md5Hex = DigestUtils.md5Hex(ucValue == null ? defaultNullValue : ucValue);
            if (log.isDebugEnabled() && StringUtil.isNotBlank(po.getId())) {
                log.debug("calc custom uq value {}, md5, {} for id {}", ucValue, md5Hex, po.getId());
            }
            return md5Hex;
        } else {
            StringBuilder sb = new StringBuilder(256);
            for (Column column : uqConstraint.getColumns()) {
                Object fieldValue = ReflectionUtils.getFieldValue(po, column.getJavaName());
                String sqlValue = getSqlForVal(fieldValue, column);
                sb.append(DigestUtils.md5Hex(sqlValue));
            }
            return DigestUtils.md5Hex(sb.toString());
        }
    }


    @Override
    public String getSqlForVal(Object val, Column column) {

        if (val == null) {
            return defaultNullValue;
        }

        return getSqlForNotNullValue(val, column);
    }

    /**
     * @param invocation
     * @return
     */
    @Override
    protected Pair<UpdateType, Table> preProcess(Invocation invocation) {
        UpdateType updateType = getUpdateType(invocation);
        Table tableMeta = getTableMeta(invocation, updateType);
        return Pair.of(updateType, tableMeta);
    }

    @Override
    protected boolean shouldApply(Invocation invocation, Pair<UpdateType, Table> context) {
        Table right = context.getRight();
        boolean c = right != null && right.hasUniqueConstraint();
        return c;
    }

    private Table getTableMeta(Invocation invocation, UpdateType context) {
        Table table = null;
        switch (context) {
            case Delete:
            case DeleteByCriteria:
            case DeleteByCondition:
            case Insert:
            case Update:
            case UpdateSelective:
            case BatchInsert:
            case BatchUpdateSelective:
                final Object[] args = invocation.getArgs();
                AbstractCommonPo po = getPoFromMyBatisArgs(args);
                Class<?> targetClass = null;
                if (po == null) {
                    List poList = getPoListFromMyBatisArgs(args);
                    if (CollectionUtils.isNotEmpty(poList)) {
                        Object obj1 = poList.get(0);
                        if (obj1 instanceof AbstractCommonPo) {
                            po = (AbstractCommonPo) obj1;
                        }
                    }
                }
                targetClass = po == null ? getDeleteMethodClass(args) : po.getClass();
                if (targetClass == null) {
                    return null;
                }
                table = ORMapping.get(targetClass);
                break;
            case None:
            default:
                return null;
        }
        return table;
    }

    private Class<?> getDeleteMethodClass(Object[] args) {
        Object arg = args[1];
        if (arg instanceof Map) {
            Map argMap = (Map) arg;
            try {
                return (Class<?>) argMap.get("clazz");
            } catch (BindingException be) {
                if (log.isDebugEnabled()) {
                    log.error("expected mybatis exception", be);
                }
            }
        }
        return null;
    }

    private AbstractCommonPo getPoFromMyBatisArgs(Object[] args) {
        Object arg = args[1];
        if (arg instanceof AbstractCommonPo) {
            return (AbstractCommonPo) arg;
        }
        if (arg instanceof Map) {
            Map argMap = (Map) arg;
            try {
                return (AbstractCommonPo) argMap.get("po");
            } catch (BindingException be) {
                if (log.isDebugEnabled()) {
                    log.error("expected mybatis exception", be);
                }
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return "UqConstraintInterceptor";
    }

    @Override
    public void setProperties(Properties properties) {

    }


}
