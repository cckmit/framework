package org.mickey.framework.core.mybatis.sharding;

import org.apache.commons.lang3.StringUtils;
import org.mickey.framework.common.database.Table;
import org.mickey.framework.core.consistenthash.ConsistentHash;
import org.mickey.framework.core.mybatis.sharding.table.TableConsistentHash;
import org.mickey.framework.core.mybatis.sharding.table.TableNode;
import org.mickey.framework.core.mybatis.sharding.table.po.IdMapping;
import org.mickey.framework.dbinspector.mybatis.sharding.table.po.ShardingMapping;

import java.util.Map;
import java.util.Set;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
public interface ShardingManager {
    /**
     * 保存idMapping 信息
     *
     * @param shardingMappings 保存shardingValue 和 shardingTable的映射
     * @return 记录数量
     */
    int saveShardingMappings(ShardingMapping... shardingMappings);

    /**
     * 保存主键和shardingValue的映射
     *
     * @param idMappings idMappings
     * @return 记录数量
     */
    int saveIdMappings(IdMapping... idMappings);

    /**
     * 获取sharing信息
     *
     * @param table         表元数据描述信息
     * @param shardingValue 分表键对应的值
     * @return 分片配置信息
     */
    String getShardingTableNameByValue(Table table, String shardingValue);

    /**
     * 根据主键获取分表的名称
     *
     * @param table 表元数据描述信息
     * @param id    主键
     * @return 分表名称
     */
    String getShardingTableNameById(Table table, String id);

    /**
     * 批量获取
     *
     * @param table          表元数据描述信息
     * @param shardingValues 多个分表键对应的值
     * @return 分表名称键值对
     */
    Map<String, String> getShardingTableNameByValues(Table table, Set<String> shardingValues);

    /**
     * 批量获取
     *
     * @param table 表元数据描述信息
     * @param ids   多个Id
     * @return 分表名称键值对 <id, shardingTable>
     */
    Map<String, String> getShardingTableNameByIds(Table table, Set<String> ids);

    /**
     * 利用一致性hash算法进行分表
     *
     * @param table         表名对象配置
     * @param shardingValue 分表键的值
     * @return 分表后的表名
     */
    default String shardingTable(Table table, String shardingValue) {
        //一致hash分表
        ConsistentHash<TableNode> consistentHash;
        if (StringUtils.isNotBlank(table.getShardingAlias())) {
            consistentHash = TableConsistentHash.getOrCreate(table.getShardingAlias(), table.getShardingCount());
        } else {
            consistentHash = TableConsistentHash.getOrCreate(table.getSqlName(), table.getShardingCount());
        }
        TableNode tableNode = consistentHash.get(shardingValue);
        return table.getSqlName() + "_" + tableNode.getIndex();
    }
}
