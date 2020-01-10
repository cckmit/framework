package org.mickey.framework.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface DbCustomUniqueConstraint {

    String name() default "";

    String valueGetter();
}
