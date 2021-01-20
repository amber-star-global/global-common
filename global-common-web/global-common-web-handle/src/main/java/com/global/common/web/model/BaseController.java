package com.global.common.web.model;

import com.global.common.utils.constants.utils.Base64Util;
import com.global.common.web.GlobalWebHeaderKey;
import com.global.common.web.model.request.OperatorModel;
import com.global.common.web.model.response.ResponseBody;
import com.global.common.web.model.response.ResponseMessage;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2019-12-11 下午 02:57
 * @Version: v1.0
 */
@Slf4j
@Component
public class BaseController {

    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected Gson gson;

    /**
     * 处理完成调用方法
     */
    protected ResponseMessage success() {
        return ResponseMessage.success();
    }

    /**
     * 处理成功调用方法
     */
    protected <T> ResponseMessage<T> success(String message, T object) {
        return ResponseMessage.success(message, new ResponseBody<>(object));
    }

    /**
     * 处理成功调用方法
     */
    protected <T> ResponseMessage<T> success(T object) {
        return success(null, object);
    }

    /**
     * 获取操作人信息
     */
    protected OperatorModel getOperatorInfo() {
        String operator = Base64Util.decode(request.getHeader(GlobalWebHeaderKey.REQUEST_OPERATOR_INFO));
        if (StringUtils.hasText(operator)) {
            log.info("获取操作人信息: {}", operator);
            return gson.fromJson(operator, OperatorModel.class);
        }
        log.warn("没有获取到登录信息");
        return new OperatorModel();
    }
}
