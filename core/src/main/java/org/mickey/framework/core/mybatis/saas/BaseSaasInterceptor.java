package org.mickey.framework.core.mybatis.saas;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.annotation.ProviderSqlSource;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Invocation;
import org.mickey.framework.common.mybatis.MybatisStmtIdContext;
import org.mickey.framework.common.util.ReflectionUtils;
import org.mickey.framework.core.mybatis.MybatisSqlBuilder;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class BaseSaasInterceptor extends InterceptorAdaptor  {
    private Set<String> commonProviderMethodNameSet = new HashSet<>();
    private Set<String> shardingProviderMethodNameSet = new HashSet<>();

    /**
     * 租户Id
     */
    final String COLUMN_TENANT_ID = "tenant_id";
    final String COLUMN_PROJECT_ID = "project_id";

    public BaseSaasInterceptor() {
        commonProviderMethodNameSet.add("insert");
        commonProviderMethodNameSet.add("batchInsert");
        commonProviderMethodNameSet.add("update");
        commonProviderMethodNameSet.add("updateSelective");
        commonProviderMethodNameSet.add("batchUpdateSelective");
        commonProviderMethodNameSet.add("delete");
        commonProviderMethodNameSet.add("deleteByCriteria");
        commonProviderMethodNameSet.add("get");
        commonProviderMethodNameSet.add("findByProperty");
        commonProviderMethodNameSet.add("findByCriteria");
        commonProviderMethodNameSet.add("findByCondition");
        commonProviderMethodNameSet.add("checkRepeat");

        List<Method> methods = ReflectionUtils.getDeclaredMethods(MybatisSqlBuilder.class);
        methods.forEach(m -> {
            commonProviderMethodNameSet.add(m.getName());
        });

        shardingProviderMethodNameSet.add("insert");
        shardingProviderMethodNameSet.add("batchInsert");
        shardingProviderMethodNameSet.add("update");
        shardingProviderMethodNameSet.add("updateSelective");
        shardingProviderMethodNameSet.add("batchUpdateSelective");
        shardingProviderMethodNameSet.add("delete");
        shardingProviderMethodNameSet.add("get");
        shardingProviderMethodNameSet.add("findByCriteria");
        shardingProviderMethodNameSet.add("findByCondition");
    }

    @Override
    protected boolean allowed(Invocation invocation) {
        final Object[] args = invocation.getArgs();
        final MappedStatement ms = (MappedStatement) args[MAPPED_STATEMENT_INDEX];
        SqlSource sqlSource = ms.getSqlSource();
        if (sqlSource instanceof ProviderSqlSource) {
            ProviderSqlSource providerSqlSource = (ProviderSqlSource) sqlSource;
            Class<?> providerType = (Class<?>) ReflectionUtils.getFieldValue(providerSqlSource, "providerType");
            Method providerMethod = (Method) ReflectionUtils.getFieldValue(providerSqlSource, "providerMethod");
            if (providerType.getName().equals(MybatisSqlBuilder.class.getName())
                    && commonProviderMethodNameSet.contains(providerMethod.getName())) {
                return false;
            }

            if (providerType.getName().equals("com.taimeitech.framework.sharding.mybatis.ShardingSqlBuilder")
                    && shardingProviderMethodNameSet.contains(providerMethod.getName())) {
                return false;
            }
        }
        //如果标记为忽略 自动忽略 增加租户Id的动作
        if (MybatisStmtIdContext.isIgnoreTenant(ms.getId())) {
            return false;
        }
        return super.allowed(invocation);
    }
}

