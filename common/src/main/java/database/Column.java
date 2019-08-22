package database;

import lombok.Data;
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
public class Column implements Comparable<Column> {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private Table table;
    private int sqlType;
    private String sqlTypeName;
    private String sqlName;
    private int length;
    private int precision;
    private int scale;
    private boolean nullable;
    private boolean indexe;
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
        Column column = (Column)o;
        return Objects.equals(sqlName, column.sqlName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sqlName);
    }
}
