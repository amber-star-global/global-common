package com.global.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2019-12-11 下午 03:37
 * @Version: v1.0
 */
@AllArgsConstructor
@Getter
public enum BusinessEnum {

    SUCCESS(600, "处理完成"),
    FAIL(601, "系统异常"),

    REQUEST_NOT_FOUND(602, "无效的访问"),
    REQUEST_METHOD_NOT_SUPPORTED(603, "请求方式不支持"),
    REQUEST_DATA_NOT_MAPPER(604, "请求参数转换失败"),

    REQUEST_TOKEN_NOT_NULL(605, "TOKEN凭证不能为空"),
    REQUEST_TOKEN_OVERDUE(606, "TOKEN凭证已过期，请获取新的TOKEN凭证"),
    REQUEST_OTHER_AREA_LOGIN_(607, "当前用户已在其他地方登录，请重新登录"),

    REQUEST_IDEMPOTENT_NOT_NULL(608, "幂等校验TOKEN不能为空"),
    REQUEST_IDEMPOTENT_PROCESSING(609, "幂等校验，业务处理中"),

    REQUEST_AUTH_PARAM_NOT_NULL(610, "缺少必传参数"),
    REQUEST_AUTH_PARAM_TYPE_NOT_SUPPORTED(611, "不支持的参数类型"),
    REQUEST_AUTH_PARAM_OUT_THRESHOLD(612, "请求参数数据不合法"),

    ;

    /**
     * 状态码
     */
    private int code;
    /**
     * 返回消息
     */
    private String message;
}
