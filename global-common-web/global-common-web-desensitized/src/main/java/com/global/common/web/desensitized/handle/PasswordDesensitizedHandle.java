package com.global.common.web.desensitized.handle;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 密码脱敏处理类
 * 所有字符全部脱敏
 *
 * @Author: 鲁砚琨
 * @CreateTime: 2020-08-05 下午 07:40
 * @Version: v1.0
 */
public final class PasswordDesensitizedHandle extends AbstractDesensitizedHandle {

    @Override
    protected String handle(String value) {
        if (Objects.isNull(value))
            return null;
        return StringUtils.rightPad(value, StringUtils.length(value), REPLACE_ASTERISK);
    }

}
