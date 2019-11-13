package org.mickey.framework.i18n;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.core.config.swagger.EnableSwagger2Doc;
import org.mickey.framework.dbinspector.autoconfigure.DbInspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@DbInspect(basePackages = "org.mickey.framework.i18n.po")
@MapperScan("org.mickey.framework.i18n.mapper")
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableSwagger2Doc
@EnableDiscoveryClient
@ComponentScan(basePackages = {"org.mickey.framework.i18n.service",
        "org.mickey.framework.i18n.api"})
public class I18nApp {
    public static void main(String[] args) {
        SpringApplication.run(I18nApp.class, args);

        log.info("I18nApp started");
    }
}
