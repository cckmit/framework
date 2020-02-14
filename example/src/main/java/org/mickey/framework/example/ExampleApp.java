package org.mickey.framework.example;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.core.config.swagger.EnableSwagger2Doc;
import org.mickey.framework.core.web.SystemRestTemplate;
import org.mickey.framework.dbinspector.autoconfigure.DbInspect;
import org.mickey.framework.filemanager.client.FileClient;
import org.mickey.framework.filemanager.config.CosProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.client.RestTemplate;

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
@EnableEurekaClient
@EnableDiscoveryClient
@EnableAspectJAutoProxy
@EnableSwagger2Doc
@ComponentScan(basePackages = {"org.mickey.framework.example.config", "org.mickey.framework.example.service", "org.mickey.framework.example.api"})
public class ExampleApp {
    public static void main(String[] args) {
        SpringApplication.run(ExampleApp.class, args);
    }

    @Bean
    @LoadBalanced
    public SystemRestTemplate restTemplate() {
        return new SystemRestTemplate();
    }
}
