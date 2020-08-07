package com.global.common.web.desensitized.handle;


import com.global.common.web.desensitized.enums.DesensitizedType;

import java.util.Objects;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-08-05 下午 07:35
 * @Version: v1.0
 */
public class DesensitizedHandleFormatterFactory {

    private static final PhoneDesensitizedHandle phoneDesensitizedFormatter = new PhoneDesensitizedHandle();
    private static final NameDesensitizedHandle nameDesensitizedFormatter = new NameDesensitizedHandle();
    private static final PasswordDesensitizedHandle passwordDesensitizedFormatter = new PasswordDesensitizedHandle();
    private static final EmailDesensitizedHandle emailDesensitizedFormatter = new EmailDesensitizedHandle();
    private static final DefaultDesensitizedHandle defaultDesensitizedFormatter = new DefaultDesensitizedHandle();

    public AbstractDesensitizedHandle getFormatter(DesensitizedType type) {
        if (Objects.nonNull(type)) {
            switch (type) {
                case PHONE:
                    return phoneDesensitizedFormatter;
                case NAME:
                    return nameDesensitizedFormatter;
                case PASSWORD:
                    return passwordDesensitizedFormatter;
                case EMAIL:
                    return emailDesensitizedFormatter;
            }
        }
        return defaultDesensitizedFormatter;
    }
}
