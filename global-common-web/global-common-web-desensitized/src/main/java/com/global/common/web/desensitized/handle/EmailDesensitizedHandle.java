package com.global.common.web.desensitized.handle;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 电子邮箱脱敏处理类
 * @Author: 鲁砚琨
 * @CreateTime: 2020-08-05 下午 07:40
 * @Version: v1.0
 */
public final class EmailDesensitizedHandle extends AbstractDesensitizedHandle {

    @Override
    protected String handle(String value) {
        if (Objects.isNull(value))
            return null;
        final int index = StringUtils.indexOf(value, "@");
        if (index <=1 ) {
            return value;
        }
        return StringUtils.rightPad(StringUtils.left(value, 1), index, REPLACE_ASTERISK)
                .concat(StringUtils.mid(value, index, StringUtils.length(value)));

    }
}
