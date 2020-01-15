package com.global.common.utils.constants.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * 处理过程枚举
 * @Author: 鲁砚琨
 * @CreateTime: 2020-01-15 下午 03:19
 * @Version: v1.0
 */
@AllArgsConstructor
@Getter
public enum  ProcessesEnum implements EnumHandle<ProcessesEnum> {

    UN_PROCESSED(1, "未处理"),
    PROCESSING(2, "处理中"),
    SUCCESS(3, "处理成功"),
    FAIL(4, "处理失败");

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
    public ProcessesEnum getEnum(Integer code) {
        Optional<ProcessesEnum> optional = Arrays.stream(ProcessesEnum.values())
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
        ProcessesEnum object = getEnum(code);
        return object != null ? object.getDesc() : null;
    }
}
