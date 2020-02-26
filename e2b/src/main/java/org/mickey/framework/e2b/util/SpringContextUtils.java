package org.mickey.framework.e2b.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * description
 *
 * @author mickey
 * 2020-02-24
 */
@Slf4j
@Data
@Component
public class SpringContextUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        applicationContext = ctx;
    }

    public static Object getBeanById(String id){
        return applicationContext.getBean(id);
    }

    public static Object getBeanByClass(Class c){
        return applicationContext.getBean(c);
    }
}
