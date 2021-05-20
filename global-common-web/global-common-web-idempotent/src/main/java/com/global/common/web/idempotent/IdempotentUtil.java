package com.global.common.web.idempotent;

import com.global.common.cache.redis.RedisValueService;
import com.global.common.exception.BusinessEnum;
import com.global.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 幂等校验处理工具类
 * @Author: 鲁砚琨
 * @Date: 2019/8/19 17:46
 * @Version: v1.0
 */
@Slf4j
@Component
public class IdempotentUtil {

    @Autowired
    private RedisValueService redisValueService;


    /**
     * 获取Token方法
     * @return
     */
    public String getToken() {
        String tokenValue = UUID.randomUUID().toString();
        setToken(tokenValue);
        log.info("获取token值: {}", tokenValue);
        return tokenValue;
    }

    /**
     * 设置token值，默认一个token存在10分钟
     */
    private void setToken(String key) {
        // 设置幂等Key,value
        redisValueService.put(key, null);
        // 设置过期时间
        redisValueService.expire(key, 10, TimeUnit.MINUTES);
    }


    /**
     * 幂等处理方法
     * @param key token
     */
    public void idempotentHandle(String key) throws BusinessException {
        log.info("幂等校验, Key为: {}", key);
        // token 值不存在, 服务已被处理
        if (!redisValueService.hasKey(key)) {
            throw new BusinessException(BusinessEnum.REQUEST_IDEMPOTENT_PROCESSING, "请求失效, 请重新操作!");
        }
        // key存在, 第一次被调用, 删除key
        redisValueService.delete(key);
    }

}
