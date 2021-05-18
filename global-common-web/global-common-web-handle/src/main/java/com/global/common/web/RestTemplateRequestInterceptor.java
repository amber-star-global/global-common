package com.global.common.web;

import com.global.common.web.serializable.JsonProxyUtil;
import com.global.common.web.utils.contants.GlobalWebHeaderKey;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * 通过restTemplate方式调用接口时,增加操作人信息
 *
 * @Author: 鲁砚琨
 * @CreateTime: 2020-11-25 下午 03:08
 * @Version: v1.0
 */
@Slf4j
@ConditionalOnClass({RestTemplate.class, JsonRestTemplate.class})
@Component
@Order(1)
public class RestTemplateRequestInterceptor implements ClientHttpRequestInterceptor {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    public RestTemplateRequestInterceptor(@Autowired(required = false) List<RestTemplate> restTemplates) {
        if (Objects.nonNull(restTemplates)) {
            restTemplates.forEach((restTemplate) -> {
                List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
                interceptors.add(interceptors.size(), this);
            });
        }
    }

    @NonNull
    public ClientHttpResponse intercept(@NonNull HttpRequest httpRequest, @NonNull byte[] bytes, @NonNull ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        HttpHeaders headers = httpRequest.getHeaders();
        log.info("httpHeaders: {}", headers.getClass());
        if (Objects.nonNull(this.request)) {
            log.info("request: {}", this.request.getClass());
            try {
                String operatorHeader = this.request.getHeader(GlobalWebHeaderKey.REQUEST_OPERATOR_INFO);
                if (StringUtils.isNotBlank(operatorHeader)) {
                    log.info("操作人信息: {}", operatorHeader);
                    List<String> currentOperateHeader = headers.get(GlobalWebHeaderKey.REQUEST_OPERATOR_INFO);
                    log.info("当前header操作人信息: {}", JsonProxyUtil.toJsonString(currentOperateHeader));
                    if (CollectionUtils.isEmpty(currentOperateHeader)) {
                        headers.add(GlobalWebHeaderKey.REQUEST_OPERATOR_INFO, operatorHeader);
                    }
                }
            } catch (Exception e) {
                log.warn(e.getMessage());
            }
        }
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }
}
