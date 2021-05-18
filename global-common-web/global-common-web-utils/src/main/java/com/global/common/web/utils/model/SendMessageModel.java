package com.global.common.web.utils.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpMethod;

/**
 * 发送消息Model
 *
 * @Author: 鲁砚琨
 * @CreateTime: 2021-01-16 下午 04:06
 * @Version: v1.0
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class SendMessageModel<EVENT> extends SendMessageOperateModel {

    private static final long serialVersionUID = 4008362534370324224L;

    /**
     * 消息Id
     */
    protected Long messageId;

    /**
     * 发送消息事件类型
     */
    protected EVENT eventType;

    /**
     * 请求地址
     */
    protected String requestUrl;

    /**
     * 请求方式
     *
     * @see HttpMethod
     */
    protected HttpMethod method;

    /**
     * 请求消息
     */
    protected String requestMessage;

    /**
     * 是否需要重试
     */
    protected boolean isRetry;
}
