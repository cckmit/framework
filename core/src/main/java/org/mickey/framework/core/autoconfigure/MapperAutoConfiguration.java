package org.mickey.framework.core.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.util.StringUtil;
import org.mickey.framework.core.mapping.MapperFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(CommonProperties.class)
public class MapperAutoConfiguration {
    private Logger logger = LoggerFactory.getLogger(MapperAutoConfiguration.class);
    @Value("${mapper.configLocations:}")
    @Deprecated
    private String configLocations;
    @Autowired
    private CommonProperties commonProperties;

    private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    @Bean
    @ConditionalOnMissingBean
    public MapperFactoryBean createMapper() {
        MapperFactoryBean mapperFactoryBean = new MapperFactoryBean();
        //new config files
        if (StringUtil.isNotBlank(commonProperties.getMapperConfigLocations())) {
            List<Resource> resources = new ArrayList<>();
            Arrays.stream(commonProperties.getMapperConfigLocations().split(",")).forEach(configFile -> {
                try {
                    Resource[] resolverResources = resourcePatternResolver.getResources(configFile);
                    Collections.addAll(resources, resolverResources);
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            });
            mapperFactoryBean.setConfigFiles(resources.toArray(new Resource[resources.size()]));
        }
        //old config files
        if (StringUtil.isNotBlank(configLocations)) {
            List<Resource> resources = new ArrayList<>();
            Arrays.stream(configLocations.split(",")).forEach(configFile -> {
                try {
                    Resource[] resolverResources = resourcePatternResolver.getResources(configFile);
                    Collections.addAll(resources, resolverResources);
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            });
            mapperFactoryBean.setConfigFiles(resources.toArray(new Resource[resources.size()]));
        }
        return mapperFactoryBean;
    }

}
