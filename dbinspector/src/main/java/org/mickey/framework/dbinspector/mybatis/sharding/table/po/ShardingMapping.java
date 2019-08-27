package org.mickey.framework.dbinspector.mybatis.sharding.table.po;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Objects;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Data
@Table(name = "t_sharding_mapping",indexes = {@Index(name = "idx_sharding_mapping",columnList = "sharding_value,table_name")})
public class ShardingMapping {
    /**
     * 原表的名称
     */
    @Column
    private String tableName;
    /**
     * 分表键
     */
    @Column
    private String shardingKey;
    /**
     * 分表值
     */
    @Column
    private String shardingValue;
    /**
     * 分表的名称
     */
    @Column
    private String shardingTableName;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShardingMapping)) {
            return false;
        }
        ShardingMapping that = (ShardingMapping) o;
        return Objects.equals(tableName, that.tableName) &&
                Objects.equals(shardingKey, that.shardingKey) &&
                Objects.equals(shardingValue, that.shardingValue) &&
                Objects.equals(shardingTableName, that.shardingTableName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(tableName, shardingKey, shardingValue, shardingTableName);
    }

}
