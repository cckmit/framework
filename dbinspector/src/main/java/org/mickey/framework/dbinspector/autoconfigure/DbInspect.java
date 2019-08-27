package org.mickey.framework.dbinspector.autoconfigure;

import org.mickey.framework.dbinspector.DbInspectorProperties;
import org.mickey.framework.dbinspector.dialect.Dialect;
import org.mickey.framework.dbinspector.dialect.impl.MysqlDialect;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;
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
@Import({DbInspectorRegister.class, DdlProcessorConfigure.class})
@AutoConfigureAfter(DataSource.class)
@EnableConfigurationProperties(DbInspectorProperties.class)
public @interface DbInspect {
    Class<? extends Dialect> dialect() default MysqlDialect.class;
    String[] value() default {};
    String[] basePackages() default {};
    Class<?>[] basePackageClasses() default {};
}
