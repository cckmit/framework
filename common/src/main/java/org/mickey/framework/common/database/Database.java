package org.mickey.framework.common.database;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * description
 *
 * @author mickey
 * 05/07/2019
 */
@Data
public class Database {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private String host;
    private int port;
    private String name;
    private DbType type;
    private String connParam;
    private String username;
    private String password;
    private String schema;
    private String catalog;

    private List<Table> tables;
}
