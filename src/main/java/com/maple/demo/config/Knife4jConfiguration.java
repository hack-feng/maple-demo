package com.maple.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author 笑小枫
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 * @date 2022/6/28
 */
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {

    @Bean(value = "example")
    public Docket example() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("笑小枫实例演示接口")
                        .description("笑小枫实例演示接口")
                        .termsOfServiceUrl("http://127.0.0.1:6666")
                        .contact(new Contact("笑小枫", "http://127.0.0.1:6666", "zfzjava@163.com"))
                        .version("1.0")
                        .build())
                //分组名称
                .groupName("演示实例接口")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.maple.demo.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
