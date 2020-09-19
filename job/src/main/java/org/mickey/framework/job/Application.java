package org.mickey.framework.job;

import org.mickey.framework.core.config.swagger.EnableSwagger2Doc;
import org.mickey.framework.core.web.SystemRestTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;

/**
 * #Description
 *
 * @author wangmeng
 * @date 2020-06-11
 */
@SpringBootApplication
@EnableSwagger2Doc
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @LoadBalanced
    public SystemRestTemplate restTemplate() {
        return new SystemRestTemplate();
    }
}
