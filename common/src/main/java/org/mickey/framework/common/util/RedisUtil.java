package org.mickey.framework.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 工具类
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Repository
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }


    /**
     * 设置有效时间
     *
     * @param key
     * @param value
     * @param time
     * @param timeUnit
     */
    public void set(String key, Object value, long time, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }


    /**
     * 创建Set类型数据
     *
     * @param key
     * @param datas
     */
    public void buildSetData(String key, HashSet<Object> datas) {
        redisTemplate.opsForSet().add(key, datas);
    }

    /**
     * 创建Set类型数据
     *
     * @param key
     * @param data
     */
    public void buildSetData(String key, Object data) {
        redisTemplate.opsForSet().add(key, data);
    }

    /**
     * 创建ZSet类型数据
     *
     * @param key
     * @param tuples
     */
    public void buildZsetData(String key, Set<ZSetOperations.TypedTuple<Object>> tuples) {
        redisTemplate.opsForZSet().add(key, tuples);
    }


    /**
     * 创建ZSet类型数据
     *
     * @param key
     * @param weigth
     * @param data
     */
    public void buildZsetData(String key, double weigth, Object data) {
        redisTemplate.opsForZSet().add(key, data, weigth);
    }

    /**
     * 创建Hash类型数据
     *
     * @param key
     * @param datas
     */
    public void buildHashData(String key, Map<String, Object> datas) {
        redisTemplate.opsForHash().putAll(key, datas);
    }

    /**
     * 创建Hash类型数据
     *
     * @param key
     * @param data
     */
    public void buildHashData(String key, String dateKey, Object data) {
        redisTemplate.opsForHash().put(key, dateKey, data);
    }


    /**
     * 判断是否存在元素
     *
     * @param key
     * @param member
     * @return
     */
    public boolean isMember(String key, Object member) {
        return redisTemplate.opsForSet().isMember(key, member);
    }

    /**
     * 获取Set数据
     *
     * @param key
     * @return
     */
    public Set<Object> getSetValue(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 获取ZSet数据
     *
     * @param key
     * @return
     */
    public Set<ZSetOperations.TypedTuple<Object>> getFirstZsetValue(String key) {
        return redisTemplate.opsForZSet().rangeWithScores(key, 0, 0);
    }


    /**
     * 获取Map数据
     *
     * @param key
     * @return
     */
    public Map getMapValue(String key) {
        return redisTemplate.opsForHash().entries(key);
    }


    /**
     * @param key
     * @return
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }


    /**
     * @param key
     * @return
     */
    public Object get(String key, String... args) {
        String k = String.format(key, args);
        return redisTemplate.opsForValue().get(k);
    }

    /**
     * @param key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 删除key
     *
     * @param key
     * @param args
     */
    public void delete(String key, String... args) {
        String k = String.format(key, args);
        redisTemplate.delete(k);
    }

    /**
     * @param key
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }


    public Set keys(String pattern) {
        return redisTemplate.keys(pattern);
    }


}

