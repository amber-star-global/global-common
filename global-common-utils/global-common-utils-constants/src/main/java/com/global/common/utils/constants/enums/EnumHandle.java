package com.global.common.utils.constants.enums;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-01-15 下午 03:22
 * @Version: v1.0
 */
public interface EnumHandle<ENUM extends Enum<ENUM>> {

    /**
     * 通过枚举值获取枚举
     * @param code 枚举编码
     */
    ENUM getEnum(Integer code);

    /**
     * 通过枚举值获取枚举描述
     * @param code 枚举编码
     */
    String getEnumDesc(Integer code);
}
