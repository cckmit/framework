package org.mickey.framework.common.database;

import lombok.extern.slf4j.Slf4j;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class DuplicateUniqueConstaint extends RuntimeException {
    public DuplicateUniqueConstaint(String name) {
        super(name + " Duplicate definition");
    }
}
