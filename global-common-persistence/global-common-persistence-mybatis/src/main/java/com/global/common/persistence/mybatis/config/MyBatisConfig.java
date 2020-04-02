package com.global.common.persistence.mybatis.config;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2020-03-31 下午 04:53
 * @Version: v1.0
 */
@Slf4j
@Configuration
@MapperScan(basePackages = "${global.mybatis.mapper.scan.package}")
public class MyBatisConfig {
}
