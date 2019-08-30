package org.mickey.framework.core.mybatis.sharding.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mickey.framework.common.database.Table;
import org.mickey.framework.core.mybatis.sharding.ShardingManager;
import org.mickey.framework.core.mybatis.sharding.table.po.IdMapping;
import org.mickey.framework.dbinspector.mybatis.sharding.table.po.ShardingMapping;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class RedisManager implements ShardingManager {

    private Charset charset = Charset.forName("UTF8");
    private StringRedisTemplate redisTemplate;

    @Override
    public int saveShardingMappings(ShardingMapping... shardingMappings) {
        if (shardingMappings == null || shardingMappings.length == 0) {
            return 0;
        }
        Set<ShardingMapping> collect = Arrays.stream(shardingMappings).collect(Collectors.toSet());
        AtomicInteger count = new AtomicInteger(0);
        collect.parallelStream().forEach(c -> {
            String cacheKey = buildShardingCacheKey(c.getTableName(), c.getShardingValue());
            Map<String, String> shardingMap = new HashMap<>();
            shardingMap.put("tableName", c.getTableName());
            shardingMap.put("shardingKey", c.getShardingKey());
            shardingMap.put("shardingValue", c.getShardingValue());
            shardingMap.put("shardingTableName", c.getShardingTableName());
            redisTemplate.opsForHash().putAll(cacheKey, shardingMap);
//			cacheFacade.setMap(cacheKey, shardingMap);
            count.getAndAdd(1);
        });
        return count.get();
    }

    @Override
    public int saveIdMappings(IdMapping... idMappings) {
        if (idMappings == null || idMappings.length == 0) {
            return 0;
        }
        List<IdMapping> collect = Arrays.stream(idMappings).collect(Collectors.toList());
        int rows = doSaveIdMappingsByPipeline(collect);
        return rows;
    }

    private int doSaveIdMappingsByPipeline(List<IdMapping> collect) {
        redisTemplate.executePipelined((RedisCallback<String>) connection -> {
            connection.openPipeline();
            for (IdMapping c : collect) {
                String cacheKey = buildIdCacheKey(c.getTableName(), c.getId());
                Map<byte[], byte[]> shardingMap = new HashMap<>();
                shardingMap.put("id".getBytes(charset), c.getId().getBytes(charset));
                shardingMap.put("tableName".getBytes(charset), c.getTableName().getBytes(charset));
                shardingMap.put("shardingKey".getBytes(charset), c.getShardingKey().getBytes(charset));
                shardingMap.put("shardingValue".getBytes(charset), c.getShardingValue() == null ? new byte[0] : c.getShardingValue().getBytes(charset));
                shardingMap.put("shardingTableName".getBytes(charset), c.getShardingTableName().getBytes(charset));
                connection.hMSet(cacheKey.getBytes(charset),shardingMap);
            }
            return null;
        });
        return collect.size();
    }

    private String buildShardingCacheKey(String tableName, String shardingValue) {
        return "sharding:valueMapping:" + tableName + ":" + shardingValue;
    }

    private String buildIdCacheKey(String tableName, String id) {
        return "sharding:idMapping:" + tableName + ":" + id;
    }

    @Override
    public String getShardingTableNameByValue(Table table, String shardingValue) {
        String cacheKey = buildShardingCacheKey(table.getSqlName(), shardingValue);
        return (String) redisTemplate.opsForHash().get(cacheKey, "shardingTableName");
//		return cacheFacade.getField(cacheKey, "shardingTableName");
    }

    @Override
    public String getShardingTableNameById(Table table, String id) {
        String idCacheKey = buildIdCacheKey(table.getSqlName(), id);
        return (String) redisTemplate.opsForHash().get(idCacheKey, "shardingTableName");
//		return cacheFacade.getField(idCacheKey, "shardingTableName");
    }

    @Override
    public Map<String, String> getShardingTableNameByValues(Table table, Set<String> shardingValues) {
        Map<String, String> map = new HashMap<>();
        if (CollectionUtils.isEmpty(shardingValues)) {
            return map;
        }
        for (String shardingValue : shardingValues) {
            String shardingTableName = getShardingTableNameByValue(table, shardingValue);
            if (StringUtils.isNotBlank(shardingTableName)) {
                map.put(shardingValue, shardingTableName);
            }
        }
        return map;
    }

    @Override
    public Map<String, String> getShardingTableNameByIds(Table table, Set<String> ids) {
        Map<String, String> map = new HashMap<>();
        if (CollectionUtils.isEmpty(ids)) {
            return map;
        }
        for (String id : ids) {
            String shardingTableName = getShardingTableNameById(table, id);
            if (StringUtils.isNotBlank(shardingTableName)) {
                map.put(id, shardingTableName);
            }
        }
        return map;
    }

    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}

