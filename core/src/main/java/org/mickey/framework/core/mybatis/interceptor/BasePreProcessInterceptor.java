package org.mickey.framework.core.mybatis.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Invocation;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public abstract class BasePreProcessInterceptor<T> extends BaseInterceptor<T> {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        T context = preProcess(invocation);
        if (shouldApply(invocation, context)) {
            if (log.isDebugEnabled()) {
                log.debug("applied interceptor {}", getName());
            }
            return doIntercept(invocation, context);
        }
        if (log.isDebugEnabled()) {
            log.debug("pass interceptor {}", getName());
        }
        return invocation.proceed();
    }

}
