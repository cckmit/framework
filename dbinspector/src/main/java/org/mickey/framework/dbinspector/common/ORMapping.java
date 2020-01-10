package org.mickey.framework.dbinspector.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.mickey.framework.common.annotation.DbCustomUniqueConstraint;
import org.mickey.framework.common.annotation.DbCustomUniqueConstraints;
import org.mickey.framework.common.annotation.Sharding;
import org.mickey.framework.common.database.JoinColumn;
import org.mickey.framework.common.database.Table;
import org.mickey.framework.common.database.*;
import org.mickey.framework.common.po.BaseExtPo;
import org.mickey.framework.common.po.AbstractCommonPo;
import org.mickey.framework.common.util.DataType;
import org.mickey.framework.common.util.ReflectUtils;
import org.mickey.framework.common.util.ReflectionUtils;
import org.mickey.framework.common.util.StringUtil;

import javax.persistence.Column;
import javax.persistence.Index;
import javax.persistence.*;
import java.lang.annotation.Annotation;
import java.lang.invoke.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.sql.Types.LONGVARCHAR;

/**
 * description
 *
 * @author mickey
 * 05/07/2019
 */
@Slf4j
public class ORMapping {

    private static final Map<Class<?>, Table> orm = new ConcurrentHashMap<Class<?>, Table>();
    private static final Map<String, Class<?>> nameMap = new ConcurrentHashMap<String, Class<?>>();

    public static Class<?> getPoClazz(String sqlTableName){
        return nameMap.get(sqlTableName);
    }

    public static Table get(Class<?> clazz) {
        javax.persistence.Table clazzAnnotation = clazz.getAnnotation(javax.persistence.Table.class);
        if (clazzAnnotation == null) {
            return null;
        }
        Table jpaTable = orm.computeIfAbsent(clazz, k -> {
            Table table = new Table();
            table.setSimpleJavaName(k.getSimpleName());
            table.setJavaName(k.getName());
            javax.persistence.Table tableAnnotation = k.getAnnotation(javax.persistence.Table.class);
            if (tableAnnotation == null) {
                throw new RuntimeException("not jpa standard class:" + k.getName());
            }
            String name = tableAnnotation.name();
            if (StringUtils.isBlank(name)) {
                name = k.getSimpleName();
            }
            table.setSqlName(StringUtil.camelToUnderline(name));
            table.setCatalog(tableAnnotation.catalog());
            table.setSchema(tableAnnotation.schema());

            List<Field> fields = ReflectUtils.getDeclaredFields(k);
            List<org.mickey.framework.common.database.Column> columns = fields.stream().filter(f -> {
                Column columnAnnotation = f.getAnnotation(Column.class);
                return columnAnnotation != null;
            }).map(field -> {
                Column columnAnnotation = field.getAnnotation(Column.class);
                org.mickey.framework.common.database.Column column = new org.mickey.framework.common.database.Column(table);
                column.setJavaName(field.getName());
                column.setJavaTypeName(field.getType().getName());
                column.setJavaType(field.getType());
                column.setPk(field.getAnnotation(Id.class) != null);
                // TODO set FK
                column.setFk(false);
                column.setLength(columnAnnotation.length());
                column.setScale(columnAnnotation.scale());
                column.setPrecision(columnAnnotation.precision());
                column.setNullable(columnAnnotation.nullable());
                column.setUnique(columnAnnotation.unique());
                column.setUpdatable(columnAnnotation.updatable());
                column.setInsertable(columnAnnotation.insertable());

                JdbcType jdbcType = autoGuessJdbcType(field.getType());
                column.setSqlType(jdbcType.TYPE_CODE);
                column.setSqlTypeName(jdbcType.name());

                String columnName = columnAnnotation.name();
                if (StringUtils.isBlank(columnName)) {
                    columnName = StringUtil.camelToUnderline(field.getName());
                }
                column.setSqlName(columnName);
                String definition = columnAnnotation.columnDefinition();
                if (StringUtils.isNotBlank(definition)) {
                    column.setColumnDefinition(definition);
                }
                if (table.getIndices() != null) {
                    column.setIndexed(table.getIndices().stream().anyMatch(index -> index.getColumnList().contains(column.getSqlName())));
                }
                if (field.getAnnotation(Version.class) != null) {
                    table.setVersionColumn(column);
                }
                return column;
            }).collect(Collectors.toList());
            if (null == columns || columns.size() == 0) {
                columns = getAnnotationsFromGetMethod(k, table);
            } else {
                columns.addAll(getAnnotationsFromGetMethod(k, table));
            }
            columns.forEach(column -> {
                if (column.isPk()) {
                    table.addColumn(column);
                }
            });
            columns.forEach(column -> {
                if (!column.isPk()) {
                    table.addColumn(column);
                }
            });

            Index[] indexes = tableAnnotation.indexes();
            Arrays.stream(indexes).forEach(index -> {
                String indexName = index.name();
                String columnList = index.columnList();
                boolean unique = index.unique();
                org.mickey.framework.common.database.Index tableIndex = new org.mickey.framework.common.database.Index(table);
                tableIndex.setName(indexName);
                tableIndex.setColumnList(columnList);
                tableIndex.setUnique(unique);
                tableIndex.setTable(table);
                table.addIndex(tableIndex);
                if (unique) {
                    String[] split = columnList.split(",");
                    buildConstraint(table, indexName, split);
                }
            });

            UniqueConstraint[] uniqueConstraints = tableAnnotation.uniqueConstraints();
            Arrays.stream(uniqueConstraints).forEach(c -> {
                String[] columnNames = c.columnNames();
                buildConstraint(table, c.name(), columnNames);
            });

            DbCustomUniqueConstraints uqc = k.getAnnotation(DbCustomUniqueConstraints.class);
            if (uqc != null) {
                DbCustomUniqueConstraint[] constraints = uqc.constraints();
                buildCustomConstraints(table, k, constraints);
            }

            Sharding sharding = k.getAnnotation(Sharding.class);
            if (sharding != null) {
                Set<String> columnSet = Arrays.stream(sharding.columns()).collect(Collectors.toSet());
                List<org.mickey.framework.common.database.Column> shardingColumns = table.getColumns().stream().filter(r -> columnSet.contains(r.getSqlName())).collect(Collectors.toList());
                if (shardingColumns != null && shardingColumns.size() > 0) {
                    table.setSharding(true);
                    table.setShardingCount(sharding.count());
                    table.setShardingAlias(sharding.alias());
                    shardingColumns.forEach(s -> s.setSharding(true));
                } else {
                    log.error("error config");
                }
            }

            fields.stream().filter(f -> f.getAnnotation(OneToOne.class) != null).forEach(field -> {
                OneToOne oneToOne = field.getAnnotation(OneToOne.class);
                Class targetEntity = oneToOne.targetEntity();
                if (targetEntity == void.class) {
                    targetEntity = field.getType();
                }
                Join join = new Join(Relation.OneToOne, field.getName(), targetEntity);
                createJoin(table, field, targetEntity, join);
            });

            fields.stream().filter(f -> f.getAnnotation(ManyToOne.class) != null).forEach(field -> {
                ManyToOne manyToOne = field.getAnnotation(ManyToOne.class);
                Class targetEntity = manyToOne.targetEntity();
                if (targetEntity == void.class) {
                    targetEntity = field.getType();
                }
                Join join = new Join(Relation.ManyToOne, field.getName(), targetEntity);
                createJoin(table, field, targetEntity, join);
            });

            fields.stream().filter(f -> f.getAnnotation(OneToMany.class) != null).forEach(field -> {
                OneToMany oneToMany = field.getAnnotation(OneToMany.class);
                Class targetEntity = oneToMany.targetEntity();
                if (targetEntity == void.class) {
                    targetEntity = (Class) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                }
                javax.persistence.Table targetTable = (javax.persistence.Table) targetEntity.getAnnotation(javax.persistence.Table.class);
                if (targetTable == null) {
                    return;
                }
                Join join = new Join(Relation.OneToMany, field.getName(), targetEntity);
                join.setTargetTableName(targetTable.name());
                String mappedBy = oneToMany.mappedBy();
                if (StringUtils.isNotBlank(mappedBy)) {
                    Field targetField = ReflectionUtils.getDeclaredField(targetEntity, mappedBy);
                    if (targetField == null) {
                        throw new RuntimeException("can't find targetField of : " + mappedBy + " from class : " + targetEntity.getName());
                    }
                    List<JoinColumn> joinColumns = getJoinColumns(targetField);
                    switchJoinColumn(table, join, joinColumns);
                } else {
                    List<Field> declaredFields = ReflectionUtils.getDeclaredFields(targetEntity);
                    List<JoinColumn> joinColumns = new ArrayList<>();
                    Field targetFieldByType = declaredFields.stream().filter(df -> df.getType().getCanonicalName().equals(clazz.getCanonicalName())).findFirst().orElse(null);
                    if (targetFieldByType == null) {
                        joinColumns = getJoinColumns(field);
                    } else {
                        joinColumns = getJoinColumns(targetFieldByType);
                    }
                    switchJoinColumn(table, join, joinColumns);
                }
            });
            if (ReflectionUtils.isSubClass(clazz, BaseExtPo.class)) {
                table.setExtTable(true);
            }
            return table;
        });
        String sqlTableName = jpaTable.getSqlName();
        nameMap.putIfAbsent(sqlTableName, clazz);
        return jpaTable;
    }

    private static void switchJoinColumn(Table table, Join join, List<JoinColumn> joinColumns) {
        if (CollectionUtils.isNotEmpty(joinColumns)) {
            joinColumns.forEach(joinColumn -> {
                String columnName = joinColumn.getName();
                String referencedColumnName = joinColumn.getReferencedColumnName();
                joinColumn.setName(columnName);
                joinColumn.setReferencedColumnName(referencedColumnName);
            });
            join.setJoinColumns(joinColumns);
            table.addJoin(join);
        }
    }

    private static void createJoin(Table table, Field field, Class targetEntity, Join join) {
        List<JoinColumn> joinColumns = getJoinColumns(field);
        if (CollectionUtils.isNotEmpty(joinColumns)) {
            javax.persistence.Table targetTable = (javax.persistence.Table) targetEntity.getAnnotation(javax.persistence.Table.class);
            if (targetTable == null) {
                return;
            }
            join.setTargetTableName(targetTable.name());
            join.setJoinColumns(joinColumns);
            table.addJoin(join);
        }
    }

    private static List<JoinColumn> getJoinColumns(Field field) {
        List<JoinColumn> columns = new ArrayList<>();
        JoinColumns joinColumns = field.getAnnotation(JoinColumns.class);
        if (joinColumns == null) {
            javax.persistence.JoinColumn joinColumn = field.getAnnotation(javax.persistence.JoinColumn.class);
            if (joinColumn == null) {
                return null;
            }
            String joinName = joinColumn.name();
            if (StringUtils.isNotBlank(joinColumn.referencedColumnName())) {
                columns.add(new JoinColumn(joinName, joinColumn.referencedColumnName()));
            } else {
                columns.add(new JoinColumn(joinName));
            }
        } else {
            javax.persistence.JoinColumn[] values = joinColumns.value();
            if (values.length > 0) {
                for (javax.persistence.JoinColumn value : values) {
                    String joinName = value.name();
                    if (StringUtils.isNotBlank(value.referencedColumnName())) {
                        columns.add(new JoinColumn(joinName, value.referencedColumnName()));
                    } else {
                        columns.add(new JoinColumn(joinName));
                    }
                }
            }
        }
        return columns;
    }

    private static void buildConstraint(Table table, String indexName, String[] sqlColumnNames) {
        List<org.mickey.framework.common.database.Column> allColumns = table.getColumns();
        List<org.mickey.framework.common.database.Column> constrainColumns = new ArrayList<>();
        for (String sqlColumnName : sqlColumnNames) {
            Optional<org.mickey.framework.common.database.Column> any = allColumns.stream().filter(c -> c.getSqlName().equals(StringUtils.trim(sqlColumnName))).findAny();
            if (any.isPresent()) {
                constrainColumns.add(any.get());
            } else {
                throw new NoColumnFoundForUniqueException(indexName, sqlColumnName);
            }
        }
        if (CollectionUtils.isNotEmpty(constrainColumns)) {
            UqConstraint uqConstraint = new UqConstraint();
            uqConstraint.setName(indexName);
            for (org.mickey.framework.common.database.Column constraintColumn : constrainColumns) {
                uqConstraint.addColumn(constraintColumn);
            }
            table.addUqConstraint(uqConstraint);
        } else {
            log.warn("no column included for constraint {}", indexName);
        }
    }

    private static void buildCustomConstraints(Table table, Class<?> k, DbCustomUniqueConstraint[] constraints) {
        if (constraints != null && constraints.length == 0) {
            return;
        }
        if (constraints != null) {
            for (DbCustomUniqueConstraint constraint : constraints) {
                Optional<UqConstraint> uqConstraint = buildCustomConstraint(k, constraint);
                if (uqConstraint.isPresent()) {
                    table.addUqConstraint(uqConstraint.get());
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static Optional<UqConstraint> buildCustomConstraint(Class<?> targetClass, DbCustomUniqueConstraint constraint) {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        String name = constraint.name();
        String valueGetter = constraint.valueGetter();
        UqConstraint result = null;
        try {
            MethodHandle getterHandle = lookup.findVirtual(targetClass, valueGetter, MethodType.methodType(String.class));
            CallSite valueCallSite = LambdaMetafactory.metafactory(lookup,
                    "apply",
                    MethodType.methodType(Function.class),
                    MethodType.methodType(Object.class, Object.class),
                    getterHandle,
                    MethodType.methodType(String.class, targetClass));

            Function<AbstractCommonPo, String> function = (Function<AbstractCommonPo, String>) valueCallSite.getTarget().invokeExact();
            result = new UqConstraint();
            result.setName(name);
            result.setLambda(function);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            log.error("lookup error", e);
        } catch (LambdaConversionException e) {
            log.error("unable to convert error", e);
        } catch (Throwable throwable) {
            log.error("unable to invoke", throwable);
        }
        return Optional.ofNullable(result);
    }

    private static JdbcType autoGuessJdbcType(Class<?> fieldType) {
        int dataType = DataType.getDataType(fieldType);
        if (DataType.isSimpleType(dataType)) {
            switch (dataType) {
                case DataType.DT_Byte:
                case DataType.DT_byte:
                case DataType.DT_Short:
                case DataType.DT_short:
                case DataType.DT_Boolean:
                case DataType.DT_boolean:
                    return JdbcType.TINYINT;
                case DataType.DT_int:
                case DataType.DT_Integer:
                    return JdbcType.INTEGER;
                case DataType.DT_Long:
                case DataType.DT_long:
                case DataType.DT_BigInteger:
                    return JdbcType.BIGINT;
                case DataType.DT_Double:
                case DataType.DT_double:
                case DataType.DT_BigDecimal:
                    return JdbcType.DECIMAL;
                case DataType.DT_Float:
                case DataType.DT_float:
                    return JdbcType.FLOAT;
                case DataType.DT_Date:
                case DataType.DT_DateTime:
                    return JdbcType.TIMESTAMP;
                default:
                    return JdbcType.VARCHAR;
            }
        } else if (dataType == DataType.DT_ENUM) {
            return JdbcType.VARCHAR;
        } else {
            return JdbcType.BLOB;
        }
    }

    private static List<org.mickey.framework.common.database.Column> getAnnotationsFromGetMethod(final Class clazz, Table table) {
        String methodName = "get";
        List<org.mickey.framework.common.database.Column> columns = new ArrayList<>();
        for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
            Method[] methods = superClass.getDeclaredMethods();
            for (Method method : methods) {
                if (!method.getName().startsWith(methodName)) {
                    continue;
                }
                org.mickey.framework.common.database.Column column = new org.mickey.framework.common.database.Column(table);
                column.setPk(false);
                Annotation[] annotations = method.getDeclaredAnnotations();
                Column columnAnnotation = null;
                for (Annotation annotation : annotations) {
                    if (Column.class.getName().equals(annotation.annotationType().getName())) {
                        columnAnnotation = (Column) annotation;
                    }
                    if (Id.class.equals(annotation.annotationType())) {
                        column.setPk(true);
                    }
                    if (Version.class.equals(annotation.annotationType())) {
                        table.setVersionColumn(column);
                    }
                }
                if (null == columnAnnotation) {
                    continue;
                }
                String fieldName = StringUtils.replace(method.getName(), methodName, StringUtils.EMPTY);
                fieldName = StringUtils.uncapitalize(fieldName);
                column.setJavaName(fieldName);
                column.setJavaTypeName(method.getReturnType().getName());
                column.setJavaType(method.getReturnType());
                // TODO set FK
                column.setFk(false);

                JdbcType jdbcType = autoGuessJdbcType(method.getReturnType());
                if (String.class.equals(method.getReturnType()) && LONGVARCHAR == columnAnnotation.length()) {
                    column.setSqlType(LONGVARCHAR);
                } else {
                    column.setLength(columnAnnotation.length());
                }
                column.setScale(columnAnnotation.scale());
                column.setPrecision(columnAnnotation.precision());
                column.setNullable(columnAnnotation.nullable());
                column.setUnique(columnAnnotation.unique());
                column.setUpdatable(columnAnnotation.updatable());
                column.setInsertable(columnAnnotation.insertable());
                column.setSqlType(jdbcType.TYPE_CODE);
                column.setSqlTypeName(jdbcType.name());

                String sqlName = columnAnnotation.name();
                if (StringUtils.isBlank(sqlName)) {
                    sqlName = StringUtil.camelToUnderline(fieldName);
                }
                column.setSqlName(sqlName);
                String definition = columnAnnotation.columnDefinition();
                if (StringUtils.isNotBlank(definition)) {
                    column.setColumnDefinition(definition);
                }
                if (table.getIndices() != null) {
                    column.setIndexed(table.getIndices().stream().anyMatch(index -> index.getColumnList().contains(column.getSqlName())));
                }
                columns.add(column);
            }
        }
        return columns;
    }
}
