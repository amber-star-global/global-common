package com.global.common.cache.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 通用缓存处理类
 * @Author: 鲁砚琨
 * @CreateTime: 2020-01-14 下午 02:09
 * @Version: v1.0
 */
@Service(value = "redisService")
public class RedisBasicService {

    @Autowired
    protected RedisTemplate redisTemplate;

    /**
     * 设置默认返回值
     */
    final Number DEFAULT_RETURN_VALUE = 0;

    /**
     * 是否存在Key
     * @param key 键
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 模糊匹配key
     * @param key 键
     */
    public Set<String> keys(String key) {
        return redisTemplate.keys(key);
    }

    /**
     * 设置过期时时间
     * @param key 键
     * @param timeout 超时时间
     * @param unit 计量单位
     */
    public boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 根据Key删除缓存
     * @param key 键
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 批量删除缓存
     * @param keys 键
     */
    public void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }
}
