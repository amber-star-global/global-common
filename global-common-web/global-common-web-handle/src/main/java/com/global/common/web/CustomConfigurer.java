package com.global.common.web;

import com.global.common.utils.constants.utils.DozerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义注入配置
 *
 * @Author: 鲁砚琨
 * @CreateTime: 2021-01-16 下午 04:59
 * @Version: v1.0
 */
@Slf4j
@Configuration
public class CustomConfigurer {

    @Bean
    public DozerMapper dozerMapper() {
        log.debug("注入DozerMapper处理类");
        return new DozerMapper();
    }
}
