package com.global.common.web.desensitized.annotations;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.global.common.web.desensitized.DesensitizedJsonSerializer;
import com.global.common.web.desensitized.enums.DesensitizedType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-08-07 上午 11:17
 * @Version: v1.0
 */
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@JacksonAnnotationsInside
@JsonSerialize(using = DesensitizedJsonSerializer.class)
public @interface Desensitized {

    DesensitizedType type();
}
