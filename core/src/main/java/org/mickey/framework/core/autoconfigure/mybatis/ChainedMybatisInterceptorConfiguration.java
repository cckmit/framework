package org.mickey.framework.core.autoconfigure.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.core.autoconfigure.CommonProperties;
import org.mickey.framework.core.mybatis.interceptor.ChainedInterceptor;
import org.mickey.framework.core.mybatis.interceptor.DefaultChainedInterceptor;
import org.mickey.framework.core.mybatis.interceptor.NamedWrapperInterceptor;
import org.mickey.framework.core.mybatis.interceptor.SimpleBatchInterceptor;
import org.mickey.framework.core.mybatis.saas.SaasSelectMdmInterceptor;
import org.mickey.framework.core.mybatis.saas.SaasUpdateMdmInterceptor;
import org.mickey.framework.core.mybatis.sharding.ShardingListByIdsInterceptor;
import org.mickey.framework.core.mybatis.unique.UqConstraintInterceptor;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Configuration
@AutoConfigureAfter(MybatisAutoConfiguration.class)
public class ChainedMybatisInterceptorConfiguration implements EnvironmentAware {

    private static String propertyPrefix = "mybatis.";

    @Autowired
    private CommonProperties properties;

    private Environment environment;

//    private RelaxedPropertyResolver resolver;

    DefaultChainedInterceptor chainedInterceptor;

    @Bean
    public ChainedInterceptor chainedInterceptor() {
        chainedInterceptor = new DefaultChainedInterceptor();
        if (properties.isEnableDbUniqueCheck()) {
            chainedInterceptor.addInterceptor(ChainedInterceptor.UQ_CONSTRAINT_ORDER, new UqConstraintInterceptor());
        }
        chainedInterceptor.addInterceptor(ChainedInterceptor.SIMPLE_BATCH_ORDER, new SimpleBatchInterceptor());
        chainedInterceptor.addInterceptor(ChainedInterceptor.SHARDING_LIST_BY_IDS, new ShardingListByIdsInterceptor());
        addSaasInterceptor();
        return chainedInterceptor;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
//        resolver = new RelaxedPropertyResolver(environment, propertyPrefix);
    }

    private void addSaasInterceptor() {
        //mdm 配置
        boolean enabled = Boolean.valueOf(environment.getProperty("saas.enabled"));
        String ignores = environment.getProperty("saas.ignores");

//        boolean enabled = Boolean.valueOf(resolver.getProperty("saas.enabled", Boolean.FALSE.toString()));
//        String ignores = resolver.getProperty("saas.ignores", "");
        if (!enabled) {
            return;
        }
        SaasUpdateMdmInterceptor updateInterceptor = new SaasUpdateMdmInterceptor();
        updateInterceptor.setIgnoreIds(ignores);
        SaasSelectMdmInterceptor selectInterceptor = new SaasSelectMdmInterceptor();
        selectInterceptor.setIgnoreIds(ignores);
        chainedInterceptor.addInterceptor(201, new NamedWrapperInterceptor("sas-select", selectInterceptor));
        chainedInterceptor.addInterceptor(202, new NamedWrapperInterceptor("sas-update", updateInterceptor));

    }
}
