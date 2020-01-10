package org.mickey.framework.core.mybatis.saas;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.mickey.framework.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class InterceptorAdaptor implements Interceptor {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected int MAPPED_STATEMENT_INDEX = 0;
    protected int PARAMETER_INDEX = 1;
    //指定哪些statementId不需要此插件处理
    protected Set<String> ignoreSet;
    //	protected boolean enabled = false;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return null;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        //设置忽略的statementId
        String ignoreIds = properties.getProperty("ignoreIds");
        _setIgnoreIds(ignoreIds);
    }

    public void setIgnoreIds(String ignoreIds) {
        _setIgnoreIds(ignoreIds);
    }

    protected void _setIgnoreIds(String ignoreIds) {
        if (StringUtil.isBlank(ignoreIds)) {
            return;
        }
        if (ignoreSet == null) {
            ignoreSet = new HashSet<>();
        }
        StringTokenizer tokenizer = new StringTokenizer(ignoreIds, ",", false);
        while (tokenizer.hasMoreTokens()) {
            ignoreSet.add(tokenizer.nextToken().trim());
        }
    }

    protected boolean allowed(Invocation invocation) {
        final Object[] args = invocation.getArgs();
        final MappedStatement ms = (MappedStatement) args[MAPPED_STATEMENT_INDEX];
		/*if (!enabled) {
			return false;
		}*/

        if (ignoreSet != null && ignoreSet.contains(ms.getId())) {
            return false;
        }
        return true;
    }
}
