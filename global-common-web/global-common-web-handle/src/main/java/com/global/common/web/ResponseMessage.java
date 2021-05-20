package com.global.common.web;

import com.global.common.exception.BusinessException;
import com.global.common.exception.BusinessEnum;
import com.global.common.web.utils.model.ResponseBody;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

/**
 * 接口返回消息统一处理类
 *
 * @Author: 鲁砚琨
 * @CreateTime: 2019-12-11 下午 03:19
 * @Version: v1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Slf4j
public class ResponseMessage<T> implements Serializable {

    private static final long serialVersionUID = -3373302607215856771L;

    /**
     * 默认成功状态码
     */
    private static final BusinessEnum DEFAULT_SUCCESS = BusinessEnum.SUCCESS;

    /**
     * 默认失败状态码
     */
    private static final BusinessEnum DEFAULT_FAIL = BusinessEnum.FAIL;

    /**
     * 状态码
     */
    private Integer code;
    /**
     * 操作消息
     */
    private String message;
    /**
     * 接口返回数据
     */
    private ResponseBody<T> responseBody;

    /**
     * 无返回值, 处理完成调用方法
     */
    public static ResponseMessage success() {
        return success(null, null);
    }

    /**
     * 有返回值, 处理完成调用方法
     *
     * @param responseBody 接口返回数据
     */
    public static <T> ResponseMessage<T> success(ResponseBody<T> responseBody) {
        return success(null, responseBody);
    }

    /**
     * 处理完成调用方法
     *
     * @param message      本次处理结果
     * @param responseBody 接口返回消息
     */
    public static <T> ResponseMessage<T> success(String message, ResponseBody<T> responseBody) {
        log.debug("处理完成: {}, {}", message, responseBody);
        message = message != null && !"".equals(message) ? message : DEFAULT_SUCCESS.getMessage();
        return new ResponseMessage<>(DEFAULT_SUCCESS.getCode(), message, responseBody);
    }

    /**
     * 处理失败调用方法
     */
    public static ResponseMessage fail() {
        return fail(null, null, null);
    }


    /**
     * 处理失败调用方法
     *
     * @param e 业务异常
     */
    public static ResponseMessage fail(BusinessException e) {
        return fail(e.getCode(), e.getMessage(), null);
    }

    /**
     * 处理失败调用方法
     *
     * @param statusEnum 返回消息枚举类
     */
    public static ResponseMessage fail(BusinessEnum statusEnum) {
        return fail(statusEnum, null);
    }

    /**
     * 处理失败调用方法
     *
     * @param statusEnum 返回消息枚举类
     * @param message    处理消息
     */
    public static ResponseMessage fail(BusinessEnum statusEnum, String message) {
        Integer code = statusEnum != null ? statusEnum.getCode() : null;
        message = message != null && !"".equals(message) ? message : statusEnum != null ? statusEnum.getMessage() : null;
        return fail(code, message, null);
    }

    /**
     * 处理失败调用方法
     *
     * @param code         状态码
     * @param message      处理消息
     * @param responseBody 返回数据
     */
    public static <T> ResponseMessage<T> fail(Integer code, String message, ResponseBody<T> responseBody) {
        log.debug("处理失败: {}, {}, {}", code, message, responseBody);
        code = code == null ? DEFAULT_FAIL.getCode() : code;
        message = message == null ? DEFAULT_FAIL.getMessage() : message;
        return new ResponseMessage<>(code, message, responseBody);
    }

    /**
     * 校验请求是否成功
     */
    public boolean isSuccess() {
        return this.getCode() != null && this.getCode().equals(BusinessEnum.SUCCESS.getCode());
    }

    /**
     * 校验接口是否有返回数据
     */
    public boolean hasObject() {
        if (!this.isSuccess())
            return false;
        if (this.getResponseBody() == null)
            return false;
        if (this.getResponseBody().getObject() == null)
            return false;
        if (this.getResponseBody().getObject() instanceof List)
            return ((List) this.getResponseBody().getObject()).size() > 0;
        return true;
    }

    /**
     * 获取接口返回数据
     */
    public T getObject() {
        return hasObject() ? getResponseBody().getObject() : null;
    }
}
