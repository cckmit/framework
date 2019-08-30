package org.mickey.framework.core.autoconfigure.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.core.mybatis.listener.MybatisListenerContainer;
import org.mickey.framework.core.mybatis.listener.impl.DefaultPreInsertListener;
import org.mickey.framework.core.mybatis.listener.impl.DefaultPreUpdateListener;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Configuration
public class MybatisListenerAutoConfiguration {
    @PostConstruct
    public void addListeners() {
        log.info("auto regist mybatis listener");
        MybatisListenerContainer.registListener(new DefaultPreInsertListener());
        MybatisListenerContainer.registListener(new DefaultPreUpdateListener());
    }
}
