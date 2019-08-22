package com.taimeitech.framework.common.annotation;

import java.lang.annotation.*;

/**
 * Created by wiki on 17-10-31.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Cacheable {

	String region() default "default";

	long expireSeconds() default 0; //0 会被全局配置复写
}
