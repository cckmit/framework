package org.mickey.framework.core.autoconfigure.mybatis;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({ShardingManagerRegister.class,})
@AutoConfigureAfter(ChainedMybatisInterceptorConfiguration.class)
public @interface EnableSharding {
}
