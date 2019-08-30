package org.mickey.framework.core.mybatis.sharding.table;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.core.consistenthash.ConsistentHashNode;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Data
public class TableNode implements ConsistentHashNode {

    private String tableName;
    private int index;

    public TableNode(String tableName, int index) {
        this.tableName = tableName;
        this.index = index;
    }

    @Override
    public String hashString() {
        return "" + index;
    }
}
