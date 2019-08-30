package org.mickey.framework.common.database;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 05/07/2019
 */
@Data
@Slf4j
public class Table {

    private String schema;
    private String catalog;
    private String sqlName;
    private String comment;
    private String ownerSynonymName;
    private List<Column> columns;
    private List<Index> indices;
    private Map<String, UqConstraint> uqConstraintMap = new HashMap<>();
    private Column versionColumn;
    private ForeignKeys exportKeys;
    private ForeignKeys importKeys;
    private String simpleJavaName;
    private String javaName;
    private boolean sharding;
    private String shardingAlias;
    private int shardingCount;
    private Database database;
    private boolean isExtTable;
    private List<Join> joins;

    public void addColumn(Column column) {
        if (this.columns == null) {
            this.columns = new ArrayList<>();
        }
        this.columns.add(column);
    }

    public void addIndex(Index index) {
        if (this.indices == null) {
            this.indices = new ArrayList<>();
        }
        this.indices.add(index);
    }

    public void addJoin(Join join) {
        if (this.joins == null) {
            this.joins = new ArrayList<>();
        }
        this.joins.add(join);
    }

    public void addUqConstraint(UqConstraint uqConstraint) {
        if (this.uqConstraintMap.containsKey(uqConstraint)) {
            throw new DuplicateUniqueConstaint(uqConstraint.getName());
        }
        if (log.isDebugEnabled()) {
            log.debug("add constraint {} for table {}", uqConstraint.getName(), getSqlName());
        }
        this.uqConstraintMap.put(uqConstraint.getName(), uqConstraint);
    }

    public boolean hasVersionColumn() {
        return versionColumn != null;
    }

    public boolean hasUniqueCOnstraint() {
        return  uqConstraintMap.size() > 0;
    }
}
