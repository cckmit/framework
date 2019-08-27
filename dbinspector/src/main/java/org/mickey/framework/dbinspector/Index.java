package org.mickey.framework.dbinspector;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Data
public class Index {
    private String name;
    private String columnList;
    private boolean unique;
    private String ddl;
}
