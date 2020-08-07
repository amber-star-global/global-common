package com.global.common.web.feign;

import com.global.common.web.idempotent.GlobalIdempotent;
import com.global.common.web.idempotent.IdempotentUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-04-01 下午 04:19
 * @Version: v1.0
 */
@Slf4j
@Configuration
public class FeignIdempotentConfig implements RequestInterceptor, Ordered {

    @Resource
    private IdempotentUtil idempotentUtil;

    @Override
    public void apply(RequestTemplate template) {
        // feign接口幂等处理
        log.debug("获取feign接口调用信息; 调用接口: {}, 请求方式: {}", template.url(), template.method());
        Collection<String> feignTokenHeader = template.headers().get(GlobalIdempotent.REQUEST_IDEM_TOKEN);
        if (feignTokenHeader != null && feignTokenHeader.size() > 0) {
            // 当前接口需要实现幂等处理, 插入token值,
            String value = idempotentUtil.getToken();
            log.debug("添加feign幂等token: {}", value);
            template.header(GlobalIdempotent.REQUEST_IDEM_TOKEN, value);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
