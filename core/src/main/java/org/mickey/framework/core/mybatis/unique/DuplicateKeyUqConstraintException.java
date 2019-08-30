package org.mickey.framework.core.mybatis.unique;

import lombok.extern.slf4j.Slf4j;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class DuplicateKeyUqConstraintException extends Exception {

    private String uqName;

    public DuplicateKeyUqConstraintException(String uqName, Throwable casue) {
        super(casue);
        this.uqName = uqName;
    }

    public String getUqName() {
        return uqName;
    }
}
