package com.imooc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {
    // http://localhost:8088/swagger-ui.html
    //配置swagger2核心配置 docket
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2) //指定api类型为swagger2
                .apiInfo(apiInfo())                    //用于定义api文档汇总信息
                .select().apis(RequestHandlerSelectors
                        .basePackage("com.imooc.controller"))//扫描controller
                .paths(PathSelectors.any())  //所有controller
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("天天吃货 电商平台接口API")//文档标题
                .contact(new Contact("imooc",
                        "https://www.imooc.com",
                        "631714470@qq.com"))//联系方式
                .description("专门提供的API文档")//详细描述
                .version("1.0")//文档版本号
                .termsOfServiceUrl("https://www.imooc.com")//网站url
                .build();

    }
}