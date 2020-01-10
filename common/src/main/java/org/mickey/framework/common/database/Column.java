package org.mickey.framework.common.database;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * description
 *
 * @author mickey
 * 05/07/2019
 */
@Data
@Slf4j
public class Column implements Comparable<Column> {

    private Table table;
    private int sqlType;
    private String sqlTypeName;
    private String sqlName;
    private int length;
    private int precision;
    private int scale;
    private boolean nullable;
    private boolean indexed;
    private boolean unique;
    private boolean insertable = true;
    private boolean updatable = true;
    private String defaultValue;
    private boolean isSharding;
    private boolean isPk;
    private boolean isFk;
    private String sqlComment;
    private String javaTypeName;
    private Class<?> javaType;
    private String javaName;
    private String columnDefinition;

    public Column(Table table) {
        this.table = table;
    }

    public void copyFrom(Column column) {
        this.sqlType = column.getSqlType();
        this.sqlTypeName = column.getSqlTypeName();
        this.sqlName = column.getSqlName();
        this.length = column.getLength();
        this.precision = column.getPrecision();
        this.scale = column.getScale();
        this.nullable = column.isNullable();
        this.indexed = column.isIndexed();
        this.unique = column.isUnique();
        this.insertable = column.isInsertable();
        this.updatable = column.isUpdatable();
        this.defaultValue = column.getDefaultValue();
        this.isSharding = column.isSharding();
        this.isPk = column.isPk();
        this.isFk = column.isFk();
        this.sqlComment = column.getSqlComment();
        this.javaTypeName = column.getJavaTypeName();
        this.javaType = column.getJavaType();
        this.javaName = column.getJavaName();
        this.columnDefinition = column.getColumnDefinition();
    }

    @Override
    public int compareTo(Column o) {
        return this.javaName.compareTo(o.javaName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Column column = (Column) o;
        return Objects.equals(sqlName, column.sqlName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sqlName);
    }

    @Override
    public String toString() {
        return "Column{" +
                "sqlType=" + sqlType +
                ", sqlTypeName='" + sqlTypeName + '\'' +
                ", sqlName='" + sqlName + '\'' +
                ", length=" + length +
                ", precision=" + precision +
                ", scale=" + scale +
                ", nullable=" + nullable +
                ", indexed=" + indexed +
                ", unique=" + unique +
                ", insertable=" + insertable +
                ", updatable=" + updatable +
                ", defaultValue='" + defaultValue + '\'' +
                ", isSharding=" + isSharding +
                ", isPk=" + isPk +
                ", isFk=" + isFk +
                ", sqlComment='" + sqlComment + '\'' +
                ", javaTypeName='" + javaTypeName + '\'' +
                ", javaType=" + javaType +
                ", javaName='" + javaName + '\'' +
                ", columnDefinition='" + columnDefinition + '\'' +
                '}';
    }
}
