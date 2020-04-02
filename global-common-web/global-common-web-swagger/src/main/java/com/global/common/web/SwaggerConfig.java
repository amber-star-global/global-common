package com.global.common.web;

import com.google.common.base.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

/**
 * @Author: 鲁砚琨
 * @CreateTime: 2019-12-11 下午 02:44
 * @Version: v1.0
 */
@Slf4j
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    private Environment env;

    @Value(value = "${global.swagger.scan.package}")
    private String swaggerScanPackage;


    /**
     * 创建API应用
     * apiInfo() 增加API相关信息
     * 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
     * 本例采用指定扫描的包路径来定义指定要建立API的目录。
     */
    @Bean
    public Docket createRestApi() {
        log.debug("初始化swagger....");
        Predicate<String> path = PathSelectors.any();
        // 禁用正式环境swagger
        if (Arrays.asList(env.getActiveProfiles()).contains("prod")) {
            path = PathSelectors.none();
        }
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerScanPackage))
                .paths(path)
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
