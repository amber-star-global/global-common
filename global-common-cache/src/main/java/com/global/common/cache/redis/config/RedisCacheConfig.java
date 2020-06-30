package com.global.common.cache.redis.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-01-14 上午 11:49
 * @Version: v1.0
 */
@Slf4j
@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {

    /**
     * 访问地址
     */
    @Value(value = "${spring.redis.host}")
    private String host;

    /**
     * 端口号，默认：6379
     */
    @Value(value = "${spring.redis.port}")
    private int port;

    /**
     * 登录密码
     */
    @Value(value = "${spring.redis.password}")
    private String password;

    /**
     * 连接数据库，默认：0
     */
    @Value(value = "${spring.redis.database}")
    private int database;

    /**
     * 连接超时时间，毫秒
     */
    @Value(value = "${spring.redis.timeout}")
    private int timeout;

    /**
     * 连接池最大空闲连接，负值表示空闲不受限制，默认：8
     */
    @Value(value = "${spring.redis.pool.max-idle}")
    private int maxIdle;

    /**
     * 连接池最小空闲连接，值为正数有效，默认：0
     */
    @Value(value = "${spring.redis.pool.min-idle}")
    private int minIdle;

    /**
     * 连接池最大个数，负数表示不受限制，默认：8
     */
    @Value(value = "${spring.redis.pool.max-active}")
    private int maxTotal;

    /**
     * 连接池最大等待时间，负值表示无限期阻止，默认：-1
     */
    @Value(value = "${spring.redis.pool.max-wait}")
    private int maxWait;

    @Bean
    public CacheManager cacheManager(RedisTemplate<String, Object> redisTemplate) {
        return new RedisCacheManager(redisTemplate);
    }

    /**
     * redis模板配置
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(JedisPoolConfig poolConfig) {
        log.debug("设置redis模板。。。。");
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory(poolConfig));
        return redisTemplate;
    }

    @Bean
    public JedisPool poolFactory(JedisPoolConfig poolConfig) {
        log.debug("设置redis连接池。。。");
        return new JedisPool(poolConfig, host, port, timeout, password, database);
    }

    /**
     * 连接工厂配置
     */
    @Bean
    public RedisConnectionFactory connectionFactory(JedisPoolConfig poolConfig) {
        log.debug("创建redis连接工厂。。。");
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName(host);
        factory.setPort(port);
        factory.setDatabase(database);
        factory.setPassword(password);
        factory.setPoolConfig(poolConfig);
        factory.afterPropertiesSet();
        log.debug("连接工厂配置参数, host: {}, port: {}, database: {}, password: {}", host, port, database, password);
        return factory;
    }

    /**
     * 连接池配置
     */
    @Bean
    public JedisPoolConfig poolConfig() {
        log.debug("设置redis连接池配置。。。");
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMaxWaitMillis(maxWait);
        return poolConfig;
    }
}
