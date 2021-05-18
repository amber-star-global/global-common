package com.global.common.send.message.api;

import com.global.common.exception.BusinessException;
import com.global.common.web.utils.model.SendMessageModel;
import com.global.common.web.model.response.ResponseMessage;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2021-01-16 下午 04:22
 * @Version: v1.0
 */
public interface SendMessageService<MODEL extends SendMessageModel> {

    /**
     * 发送消息业务处理
     *
     * @param model 发送消息Model
     */
    ResponseMessage process(MODEL model) throws BusinessException;
}
