package org.mickey.framework.core.mybatis.interceptor;

import org.apache.ibatis.plugin.Interceptor;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
public interface NamedInterceptor extends Interceptor {

    String getName();
}
