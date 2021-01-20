package com.global.common.web.desensitized.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 脱敏类型枚举
 *
 * @Author: 鲁砚琨
 * @CreateTime: 2020-08-05 下午 06:09
 * @Version: v1.0
 */
@AllArgsConstructor
@Getter
public enum DesensitizedType {

    /**
     * 手机号
     */
    PHONE,
    /**
     * 名称
     */
    NAME,
    /**
     * 密码
     */
    PASSWORD,
    /**
     * 电子邮箱
     */
    EMAIL
    ;
}
