package com.global.common.cache.redis;

import org.springframework.stereotype.Service;

/**
 * 有序集合结构缓存处理类
 * @Author: 鲁砚琨
 * @CreateTime: 2020-01-14 下午 03:15
 * @Version: v1.0
 */
@Service
public class RedisZSetService extends RedisService {

    /**
     * 有序集合，通过key添加元素
     * @param key 键
     * @param value 值
     * @param score 权重值
     */
    public boolean add(String key, Object value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 有序集合，增加元素的权重值
     * @param key 键
     * @param value 元素值
     * @param delta 新的权重值
     */
    public double incrementScore(String key, Object value, double delta) {
        Double incrementScore = redisTemplate.opsForZSet().incrementScore(key, value, delta);
        return incrementScore != null ? incrementScore : DEFAULT_RETURN_VALUE.doubleValue();
    }

    /**
     * 有序集合，删除key下的指定值
     * @param key 键
     * @param values 值
     */
    public long remove(String key, Object... values) {
        Long remove = redisTemplate.opsForZSet().remove(key, values);
        return remove != null ? remove : DEFAULT_RETURN_VALUE.longValue();
    }

    /**
     * 有序集合，获取集合大小
     * @param key 键
     */
    public long size(String key) {
        Long size = redisTemplate.opsForZSet().size(key);
        return size != null ? size : DEFAULT_RETURN_VALUE.longValue();
    }
}
