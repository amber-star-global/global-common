package com.global.common.send.message.impl;

import com.global.common.exception.BusinessEnum;
import com.global.common.exception.BusinessException;
import com.global.common.send.message.api.SendMessageService;
import com.global.common.utils.constants.utils.Base64Util;
import com.global.common.utils.constants.utils.DozerMapper;
import com.global.common.web.serializable.JsonProxyUtil;
import com.global.common.web.utils.contants.GlobalWebHeaderKey;
import com.global.common.web.JsonRestTemplate;
import com.global.common.web.utils.model.OperatorModel;
import com.global.common.web.utils.model.SendMessageModel;
import com.global.common.web.utils.model.SendMessageOperateModel;
import com.global.common.web.model.response.ResponseMessage;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Objects;

/**
 * 发送消息抽象处理类
 *
 * @Author: 鲁砚琨
 * @CreateTime: 2021-01-11 下午 06:48
 * @Version: v1.0
 */
@Slf4j
@Component
public abstract class AbstractSendMessageServiceImpl<MODEL extends SendMessageModel, EVENT extends Enum>
        implements SendMessageService<MODEL> {

    @Autowired
    protected DozerMapper dozerMapper;
    @Autowired
    private JsonRestTemplate jsonRestTemplate;

    /**
     * 定义发送消息事件
     */
    protected abstract EVENT getEventEnum();

    /**
     * 处理发送消息重试处理, 子类根据异常原因处理重试操作
     *
     * @param e     异常
     * @param model 发送消息Model
     */
    protected abstract void retrySendMessage(Throwable e, MODEL model) throws BusinessException;

    /**
     * 处理发送消息
     *
     * @param model 发送消息Model
     */
    @Override
    public ResponseMessage process(MODEL model) throws BusinessException {
        ResponseMessage responseMessage = null;
        try {
            log.info("发送消息, 处理事件: {}, 请求参数: {}", getEventEnum(), model);
            // 发送消息前处理消息, 默认无处理方法, 子类可以根据需求处理
            sendMessageBefore(model);
            responseMessage = getResponse(model);
            log.info("接收消息, 响应数据: {}", responseMessage);
            // 发送消息后处理消息, 默认无处理方法, 子类可以根据需求处理
            sendMessageAfter(model, responseMessage);
            if (Objects.isNull(responseMessage)) {
                // 交互异常, 发送消息失败
                throw new BusinessException();
            } else if (!responseMessage.isSuccess()) {
                // 交互业务异常, 处理数据失败
                throw new BusinessException(responseMessage.getCode(), responseMessage.getMessage());
            }
        } catch (Throwable e) {
            log.warn(e.getMessage(), e);
            // 需要重试时, 进入重试队列
            if (model.isRetry()) {
                // 根据异常处理重试操作, 如果需要重试,切重试发生异常需要对外抛出异常,
                // 如果发起重试处理, 则不需要再抛出异常
                retrySendMessage(e, model);
            }
        }
        return responseMessage;
    }

    /**
     * 发送消息之前处理方法, 子类可以重写该方法
     */
    protected void sendMessageBefore(MODEL model) {

    }

    /**
     * 发送消息之后处理方法, 子类可以重写该方法
     */
    protected void sendMessageAfter(MODEL model, ResponseMessage responseMessage) {

    }

    /**
     * 发送消息
     *
     * @param model 发送消息Model
     */
    protected ResponseMessage getResponse(MODEL model) throws BusinessException {
        if (Objects.nonNull(model.getMethod()) && StringUtils.hasText(model.getRequestUrl())) {
            switch (model.getMethod()) {
                case GET:
                    return jsonRestTemplate.getForObject(model.getRequestUrl(), ResponseMessage.class);
                case POST:
                    if (StringUtils.isEmpty(model.getRequestMessage())) {
                        throw new BusinessException(BusinessEnum.REQUEST_AUTH_PARAM_NOT_NULL.getCode(), "POST请求参数不能为空");
                    }
                    return jsonRestTemplate.postForObject(model.getRequestUrl(), model.getRequestMessage(), getOperatorHeader(model), ResponseMessage.class);
            }
        }
        return null;
    }

    /**
     * 设置操作人header信息
     */
    private Map<String, String> getOperatorHeader(MODEL model) {
        if (Objects.nonNull(model.getOperatorId())) {
            Map<String, String> operatorHeaderMap = Maps.newHashMap();
            operatorHeaderMap.put(GlobalWebHeaderKey.REQUEST_OPERATOR_INFO, getOperatorInfo(model));
            return operatorHeaderMap;
        }
        return null;
    }

    /**
     * 设置操作人信息
     *
     * @param operateModel 发送消息的操作人信息
     */
    private String getOperatorInfo(SendMessageOperateModel operateModel) {
        OperatorModel operator = dozerMapper.map(operateModel, OperatorModel.class);
        String operatorJson = JsonProxyUtil.toJsonString(operator);
        log.debug("操作人信息: {}", operatorJson);
        return Base64Util.encode(operatorJson);
    }
}
