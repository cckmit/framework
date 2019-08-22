package org.mickey.framework.common.database;

import lombok.extern.slf4j.Slf4j;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class NoColumnFoundForUniqueConstaint extends RuntimeException {
    public NoColumnFoundForUniqueConstaint(String uqName, String columnName) {
        super(columnName + " cannot be found in " + uqName);
    }
}
