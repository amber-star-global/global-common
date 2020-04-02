package com.global.common.utils.constants.tools.verify.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-04-02 上午 11:08
 * @Version: v1.0
 */
@Target({ElementType.FIELD}) // 定义注解存在范围
@Retention(RetentionPolicy.RUNTIME) // 运行时解析
@Documented // 生产文档
public @interface VerifyValue {

    // 字段名称
    String name();
    // 验证异常描述
    String message();
}