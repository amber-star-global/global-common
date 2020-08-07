package com.global.common.web.desensitized.handle;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-08-07 上午 11:23
 * @Version: v1.0
 */
public final class DefaultDesensitizedHandle extends AbstractDesensitizedHandle {

    @Override
    protected String handle(String value) {
        return value;
    }
}
