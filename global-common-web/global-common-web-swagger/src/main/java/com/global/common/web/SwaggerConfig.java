package com.global.common.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2019-12-11 下午 02:44
 * @Version: v1.0
 */
@Slf4j
@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "swagger.scan", name = "package")
public class SwaggerConfig {

    @Value(value = "${global.swagger.scan.package:}")
    private String swaggerScanPackage;

    /**
     * 创建API应用
     * apiInfo() 增加API相关信息
     * 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
     * 本例采用指定扫描的包路径来定义指定要建立API的目录。
     */
    @Bean
    @ConditionalOnProperty(name = "spring.profiles.active", havingValue = "dev")
    public Docket createRestApiDev() {
        log.debug("初始化dev-swagger....");
        return getDocket();
    }

    @Bean
    @ConditionalOnProperty(name = "spring.profiles.active", havingValue = "qa")
    public Docket createRestApiQa() {
        log.debug("初始化qa-swagger....");
        return getDocket();
    }

    private Docket getDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerScanPackage))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 创建该API的基本信息（这些基本信息会展现在文档页面中）
     * 访问地址：http://项目实际地址/swagger-ui.html
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("REST API")
                .description("...................")
                .termsOfServiceUrl("http://www.baidu.com")
                .version("1.0")
                .build();
    }
}
