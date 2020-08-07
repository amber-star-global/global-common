package com.global.common.web.desensitized.handle;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-08-05 下午 06:07
 * @Version: v1.0
 */
public abstract class AbstractDesensitizedHandle {


    protected final String REPLACE_ASTERISK = "*";


    protected abstract String handle(String value);

    protected String getReplaceAsterisk(int length) {
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
