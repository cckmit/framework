package org.mickey.framework.core.mybatis.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;

import java.util.Properties;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class NamedWrapperInterceptor implements NamedInterceptor {

    private String name;

    private Interceptor interceptor;

    public NamedWrapperInterceptor(String name, Interceptor interceptor) {
        this.name = name;
        this.interceptor = interceptor;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        throw new NotImplementedException("should never be here");
    }

    @Override
    public Object plugin(Object target) {
        return interceptor.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        throw new NotImplementedException("should never be here");
    }
}
