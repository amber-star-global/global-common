package com.global.common.web.desensitized.handle;


import com.global.common.web.desensitized.enums.DesensitizedType;

import java.util.Objects;

/**
 * 脱敏处理工厂类型
 * 根据标签定义的类型获取不同的处理规则
 * @Author: 鲁砚琨
 * @CreateTime: 2020-08-05 下午 07:35
 * @Version: v1.0
 */
public final class DesensitizedHandleFactory {

    /**
     * 手机号脱敏
     */
    private static final PhoneDesensitizedHandle phoneDesensitizedFormatter = new PhoneDesensitizedHandle();
    /**
     * 姓名脱敏
     */
    private static final NameDesensitizedHandle nameDesensitizedFormatter = new NameDesensitizedHandle();
    /**
     * 密码脱敏
     */
    private static final PasswordDesensitizedHandle passwordDesensitizedFormatter = new PasswordDesensitizedHandle();
    /**
     * 电子邮件脱敏
     */
    private static final EmailDesensitizedHandle emailDesensitizedFormatter = new EmailDesensitizedHandle();
    /**
     * 默认脱敏(不脱敏)
     */
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
