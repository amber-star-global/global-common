package com.global.common.web;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
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
@Order
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
        if (Objects.nonNull(request)) {
            try {
                String operator = request.getHeader(GlobalWebHeaderKey.REQUEST_OPERATOR_INFO);
                if (StringUtils.hasText(operator)) {
                    headers.add(GlobalWebHeaderKey.REQUEST_OPERATOR_INFO, operator);
                }
            } catch (Exception e) {
                log.warn(e.getMessage());
            }
        }
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }
}
