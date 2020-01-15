package com.global.common.cache.redis;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 键值结构缓存处理类
 * @Author: 鲁砚琨
 * @CreateTime: 2020-01-14 下午 03:14
 * @Version: v1.0
 */
@Service(value = "redisValueService")
public class RedisValueService extends RedisBasicService {

    /**
     * 设置键值
     * @param key 键
     * @param value 值
     */
    public void put(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 批量设置键值
     * @param map key为键，value为值
     */
    public void multiSet(Map<String, Object> map) {
        redisTemplate.opsForValue().multiSet(map);
    }

    /**
     * 获取值
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 批量获取值
     * @param keys 键
     */
    public List<Object> multiGet(Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }
}
