package com.global.common.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2019-12-11 下午 02:55
 * @Version: v1.0
 */
@Slf4j
@EnableWebMvc
@Configuration
public class CustomWebConfigurerAdapter extends WebMvcConfigurerAdapter {

    /**
     * jackson消息转换
     */
    @Bean
    public MappingJackson2HttpMessageConverter jacksonHttpMessageConverter() {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setDefaultCharset(Charset.defaultCharset());
        return messageConverter;
    }

    /**
     * 配置消息转换
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("[启动配置处理]配置消息转换......");
        converters.add(jacksonHttpMessageConverter());
    }

    /**
     * 配置跨域访问
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("[启动配置处理]配置跨域访问......");
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(false).maxAge(3600);
    }

    /**
     * 配置内容裁决解析器
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        log.info("[启动配置内容裁决解析器处理]配置内容裁决解析器处理......");
        configurer.favorPathExtension(false);
    }

    /**
     * 配置默认servlet处理方式
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        log.info("[启动配置处理]配置默认servlet处理方式......");
        configurer.enable();
    }
}
