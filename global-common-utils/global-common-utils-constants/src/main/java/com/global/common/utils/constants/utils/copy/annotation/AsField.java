package com.global.common.utils.constants.utils.copy.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 指定转换的字段
 *
 * @Author: 鲁砚琨
 * @CreateTime: 2020-04-13 上午 10:35
 * @Version: v1.0
 */
@Target({ElementType.FIELD}) // 定义注解存在范围
@Retention(RetentionPolicy.RUNTIME) // 运行时解析
@Documented // 生产文档
public @interface AsField {

    String value();
}
