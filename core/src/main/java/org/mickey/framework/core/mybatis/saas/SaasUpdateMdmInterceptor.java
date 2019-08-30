package org.mickey.framework.core.mybatis.saas;

import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.mickey.framework.common.SystemContext;
import org.mickey.framework.common.po.BasePo;
import org.mickey.framework.common.po.BaseProjectPo;
import org.mickey.framework.common.util.DataType;
import org.mickey.framework.common.util.StringUtil;
import org.mickey.framework.common.util.UUIDUtils;
import org.mickey.framework.core.mybatis.MybatisUtils;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Intercepts({@Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class})})
public class SaasUpdateMdmInterceptor extends BaseSaasInterceptor {
    /**
     * 各个参数顺序
     *
     * @see Executor#update(MappedStatement, Object)
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (!allowed(invocation)) {
            return invocation.proceed();
        }
        final Object[] args = invocation.getArgs();
        final MappedStatement ms = (MappedStatement) args[MAPPED_STATEMENT_INDEX];
        final Object parameter = args[PARAMETER_INDEX];
        if (ignoreSet != null && ignoreSet.contains(ms.getId())) {
            return invocation.proceed();
        }
        BoundSql boundSql = ms.getBoundSql(parameter);
        String sql = boundSql.getSql();
        Statement statement = MybatisUtils.parseSql(sql);
        boolean insert = false;
        if (statement instanceof Insert) {
            insert = true;
        }
        if (parameter != null) {
            if (parameter instanceof BasePo) {
                _setCommonProps(insert, parameter);
            } else if (parameter instanceof Map) {
                Map map = (Map) parameter;
                visitMap(insert, map);
            }
        }
        return invocation.proceed();
    }

    private void visitMap(boolean insert, Map map) {
        Collection values = map.values();
        for (Object value : values) {
            if (value instanceof BasePo) {
                ((BasePo) value).setTenantId(SystemContext.getTenantId());
            }
            int dataType = DataType.getDataType(value);
            if (dataType == DataType.DT_Array) {
                Object[] objects = (Object[]) value;
                for (Object object : objects) {
                    _setCommonProps(insert, object);
                }
            } else if (dataType == DataType.DT_List) {
                List list = (List) value;
                for (Object object : list) {
                    _setCommonProps(insert, object);
                }
            }
        }
    }

    /**
     * 设置公共属性，在insert或者update的时候，自动设置id及租户Id
     *
     * @param insert 是否更新
     * @param object PO对象
     */
    private void _setCommonProps(boolean insert, Object object) {
        try {
            if (!(object instanceof BasePo)) {
                return;
            }
            //设置companyId
            BasePo basePO = (BasePo) object;
            basePO.setTenantId(SystemContext.getTenantId());
            //判断是否为insert，如果为insert，自动设置主键
            if(basePO instanceof BaseProjectPo){
                ((BaseProjectPo)basePO).setProjectId(SystemContext.getProjectId());
            }
            String id = basePO.getId();
            if (StringUtil.isBlank(id)) {
                String uuid = UUIDUtils.getUUID();
                basePO.setId(uuid);
            }
            if (insert) {
                if (basePO.getCreateBy() == null) {
                    basePO.setCreateBy(SystemContext.getUserId());
                }
                if (basePO.getCreateTime() == null) {
                    basePO.setCreateTime(new Date());
                }
            }

            if (basePO.getUpdateBy() == null) {
                basePO.setUpdateBy(SystemContext.getUserId());
            }
            if (basePO.getUpdateTime() == null) {
                basePO.setUpdateTime(new Date());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
