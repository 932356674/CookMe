package com.gss.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket create(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(true)
                .select()
                .paths(Predicates.or(PathSelectors.regex("/sys/.*")))
                .build();
    }
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("CookMe")//大标题
                .description("CookMe在线接口文档")//详细描述
                .version("1.0")//版本
                .contact(new Contact("java", "http://gss.com", "123456@qq.com"))//作者
                .license("The Apache License, Version 2.0")//许可证信息
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")//许可证地址
                .build();

    }


}
