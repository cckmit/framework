package org.mickey.framework.core.autoconfigure.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.core.mybatis.sharding.impl.DoubleWriteManager;
import org.mickey.framework.core.mybatis.sharding.impl.JdbcManager;
import org.mickey.framework.core.mybatis.sharding.impl.RedisManager;
import org.mickey.framework.dbinspector.mybatis.ShardingScannerListener;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class ShardingManagerRegister implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {

    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(EnableSharding.class.getName()));

        GenericBeanDefinition shardingTableScanner = new GenericBeanDefinition();
        shardingTableScanner.setBeanClass(ShardingScannerListener.class);
        shardingTableScanner.setScope(BeanDefinition.SCOPE_SINGLETON);
        registry.registerBeanDefinition("shardingTableScanner", shardingTableScanner);

        GenericBeanDefinition jdbcManager = new GenericBeanDefinition();
        jdbcManager.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        jdbcManager.setScope(BeanDefinition.SCOPE_SINGLETON);
        jdbcManager.setNonPublicAccessAllowed(true);
        jdbcManager.setBeanClass(JdbcManager.class);
        registry.registerBeanDefinition("jdbcShardingManager", jdbcManager);

        GenericBeanDefinition redisShardingManager = new GenericBeanDefinition();
        redisShardingManager.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        redisShardingManager.setScope(BeanDefinition.SCOPE_SINGLETON);
        redisShardingManager.setNonPublicAccessAllowed(true);
        redisShardingManager.setBeanClass(RedisManager.class);
        redisShardingManager.getPropertyValues().add("redisTemplate", new RuntimeBeanReference("shardingStringRedisTemplate"));
        registry.registerBeanDefinition("redisShardingManager", redisShardingManager);

        GenericBeanDefinition defaultShardingManager = new GenericBeanDefinition();
        defaultShardingManager.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        defaultShardingManager.setScope(BeanDefinition.SCOPE_SINGLETON);
        defaultShardingManager.setNonPublicAccessAllowed(true);
        defaultShardingManager.setPrimary(true);
        defaultShardingManager.setBeanClass(DoubleWriteManager.class);

        defaultShardingManager.getPropertyValues().add("poolExecutor", new RuntimeBeanReference("threadPoolExecutor"));
        defaultShardingManager.getPropertyValues().add("jdbcShardingManager", new RuntimeBeanReference("jdbcShardingManager"));
        defaultShardingManager.getPropertyValues().add("redisShardingManager", new RuntimeBeanReference("redisShardingManager"));
        registry.registerBeanDefinition("defaultShardingManager", defaultShardingManager);
    }
}
