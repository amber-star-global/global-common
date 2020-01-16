package com.global.common.utils.file.csv.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: csv文件定义表头注解
 * @Author: 鲁砚琨
 * @Date: 2019/2/22 15:46
 * @Version: v1.0
 */
@Target({ElementType.FIELD}) // 定义注解存在范围
@Retention(RetentionPolicy.RUNTIME) // 运行时解析
@Documented // 生产文档
public @interface CsvColumn {

    /**
     * 设置头名称
     */
    String name();

    /**
     * 是否忽略这一列数据, 默认不忽略
     */
    boolean ignore() default false;
}
