package com.global.common.logger;

import com.global.common.logger.annotation.Logger;
import com.global.common.web.serializable.JsonProxyUtil;
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
        // 获取当前方法的日志级别
        Logger.LoggerLevelEnum level = logger.level();
        try {
            String paramMessage = String.format("调用当前方法: %s, 请求参数: %s", currentMethod, isJson ? currentArgs.length == 1 ? JsonProxyUtil.toJsonString(currentArgs[0]) : JsonProxyUtil.toJsonString(currentArgs) : currentArgs);
            switchLevel(level, paramMessage);
            Object proceed = point.proceed();
            String resultMessage = String.format("调用当前方法: %s, 返回结果: %s", currentMethod, isJson ? JsonProxyUtil.toJsonString(proceed) : proceed);
            switchLevel(level, resultMessage);
            return proceed;
        } catch (Throwable e) {
            log.error(String.format("调用当前方法: %s, 异常信息: %s", currentMethod, e.getMessage()));
            throw e;
        }
    }

    /**
     * 选择日志输出
     * @param level 日志等级
     * @param message 输出消息
     */
    private void switchLevel(Logger.LoggerLevelEnum level, String message) {
        switch (level) {
            case INFO:
                log.info(message);
                break;
            case DEBUG:
            default:
                log.debug(message);
                break;
        }
    }
}
