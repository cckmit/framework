package org.mickey.framework.core.mybatis.saas;

import lombok.extern.slf4j.Slf4j;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class SassInterceptorException extends RuntimeException {

    public SassInterceptorException(String message) {
        super(message);
    }

    public SassInterceptorException(String message, Throwable cause) {
        super(message, cause);
    }

    public SassInterceptorException(Throwable cause) {
        super(cause);
    }
}
