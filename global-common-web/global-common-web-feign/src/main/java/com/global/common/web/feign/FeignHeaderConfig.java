package com.global.common.web.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-06-29 上午 10:25
 * @Version: v1.0
 */
@Slf4j
@Configuration
public class FeignHeaderConfig implements RequestInterceptor, Ordered {

    private final static Map<String, String> HEADERS_KEY = new HashMap<>();

    static {
        HEADERS_KEY.put("accept", "Accept");
        HEADERS_KEY.put("accept-charset", "Accept-Charset");
        HEADERS_KEY.put("accept-encoding", "Accept-Encoding");
        HEADERS_KEY.put("accept-language", "Accept-Language");
        HEADERS_KEY.put("accept-ranges", "Accept-Ranges");
        HEADERS_KEY.put("authorization", "Authorization");
        HEADERS_KEY.put("content-length", "Content-Length");
        HEADERS_KEY.put("content-type", "Content-Type");
        HEADERS_KEY.put("from", "From");
        HEADERS_KEY.put("host", "Host");
        HEADERS_KEY.put("user-agent", "User-Agent");
    }

    @Override
    public void apply(RequestTemplate template) {
        log.debug("过滤请求头信息, 把请求头传递到feign调用的服务");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.nonNull(attributes)) {
            HttpServletRequest request = attributes.getRequest();
            if (Objects.nonNull(request)) {
                Enumeration<String> headerNames = request.getHeaderNames();
                if (Objects.nonNull(headerNames)) {
                    while (headerNames.hasMoreElements()) {
                        String name = headerNames.nextElement();
                        if (HEADERS_KEY.containsKey(name) && template.headers().containsKey(HEADERS_KEY.get(name))) {
                            //不覆写
                            log.debug("feign header not Override: {}", name);
                        } else {
                            String values = request.getHeader(name);
                            template.header(name, values);
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getOrder() {
        return 9;
    }
}
