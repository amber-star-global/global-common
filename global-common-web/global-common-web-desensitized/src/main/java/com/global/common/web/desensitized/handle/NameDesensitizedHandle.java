package com.global.common.web.desensitized.handle;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 名称脱敏处理类
 * 只保留姓氏
 * @Author: 鲁砚琨
 * @CreateTime: 2020-08-05 下午 07:40
 * @Version: v1.0
 */
public final class NameDesensitizedHandle extends AbstractDesensitizedHandle {


    @Override
    protected String handle(String value) {
        if (Objects.isNull(value))
            return null;
        String name = StringUtils.left(value, 1);
        return StringUtils.rightPad(name, StringUtils.length(value), REPLACE_ASTERISK);
    }
}
