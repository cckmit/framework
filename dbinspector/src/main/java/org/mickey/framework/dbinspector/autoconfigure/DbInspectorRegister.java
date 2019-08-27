package org.mickey.framework.dbinspector.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mickey.framework.common.SystemConstant;
import org.mickey.framework.dbinspector.DbInspector;
import org.mickey.framework.dbinspector.DbInspectorProperties;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class DbInspectorRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private ConfigurableEnvironment environment;

    private DbInspectorProperties dbInspectorProperties = new DbInspectorProperties();

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
        RelaxedPropertyResolver resolver = new RelaxedPropertyResolver(environment);
        Map<String, Object> properties = resolver.getSubProperties("");
        RelaxedDataBinder binder = new RelaxedDataBinder(dbInspectorProperties, SystemConstant.FRAMEWORK_NS + SystemConstant.DOT + "db-inspector");
        binder.bind(new MutablePropertyValues(properties));
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(DbInspect.class.getName()));
        List<String> basePackages = new ArrayList<String>();
        for (String pkg : annoAttrs.getStringArray("value")) {
            if (StringUtils.isNotBlank(pkg)) {
                basePackages.add(pkg);
            }
        }
        for (String pkg : annoAttrs.getStringArray("basePackages")) {
            if (StringUtils.isNotBlank(pkg)) {
                basePackages.add(pkg);
            }
        }
        for (Class<?> clazz : annoAttrs.getClassArray("basePackageClasses")) {
            basePackages.add(ClassUtils.getPackageName(clazz));
        }
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
        beanDefinition.setNonPublicAccessAllowed(true);
        beanDefinition.setBeanClass(DbInspector.class);

        beanDefinition.getPropertyValues().add("packagesToScan", basePackages);

        beanDefinition.getPropertyValues().addPropertyValue("enabled", dbInspectorProperties.isEnabled());
        beanDefinition.getPropertyValues().addPropertyValue("async", dbInspectorProperties.isAsync());
        String ddlProcessorBeanName = dbInspectorProperties.getDdlProcessorBeanName();
        if (StringUtils.isNoneBlank(ddlProcessorBeanName)) {
            beanDefinition.getPropertyValues().addPropertyValue("ddlProcessor", new RuntimeBeanReference(ddlProcessorBeanName));
        }

        registry.registerBeanDefinition("dbInspector", beanDefinition);
    }
}
