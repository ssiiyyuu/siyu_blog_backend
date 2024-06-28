package com.siyu.service_blog.config;

import java.util.Arrays;
import java.util.function.Predicate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableOpenApi
public class SwaggerConfig {
    
    @Bean
    public Docket webApiConfig(){
        RequestParameter token_param = new RequestParameterBuilder()
                                        .name("X-Token")
                                        .description("token")
                                        .in(ParameterType.HEADER)
                                        .build();
        RequestParameter identifier_param = new RequestParameterBuilder()
                                        .name("identifier")
                                        .description("uuid")
                                        .in(ParameterType.HEADER)
                                        .build();
        return new Docket(DocumentationType.OAS_30)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                .paths(Predicate.not(PathSelectors.regex("/admin/.*")))
                .paths(Predicate.not(PathSelectors.regex("/error.*")))
                .build()
                .globalRequestParameters(Arrays.asList(token_param, identifier_param));

    }

    private ApiInfo webApiInfo(){

        return new ApiInfoBuilder()
                .title("API文档")
                .description("本文档描述了个人博客微服务接口定义")
                .version("1.0")
                .contact(new Contact("siyu", "http://siyu.site", "504826715@qq.com"))
                .build();
    }

}
