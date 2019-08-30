package org.mickey.framework.core.mybatis.saas;

import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.mickey.framework.common.SystemContext;
import org.mickey.framework.common.po.BasePo;
import org.mickey.framework.common.po.BaseProjectPo;
import org.mickey.framework.common.util.ReflectionUtils;
import org.mickey.framework.core.mybatis.MybatisUtils;
import org.mickey.framework.dbinspector.common.ORMapping;

import java.util.List;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Intercepts(
        {
                @Signature(
                        type = Executor.class,
                        method = "query",
                        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
        })
public class SaasSelectMdmInterceptor extends BaseSaasInterceptor {
    /**
     * 各个参数顺序
     *
     * @see Executor#query(MappedStatement, Object, RowBounds, ResultHandler)
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (!allowed(invocation)) {
            return invocation.proceed();
        }
        final Object[] args = invocation.getArgs();
        final MappedStatement ms = (MappedStatement) args[MAPPED_STATEMENT_INDEX];
        final Object parameter = args[PARAMETER_INDEX];
        String id = ms.getId();
        if (ignoreSet != null && ignoreSet.contains(id)) {
            return invocation.proceed();
        }
        BoundSql boundSql = ms.getBoundSql(parameter);
        String sql = boundSql.getSql();
        //sql解析 自动在select的where条件中增加companyId
        String parsedSql = addTenantIdCondition(sql);
        MappedStatement newSql = MybatisUtils.copyFromNewSql(ms, boundSql, parsedSql);
        args[MAPPED_STATEMENT_INDEX] = newSql;
        return invocation.proceed();
    }

    private String addTenantIdCondition(String sql) {

        Statement statement = MybatisUtils.parseSql(sql);
        if (statement instanceof Select) {
            Select select = (Select) statement;
            SelectBody selectBody = select.getSelectBody();
            selectBody.accept(new SelectVisitorAdapter() {
                @Override
                public void visit(PlainSelect plainSelect) {
                    FromItem fromItem = plainSelect.getFromItem();
                    if (!(fromItem instanceof Table)) {
                        logger.error("the origin sql is:" + sql);
                        logger.error("we don't support subselect or procedure,please make sure your sql has added tenant_id conditions");
                        return;
                    }
                    Table fromTable = (Table) fromItem;
                    List<Join> joins = plainSelect.getJoins();
                    //连接时，将tenant_id的条件加入到join的on语句中
                    if (CollectionUtils.isNotEmpty(joins)) {
                        for (Join join : joins) {
                            FromItem rightItem = join.getRightItem();
                            Table rightTable = (Table) rightItem;
                            if (hasTenantId(fromTable) && hasTenantId(rightTable)) {
                                if ((join.isLeft() || join.isRight() || join.isFull() || join.isCross() || join.isInner()) && rightItem instanceof Table) {
                                    Expression onExpression = join.getOnExpression();
                                    List<Column> usingColumns = join.getUsingColumns();
                                    if (usingColumns != null) {
                                        throw new SassInterceptorException("using statement is not supported");
                                    }
                                    EqualsTo tenantOn = genJoinTenantId((Table) fromItem, (Table) rightItem);
                                    if (onExpression == null) {
                                        onExpression = tenantOn;
                                    } else {
                                        onExpression = new AndExpression(onExpression, tenantOn);
                                    }
                                    if (hasProjectId(fromTable) && hasProjectId(rightTable)){
                                        onExpression = new AndExpression(onExpression,genJoinProjectId(fromTable,rightTable));
                                    }
                                    join.setOnExpression(onExpression);
                                }
                            }
                        }
                    }
                    //自动将tenant_id的筛选条件增加到sql语句的where部分
                    if (hasTenantId(fromTable)) {
                        Expression where = plainSelect.getWhere();
                        EqualsTo equalsToTenant = genWhereTenantId(fromTable);
                        if (where == null) {
                            where = equalsToTenant;
                        } else {
                            where = new AndExpression(where, equalsToTenant);
                        }
                        if (hasProjectId(fromTable)) {
                            where = new AndExpression(where, genWhereProjectId(fromTable));
                        }
                        plainSelect.setWhere(where);
                    }

                }
            });
            return select.toString();
        }
        return sql;
    }

    private EqualsTo genJoinTenantId(Table fromItem, Table rightItem) {
        EqualsTo equalsTo = new EqualsTo();
        equalsTo.setLeftExpression(new Column(fromItem, COLUMN_TENANT_ID));
        equalsTo.setRightExpression(new Column(rightItem, COLUMN_TENANT_ID));
        return equalsTo;
    }

    private EqualsTo genJoinProjectId(Table fromItem, Table rightItem) {
        EqualsTo equalsTo = new EqualsTo();
        equalsTo.setLeftExpression(new Column(fromItem, COLUMN_PROJECT_ID));
        equalsTo.setRightExpression(new Column(rightItem, COLUMN_PROJECT_ID));
        return equalsTo;
    }

    private EqualsTo genWhereTenantId(Table fromItem) {
        EqualsTo equalsTo = new EqualsTo();
        equalsTo.setLeftExpression(new Column(fromItem, COLUMN_TENANT_ID));
        equalsTo.setRightExpression(new StringValue("'" + SystemContext.getTenantId() + "'"));
        return equalsTo;
    }

    private EqualsTo genWhereProjectId(Table fromItem) {
        EqualsTo equalsTo = new EqualsTo();
        equalsTo.setLeftExpression(new Column(fromItem, COLUMN_PROJECT_ID));
        equalsTo.setRightExpression(new StringValue("'" + SystemContext.getProjectId() + "'"));
        return equalsTo;
    }

    boolean hasTenantId(Table table) {
        String name = table.getName();
        Class<?> poClazz = ORMapping.getPoClazz(name);
        if (poClazz == null) return false;
        return ReflectionUtils.isSubClass(poClazz, BasePo.class);
    }

    boolean hasProjectId(Table table) {
        String name = table.getName();
        Class<?> poClazz = ORMapping.getPoClazz(name);
        if (poClazz == null) return false;
        return ReflectionUtils.isSubClass(poClazz, BaseProjectPo.class);
    }
}
