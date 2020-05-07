package com.global.common.persistence.mybatis.config;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-03-31 下午 04:53
 * @Version: v1.0
 */
@Slf4j
@Configuration
@AutoConfigureAfter(MybatisAutoConfiguration.class)
public class MyBatisConfig implements EnvironmentAware {

    private String mapperScan;

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        log.debug("配置Mybatis映射扫描路径: {}", mapperScan);
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        configurer.setBasePackage(mapperScan);
        return configurer;
    }

    @Override
    public void setEnvironment(Environment environment) {
        mapperScan = environment.getProperty("global.mybatis.mapper.scan.package");
    }
}