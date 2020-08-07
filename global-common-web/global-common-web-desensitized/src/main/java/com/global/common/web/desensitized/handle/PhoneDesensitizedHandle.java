package com.global.common.web.desensitized.handle;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 手机号脱敏处理类
 * 前三后四不脱敏
 *
 * @Author: 鲁砚琨
 * @CreateTime: 2020-08-05 下午 06:06
 * @Version: v1.0
 */
public final class PhoneDesensitizedHandle extends AbstractDesensitizedHandle {


    @Override
    protected String handle(String value) {
        if (Objects.isNull(value))
            return null;
        return StringUtils.left(value, 3).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(value, 4),
                StringUtils.length(value), REPLACE_ASTERISK), getReplaceAsterisk(3)));
    }
}
