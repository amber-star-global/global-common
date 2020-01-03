package com.global.common.web.base;

import com.global.common.web.base.response.ResponseBody;
import com.global.common.web.base.response.ResponseMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2019-12-11 下午 02:57
 * @Version: v1.0
 */
@Slf4j
public class BaseController {

    /**
     * 处理完成调用方法
     */
    protected ResponseMessage success() {
        return ResponseMessage.success();
    }

    /**
     * 处理成功调用方法
     * @param message 成功消息
     * @param object 返回数据
     */
    protected <T> ResponseMessage success(String message, T object) {
        return ResponseMessage.success(message, new ResponseBody<>(object));
    }

    /**
     * 处理成功调用方法
     * @param object 返回数据
     */
    protected <T> ResponseMessage<T> success(T object) {
        return ResponseMessage.success(new ResponseBody<>(object));
    }
}
