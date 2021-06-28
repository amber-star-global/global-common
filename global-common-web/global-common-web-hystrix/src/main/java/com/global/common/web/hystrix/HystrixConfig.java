package com.global.common.web.hystrix;

import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Configuration;

/**
 * @EnableHystrix: 启用断路器
 * @EnableHystrixDashboard: 启用可视化组件
 * http://localhost:9001/hystrix
 *
 * 配置hystrix详情参照:  https://www.pianshen.com/article/1661146624/
 * @Author: 鲁砚琨
 * @CreateTime: 2021-05-19 下午 01:54
 * @Version: v1.0
 */
@Configuration
@EnableHystrix
@EnableHystrixDashboard
public class HystrixConfig {

}
