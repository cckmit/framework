package org.mickey.framework.dbinspector.dialect;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.dbinspector.dialect.impl.MysqlDialect;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class DialectFactory {
    public static Dialect getDialect(String dbType) {
        switch (dbType) {
            case "mysql":
                return new MysqlDialect();
        }
        return new MysqlDialect();
    }
}
