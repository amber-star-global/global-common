package com.global.common.web.idempotent;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-03-31 下午 05:52
 * @Version: v1.0
 */
public interface GlobalIdempotent {

    // 幂等校验TOKEN
    String REQUEST_IDEM_TOKEN = "X-REQ-IDEM-TOKEN";

}
