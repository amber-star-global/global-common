package com.global.common.utils.constants.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-01-15 下午 05:48
 * @Version: v1.0
 */
@AllArgsConstructor
@Getter
public enum DelFlagEnum implements EnumHandle<DelFlagEnum> {

    DELETE(0, "删除"),
    NORMAL(1, "正常"),
    ;

    /**
     * 枚举值
     */
    private Integer code;

    /**
     * 枚举描述
     */
    private String desc;

    /**
     * 通过枚举值获取枚举
     *
     * @param code 枚举编码
     */
    @Override
    public DelFlagEnum getEnum(Integer code) {
        Optional<DelFlagEnum> optional = Arrays.stream(DelFlagEnum.values())
                .filter(e -> e.getCode().equals(code)).findFirst();
        return optional.isPresent() ? optional.get() : null;
    }

    /**
     * 通过枚举值获取枚举描述
     *
     * @param code 枚举编码
     */
    @Override
    public String getEnumDesc(Integer code) {
        DelFlagEnum object = getEnum(code);
        return object != null ? object.getDesc() : null;
    }
}
