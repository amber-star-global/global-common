package com.global.common.valid.annotations;

import com.global.common.valid.handle.EnumValueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 枚举类参数校验
 * @Author: 鲁砚琨
 * @CreateTime: 2020-01-15 下午 06:51
 * @Version: v1.0
 */
@Target(value = {ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValueValidator.class)
@Documented
public @interface EnumValue {

    Class<? extends Enum<?>> value();

    /**
     * 失败返回消息
     */
    String message();

    /**
     * 执行方法
     */
    String enumMethod();

    /**
     * 自定义校验注解必加配置
     */
    Class<?>[] groups() default {};

    /**
     * 自定义校验注解必加配置
     */
    Class<? extends Payload>[] payload() default {};
}
