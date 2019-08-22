package org.mickey.framework.common.database;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mickey.framework.common.po.CommonPo;
import org.springframework.util.Assert;

import java.util.TreeSet;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * description
 *
 * @author mickey
 * 05/07/2019
 */
@Slf4j
@Data
public class UqConstraint {
    public static final String Post_Table_Fix = "_framework_unique";

    private String name;
    private Pattern pattern;
    private TreeSet<String> javaColumnNames = new TreeSet<>();
    private TreeSet<Column> columns = new TreeSet<>();
    private Function<CommonPo, String> lambda;

    public static String getUniqueTableName(Table table) {
        return table.getSqlName() + UqConstraint.Post_Table_Fix;
    }

    public void setName(String name) {
        Assert.isTrue(StringUtils.isNotBlank(name), "uq name cannot be null");
        this.name = name;
        pattern = Pattern.compile(name + "-");
    }

    public void addColumn(Column column) {
        if (columns.contains(column)) {
            log.error("repeated column definition for index {}, column name {}", name, column.getSqlName());
        }
        columns.add(column);
        javaColumnNames.add(column.getJavaName());
    }

    public boolean isCustom() {
        return lambda != null;
    }

    public boolean hasJavaColumn(String javaName) {
        return javaColumnNames.contains(javaName);
    }
}
