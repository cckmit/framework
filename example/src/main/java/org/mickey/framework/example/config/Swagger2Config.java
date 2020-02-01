package org.mickey.framework.example.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
// @Slf4j
// @Configuration
public class Swagger2Config {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.mickey.framework.example.api"))
                .paths(PathSelectors.any())
                .build()
                .groupName("Example API")
                .useDefaultResponseMessages(Boolean.FALSE);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("haolun APIs")
                .description("simple apis")
                .termsOfServiceUrl("http://www.gm.com")
                .contact(new Contact("suliyea", "http://xxx", "suliyea@qq.com"))
                .version("1.0")
                .build();
    }
}
