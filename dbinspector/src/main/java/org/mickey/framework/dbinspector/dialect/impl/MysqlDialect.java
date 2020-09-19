package org.mickey.framework.dbinspector.dialect.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mickey.framework.common.database.Column;
import org.mickey.framework.common.database.Index;
import org.mickey.framework.common.database.Table;
import org.mickey.framework.common.util.DataType;
import org.mickey.framework.dbinspector.dialect.Dialect;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class MysqlDialect implements Dialect {

    @Override
    public String buildCreateTableClause(Table paramTable) {
        StringBuilder builder = new StringBuilder();
        builder.append("create table `")
                .append(paramTable.getSqlName())
                .append("` (");
        StringBuilder primaryKeys = new StringBuilder();
        paramTable.getColumns().forEach(column -> {
            String columnName = column.getSqlName();
            builder.append("`")
                    .append(columnName)
                    .append("` ")
                    .append(getColumnDefinition(column))
                    .append(",");
            if (column.isPk()) {
                primaryKeys.append("`")
                        .append(columnName)
                        .append("`")
                        .append(",");
            }
        });
        if (primaryKeys.length() > 0) {
            primaryKeys.deleteCharAt(primaryKeys.length() - 1);
            builder.append(" primary key (")
                    .append(primaryKeys)
                    .append(")");
        } else {
            builder.deleteCharAt(builder.length() - 1);
        }
        builder.append(")");
        builder.append(" comment '");
        builder.append(org.mickey.framework.common.util.StringUtil.valueOf(paramTable.getComment()));
        builder.append("'");
        return builder.toString();
    }

    @Override
    public String buildAddColumnClause(Column paramColumn) {
        if (paramColumn == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("alter table ")
                .append(paramColumn.getTable().getSqlName())
                .append(" add ")
                .append(paramColumn.getSqlName())
                .append(" ")
                .append(getColumnDefinition(paramColumn));
        return builder.toString();
    }

    @Override
    public String buildUpdateColumnClause(Column paramColumn) {
        if (paramColumn == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("alter table ")
                .append(paramColumn.getTable().getSqlName())
                .append(" modify ")
                .append(paramColumn.getSqlName())
                .append(" ")
                .append(getColumnDefinition(paramColumn));
        return builder.toString();
    }

    @Override
    public String buildIndexClause(Index paramIndex) {
        if (paramIndex == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("create ")
                .append(paramIndex.isUnique() ? "unique" : " ")
                .append(" index ")
                .append(paramIndex.getName())
                .append(" on ")
                .append(paramIndex.getTable().getSqlName())
                .append("(")
                .append(paramIndex.getColumnList())
                .append(")");
        return builder.toString();
    }

    private String getColumnDefinition(Column column) {
        if (StringUtils.isNotBlank(column.getColumnDefinition())) {
            return column.getColumnDefinition();
        }
        String definition = "";
        int dataType = DataType.getDataType(column.getJavaType());
        if (DataType.isSimpleType(dataType)) {
            switch (dataType) {
                case DataType.DT_Byte:
                case DataType.DT_byte:
                case DataType.DT_Short:
                case DataType.DT_short:
                    definition = "tinyint";
                    break;
                case DataType.DT_Boolean:
                case DataType.DT_boolean:
                    definition = "tinyint(1)";
                    break;
                case DataType.DT_int:
                case DataType.DT_Integer:
                    definition = "int4";
                    break;
                case DataType.DT_Long:
                case DataType.DT_long:
                case DataType.DT_BigInteger:
                    definition = "bigint";
                    break;
                case DataType.DT_Double:
                case DataType.DT_double:
                    definition = "double";
                    break;
                case DataType.DT_BigDecimal:
                    definition = "decimal(" + column.getLength() + "," + column.getScale() + ")";
                    break;
                case DataType.DT_Float:
                case DataType.DT_float:
                    definition = "float";
                    break;
                case DataType.DT_char:
                case DataType.DT_Character:
                case DataType.DT_String:
                    definition = "varchar(" + column.getLength() + ")";
                    break;
                case DataType.DT_Date:
                case DataType.DT_DateTime:
                    definition = "timestamp";
                    break;
                default:
                    definition = "varchar(255)";
                    break;
            }
        } else if (DataType.DT_ENUM == dataType) {
            definition = "varchar(" + column.getLength() + ")";
        } else {
            definition = "text";
        }
        if (!column.isNullable()) {
            definition = definition + " not null";
        }
        if (column.getDefaultValue() != null) {
            definition = definition + " default '" + column.getDefaultValue() + "'";
        }
        if (StringUtils.isNotBlank(column.getSqlComment())) {
            definition += " comment '" + column.getSqlComment() + "'";
        }

        return definition;
    }

    @Override
    public String buildPrimaryKeyClause(Class<?> paramClass) {
        return null;
    }
}
