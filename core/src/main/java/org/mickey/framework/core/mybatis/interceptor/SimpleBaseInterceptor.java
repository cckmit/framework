package org.mickey.framework.core.mybatis.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public abstract class SimpleBaseInterceptor implements NamedInterceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (shouldApply(invocation)) {
            if (log.isDebugEnabled()) {
                log.debug("applied interceptor {}", getName());
            }
            return doIntercept(invocation);
        }
        if (log.isDebugEnabled()) {
            log.debug("pass interceptor {}", getName());
        }
        return invocation.proceed();
    }

    /**
     * @param invocation call invocation.proceed if need to fo to next intercept other wise return int or any non-proxy
     * @return
     */
    protected abstract Object doIntercept(Invocation invocation) throws Throwable;


    protected abstract boolean shouldApply(Invocation invocation);

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
}
