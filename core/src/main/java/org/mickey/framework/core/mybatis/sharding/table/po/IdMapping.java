package org.mickey.framework.core.mybatis.sharding.table.po;

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
@Table(name = "t_id_mapping",indexes = {@Index(name = "idx_id_mapping",columnList = "id,table_name")})
public class IdMapping {
    /**
     * 原始数据的主键
     */
    @Column(length = 50)
    private String id;
    /**
     * 表名
     */
    @Column()
    private String tableName;
    /**
     * 分表键
     */
    @Column()
    private String shardingKey;
    /**
     * 分表键对应的值
     */
    @Column()
    private String shardingValue;
    /**
     * 分表的名称
     */
    @Column()
    private String shardingTableName;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IdMapping)) {
            return false;
        }
        IdMapping idMapping = (IdMapping) o;
        return Objects.equals(id, idMapping.id) &&
                Objects.equals(shardingTableName, idMapping.shardingTableName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shardingTableName);
    }
}
