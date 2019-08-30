package org.mickey.framework.core.mybatis;

import lombok.extern.slf4j.Slf4j;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class BatchOperationException extends RuntimeException {

    public BatchOperationException(String message) {
        super(message);
    }
}
