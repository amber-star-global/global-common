package com.global.common.logger.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 记录当前方法入参及结果的日志
 * @Author: 鲁砚琨
 * @CreateTime: 2020-04-10 下午 05:49
 * @Version: v1.0
 */
@Target({ElementType.METHOD}) // 定义注解存在范围
@Retention(RetentionPolicy.RUNTIME) // 运行时解析
@Documented // 生产文档
public @interface Logger {

    /**
     * 记录日志的方法名称
     */
    String methodName() default "";

    /**
     * 是否用json格式
     */
    boolean isJson() default true;

    /**
     * 日志级别
     */
    LoggerLevelEnum level() default LoggerLevelEnum.DEBUG;

    /**
     * 日志级别
     */
    enum LoggerLevelEnum {
        INFO,
        DEBUG,
        ;
    }
}
