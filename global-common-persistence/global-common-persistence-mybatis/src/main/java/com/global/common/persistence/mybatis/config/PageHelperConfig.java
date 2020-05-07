package com.global.common.persistence.mybatis.config;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.autoconfigure.PageHelperProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-05-07 下午 06:14
 * @Version: v1.0
 */
@Slf4j
@Configuration
public class PageHelperConfig {

    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        pageHelper.setProperties(pageHelperProperties().getProperties());
        return pageHelper;
    }

    private PageHelperProperties pageHelperProperties() {
        PageHelperProperties pageHelperProperties = new PageHelperProperties();
        pageHelperProperties.setOffsetAsPageNum("true");
        pageHelperProperties.setRowBoundsWithCount("true");
        pageHelperProperties.setReasonable("true");
        return pageHelperProperties;
    }
}
