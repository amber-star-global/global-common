package com.global.common.web.desensitized.handle;

/**
 * 脱敏处理的抽象类,
 * 定义替换符: *
 * 子类需要实现handle方法, 对应不同的实现
 *
 * @Author: 鲁砚琨
 * @CreateTime: 2020-08-05 下午 06:07
 * @Version: v1.0
 */
public abstract class AbstractDesensitizedHandle {

    final String REPLACE_ASTERISK = "*";

    protected abstract String handle(String value);

    String getReplaceAsterisk(int length) {
        if (length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        do {
            sb.append("*");
            length--;
        } while (length > 0);
        return sb.toString();
    }

    public String process(String value) {
        return handle(value);
    }

}
