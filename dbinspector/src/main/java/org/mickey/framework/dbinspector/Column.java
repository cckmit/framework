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
public class Column {
    /**
     * Reference to the containing table
     */
    private final Table table;
    /**
     * The java.sql.Types type
     */
    private final int sqlType;
    /**
     * The sql typename. provided by JDBC driver
     */
    @SuppressWarnings("unused")
    private final String sqlTypeName;
    /**
     * The name of the column
     */
    private final String sqlName;
    /**
     * @todo-javadoc Describe the column
     */
    private final int size;
    /**
     * @todo-javadoc Describe the column
     */
    private final int decimalDigits;
    /**
     * True if the column is nullable
     */
    private final boolean nullable;
    /**
     * True if the column is indexed
     */
    private final boolean indexed;
    /**
     * True if the column is unique
     */
    private final boolean unique;
    /**
     * Null if the DB reports no default value
     */
    private final String defaultValue;
    /**
     * True if the column is a primary key
     */
    private boolean primaryKey;
    /**
     * True if the column is a foreign key
     */
    private boolean foreignKey;
    // 每列的别名
    private String comment = "";

    /**
     * Describe what the DbColumn constructor does
     *
     * @param table         Describe what the parameter does
     * @param sqlType       Describe what the parameter does
     * @param sqlTypeName   Describe what the parameter does
     * @param sqlName       Describe what the parameter does
     * @param size          Describe what the parameter does
     * @param decimalDigits Describe what the parameter does
     * @param isPk          Describe what the parameter does
     * @param isNullable    Describe what the parameter does
     * @param isIndexed     Describe what the parameter does
     * @param defaultValue  Describe what the parameter does
     * @param isUnique      Describe what the parameter does
     */
    public Column(Table table, int sqlType, String sqlTypeName, String sqlName,
                  int size, int decimalDigits, boolean isPk, boolean isNullable,
                  boolean isIndexed, boolean isUnique, String defaultValue) {
        this.table = table;
        this.sqlType = sqlType;
        this.sqlName = sqlName;
        this.sqlTypeName = sqlTypeName;
        this.size = size;
        this.decimalDigits = decimalDigits;
        primaryKey = isPk;
        nullable = isNullable;
        indexed = isIndexed;
        unique = isUnique;
        this.defaultValue = defaultValue;
        log.debug(sqlName + " isPk -> " + primaryKey);

    }
}
