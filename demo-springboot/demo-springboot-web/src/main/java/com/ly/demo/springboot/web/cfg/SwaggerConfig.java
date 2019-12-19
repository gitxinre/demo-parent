package com.ly.demo.springboot.web.cfg;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xinre
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                // 方法需要有ApiOperation注解才能生存接口文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //.apis(RequestHandlerSelectors.basePackage("com.ly.demo"))
                // 路径使用any风格
                .paths(PathSelectors.any())
                .build()
                // 如何保护我们的Api，有三种验证（ApiKey, BasicAuth, OAuth）
                //.securitySchemes(security())
                // 接口文档的基本信息
                .apiInfo(apiInfo());
    }

    /**
     * 接口文档详细信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("DEMO API 文档")
                .description("restful 风格接口")
                .contact(new Contact("xinre", "http://www.baidu.com", "abc.tjhq.com"))
                //.termsOfServiceUrl("http://www.localhost:8002")
                .version("1.0.0")
                .build();
    }

    private List<ApiKey> security() {
        ArrayList<ApiKey> apiKeys = new ArrayList<>();
        apiKeys.add(new ApiKey("token", "token", "header"));
        return apiKeys;
    }
}
