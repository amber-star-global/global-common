package com.global.common.web;

/**
 * 定义全局headerKey
 *
 * @Author: 鲁砚琨
 * @CreateTime: 2021-01-16 下午 04:35
 * @Version: v1.0
 */
public interface GlobalWebHeaderKey {

    /**
     * 幂等Token
     */
    String REQUEST_IDEM_TOKEN = "X-REQ-IDEMPOTENT-TOKEN";

    /**
     * 操作人信息
     */
    String REQUEST_OPERATOR_INFO = "X-OPERATOR-INFO";

}
