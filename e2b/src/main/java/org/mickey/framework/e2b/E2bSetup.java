package org.mickey.framework.e2b;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.core.web.SystemRestTemplate;
import org.mickey.framework.dbinspector.autoconfigure.DbInspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * description
 *
 * @author mickey
 * 2020-02-20
 */
@Slf4j
@DbInspect(basePackages = "org.mickey.framework.e2b.po.*")
@MapperScan("org.mickey.framework.e2b.mapper.*")
@ComponentScan(basePackages = {"org.mickey.framework.e2b.config", "org.mickey.framework.e2b.service", "org.mickey.framework.e2b.controller"})
@SpringBootApplication
@EnableAutoConfiguration
@EnableAspectJAutoProxy
//@EnableFeignClients
//@EnableEurekaClient
public class E2bSetup {
    public static void main(String[] args){
        SpringApplication.run(E2bSetup.class, args);
    }

    @Bean
    @LoadBalanced
    public SystemRestTemplate restTemplate() {
        return new SystemRestTemplate();
    }
}
