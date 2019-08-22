package com.taimeitech.framework.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({})
@Retention(RUNTIME)
public @interface DbCustomUniqueConstraint {

	String name() default "";

	String valueGetter(); //返回String
}
