package org.mickey.framework.dbinspector.dialect;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.database.Column;
import org.mickey.framework.common.database.Index;
import org.mickey.framework.common.database.Table;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
public interface Dialect {
    public static final String BLANK = "";

    String buildCreateTableClause(Table paramTable);

    String buildAddColumnClause(Column paramColumn);

    String buildUpdateColumnClause(Column paramColumn);

    String buildIndexClause(Index paramIndex);

    String buildPrimaryKeyClause(Class<?> paramClass);
}
