package database;

import lombok.Data;
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
public class Table {
    private Logger logger = LoggerFactory.getLogger(getClass());

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
}
