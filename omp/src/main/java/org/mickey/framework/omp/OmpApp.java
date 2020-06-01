package org.mickey.framework.omp;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.core.config.swagger.EnableSwagger2Doc;
import org.mickey.framework.core.web.SystemRestTemplate;
import org.mickey.framework.dbinspector.autoconfigure.DbInspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author wangmeng
 * @date 2020-05-22
 */
@Slf4j
@DbInspect(basePackages = "org.mickey.framework.example.po")
@MapperScan("org.mickey.framework.example.mapper")
@SpringBootApplication
//@EnableEurekaClient
@EnableDiscoveryClient
@EnableAspectJAutoProxy
@EnableSwagger2Doc
@ComponentScan(basePackages = {"org.mickey.framework.omp.config", "org.mickey.framework.omp.service", "org.mickey.framework.omp.api"})
public class OmpApp {
    public static void main(String[] args) {
        SpringApplication.run(OmpApp.class, args);
    }

    @Bean
    @LoadBalanced
    public SystemRestTemplate restTemplate() {
        return new SystemRestTemplate();
    }
}
