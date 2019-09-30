package org.mickey.framework.example;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.dbinspector.autoconfigure.DbInspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@DbInspect(basePackages = "org.mickey.framework.example.po")
@MapperScan("org.mickey.framework.example.mapper")
@SpringBootApplication
@EnableAutoConfiguration
@EnableAspectJAutoProxy
@EnableSwagger2
@ComponentScan(basePackages = {"org.mickey.framework.common.util", "org.mickey.framework.common.config", "org.mickey.framework.example.config", "org.mickey.framework.example.service", "org.mickey.framework.example.api"})
public class ExampleApp {
    public static void main(String[] args) {
        SpringApplication.run(ExampleApp.class, args);
    }
}
