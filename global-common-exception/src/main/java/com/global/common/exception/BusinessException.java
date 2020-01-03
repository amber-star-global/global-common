package com.global.common.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2019-12-11 下午 04:19
 * @Version: v1.0
 */
@Slf4j
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -4399798015112323376L;
    /**
     * 业务编码
     */
    private int code;
    /**
     * 返回消息
     */
    private String message;
    /**
     * 业务异常枚举
     */
    private BusinessEnum businessEnum = BusinessEnum.FAIL;

    private void init() {
        this.code = this.businessEnum.getCode();
        this.message = this.businessEnum.getMessage();
    }

    /**
     * 默认业务异常
     */
    public BusinessException() {
        log.error("当前业务处理失败!");
        init();
    }

    /**
     * 非业务抛出异常
     * @param exception 在业务处理中,抛出非业务异常
     */
    public BusinessException(Throwable exception) {
        log.error(exception.getMessage(), exception);
        init();
    }

    /**
     *
     * @param code
     * @param message
     * @param exception
     */
    public BusinessException(int code, String message, Throwable exception) {
        log.error(exception.getMessage(), exception);
        this.code = code;
        this.message = message;
    }

    /**
     * 自定义话术, 异常处理
     * @param businessEnum 业务异常枚举
     * @param message 自定义话术
     */
    public BusinessException(BusinessEnum businessEnum, String message) {
        this.businessEnum = businessEnum;
        this.code = businessEnum.getCode();
        this.message = message;
    }

    /**
     * 获取业务异常编码
     * @return
     */
    public int getCode() {
        return this.code;
    }

    /**
     * 获取业务异常信息
     * @return
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * 获取业务异常枚举
     * @return
     */
    public BusinessEnum getBusinessEnum() {
        return this.businessEnum;
    }
}
