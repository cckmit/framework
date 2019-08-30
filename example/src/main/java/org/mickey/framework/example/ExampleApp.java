package org.mickey.framework.example;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.dbinspector.autoconfigure.DbInspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

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
public class ExampleApp {
    public static void main(String[] args) {
        SpringApplication.run(ExampleApp.class, args);
    }
}
