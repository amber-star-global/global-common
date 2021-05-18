package com.global.common.send.message.aspect;

import com.global.common.utils.constants.enums.GlobalProcessesEnum;
import com.global.common.utils.constants.utils.Base64Util;
import com.global.common.utils.constants.utils.DozerMapper;
import com.global.common.web.serializable.JsonProxyUtil;
import com.global.common.web.utils.contants.GlobalWebHeaderKey;
import com.global.common.web.utils.model.OperatorModel;
import com.global.common.web.utils.model.SendMessageModel;
import com.global.common.web.model.response.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 记录发送消息操作日志
 *
 * @Author: 鲁砚琨
 * @CreateTime: 2021-01-12 下午 03:22
 * @Version: v1.0
 */
@Slf4j
@Aspect
@Component
public abstract class AbstractSendMessageAspect<MODEL extends SendMessageModel> {

    @Autowired
    protected DozerMapper dozerMapper;

    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 创建操作日志
     */
    protected abstract void createOperatorRecord(MODEL pushModel);

    /**
     * 记录重试次数
     */
    protected abstract void retryCountRecord(Long messageId);

    /**
     * 记录响应结果
     *
     * @param messageId       消息Id
     * @param code            操作状态码
     * @param responseMessage 响应数据
     */
    protected abstract void updateResponseRecord(Long messageId, Integer code, String responseMessage);


    @Around(value = "execution(public * com.global.common.send.message.api.SendMessageService.process(..)) && args(pushModel)")
    public Object around(ProceedingJoinPoint point, MODEL pushModel) throws Throwable {
        log.debug("发送消息, 请求参数: {}", pushModel);
        // 处理操作人信息
        operatorInfoHandle(pushModel);
        // 记录发送消息日志
        GlobalProcessesEnum statusEnum = GlobalProcessesEnum.PROCESSING;
        String response = null;
        if (Objects.isNull(pushModel.getMessageId())) {
            // 如果没有操作记录, 创建操作日志
            createOperatorRecord(pushModel);
        } else {
            // 记录重试次数
            retryCountRecord(pushModel.getMessageId());
        }
        try {
            // 执行处理逻辑
            Object proceed = point.proceed();
            // 获取处理结果, 记录发送消息返回信息
            if (Objects.nonNull(proceed)) {
                ResponseMessage message = dozerMapper.map(proceed, ResponseMessage.class);
                statusEnum = message.isSuccess() ? GlobalProcessesEnum.SUCCESS : GlobalProcessesEnum.FAIL;
                response = JsonProxyUtil.toJsonString(message);
            } else {
                statusEnum = GlobalProcessesEnum.FAIL;
            }
        } catch (Throwable e) {
            statusEnum = GlobalProcessesEnum.FAIL;
            response = e.getMessage();
            throw e;
        } finally {
            if (Objects.nonNull(pushModel.getMessageId())) {
                updateResponseRecord(pushModel.getMessageId(), statusEnum.getCode(), response);
            }
        }
        return null;
    }

    /**
     * 处理操作人信息
     * 如果第一次操作时 header有操作人信息, 需要把操作人信息放入发送消息model里
     *
     * @param model 发送消息请求Model
     */
    private void operatorInfoHandle(MODEL model) {
        if (Objects.isNull(model.getOperatorId())) {
            String operatorBase64 = httpServletRequest.getHeader(GlobalWebHeaderKey.REQUEST_OPERATOR_INFO);
            if (StringUtils.hasText(operatorBase64)) {
                String operatorJson = Base64Util.decode(operatorBase64);
                OperatorModel operator = JsonProxyUtil.parseObject(operatorJson, OperatorModel.class);
                dozerMapper.map(operator, model);
            }
        }
    }
}
