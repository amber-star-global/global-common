package com.global.common.logger;

import com.alibaba.fastjson.JSON;
import com.global.common.logger.annotation.Logger;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 日志切面处理
 * @Author: 鲁砚琨
 * @CreateTime: 2020-04-10 下午 05:51
 * @Version: v1.0
 */
@Slf4j
@Aspect
@Component
public class LoggerAspect {


    /**
     * 记录调用方法的请求参数, 返回参数, 及报错时的错误信息
     */
    @Around(value = "@annotation(com.global.common.logger.annotation.Logger) && @annotation(logger)")
    public Object around(ProceedingJoinPoint point, Logger logger) throws Throwable {
        // 是否将请求参数及返回参数转换成json格式
        boolean isJson = logger.isJson();
        // 当前方法名称
        String currentMethod = "".equals(logger.methodName()) ? point.getSignature().getName() : logger.methodName();
        // 当前方法请求参数
        Object[] currentArgs = point.getArgs();
        try {
            log.info(String.format("调用当前方法: %s, 请求参数: %s", currentMethod, isJson ? currentArgs.length == 1 ? JSON.toJSONString(currentArgs[0]) : JSON.toJSONString(currentArgs) : currentArgs));
            Object proceed = point.proceed();
            log.info(String.format("调用当前方法: %s, 返回结果: %s", currentMethod, isJson ? JSON.toJSONString(proceed) : proceed));
            return proceed;
        } catch (Throwable e) {
            log.error(String.format("调用当前方法: %s, 异常信息: %s", currentMethod, e.getMessage()));
            throw e;
        }
    }

}
