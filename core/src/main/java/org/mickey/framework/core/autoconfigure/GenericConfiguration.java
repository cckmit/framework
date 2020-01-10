package org.mickey.framework.core.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.SpringUtils;
import org.mickey.framework.common.SystemConstant;
import org.mickey.framework.common.zookeeper.ZkDistributeLock;
import org.mickey.framework.dbinspector.common.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({CommonProperties.class})
public class GenericConfiguration {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private CommonProperties properties;

//    @Bean
//    @ConditionalOnMissingBean()
//    public I18nProvider redisI18nProvider() {
//        return new RedisI18nProvider();
//    }

    @Bean
    @ConditionalOnProperty(prefix = SystemConstant.FRAMEWORK_NS, name = "zkAddress")
    public ZkDistributeLock distributeLock() {
        return new ZkDistributeLock(properties.getZkAddress());
    }

    @Bean
    @Primary
    public ThreadPoolExecutor threadPoolExecutor() {
        int coreSize = properties.getThreadPool().getCoreSize();
        int maxSize = properties.getThreadPool().getMaxSize();
        int queueCapacity = properties.getThreadPool().getQueueCapacity();
        int keepAliveSeconds = properties.getThreadPool().getKeepAliveSeconds();
        return ThreadPoolExecutor.newThreadPool(coreSize, maxSize, queueCapacity, keepAliveSeconds);
    }
    private static final String APP_NAME = "app.name";
    @PostConstruct
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public void setApplicationContext() {
        SpringUtils.setApplicationContext(applicationContext);
        setAppNameForCatInitConnection();
    }

    /**
     * 应用初始化连接cat服务器端的时候,给app.name赋值
     *  */
    private void setAppNameForCatInitConnection() {
        //begin add by jiangfei  0228
        String applicationName = applicationContext.getEnvironment().getProperty("spring.application.name");
        if(applicationName!=null){
            String appName = System.getProperty(APP_NAME);
            if(appName == null || appName.equals("bootstrap")){
                System.setProperty(APP_NAME, applicationName);
            }
        }
        //end
    }

//    @Bean
//    @ConditionalOnProperty(prefix = SystemConstant.FRAMEWORK_NS, name = "autoWrapResponse", havingValue = "true")
//    public ResponseBodyWrapFactoryBean responseBodyWrapper() {
//        return new ResponseBodyWrapFactoryBean();
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public DefaultExceptionHandler defaultExceptionHandler (){
//        return new DefaultExceptionHandler();
//    }
}
