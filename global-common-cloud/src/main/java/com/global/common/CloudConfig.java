package com.global.common;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2021-05-25 下午 02:32
 * @Version: v1.0
 */
@Configuration
@EnableEurekaClient
@EnableConfigurationProperties
public class CloudConfig {

}
