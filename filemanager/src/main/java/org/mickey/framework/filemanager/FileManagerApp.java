package org.mickey.framework.filemanager;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.core.config.swagger.EnableSwagger2Doc;
import org.mickey.framework.core.web.SystemRestTemplate;
import org.mickey.framework.dbinspector.autoconfigure.DbInspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * description
 *
 * @author mickey
 * 2020-02-12
 */
@Slf4j
@DbInspect(basePackages = "org.mickey.framework.filemanager.po")
@MapperScan("org.mickey.framework.filemanager.mapper")
@SpringBootApplication
@EnableAutoConfiguration
@EnableEurekaClient
@EnableDiscoveryClient
@EnableAspectJAutoProxy
@EnableSwagger2Doc
@ComponentScan(basePackages = {"org.mickey.framework.filemanager.config", "org.mickey.framework.filemanager.service", "org.mickey.framework.filemanager.api"})
public class FileManagerApp {
    public static void main(String[] args) {
        SpringApplication.run(FileManagerApp.class, args);
    }

    @Bean
    @LoadBalanced
    public SystemRestTemplate restTemplate() {
        return new SystemRestTemplate();
    }
}
