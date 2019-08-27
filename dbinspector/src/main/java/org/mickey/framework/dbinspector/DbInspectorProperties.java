package org.mickey.framework.dbinspector;

import lombok.Data;
import org.mickey.framework.common.SystemConstant;
import org.mickey.framework.dbinspector.dialect.impl.MysqlDialect;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = SystemConstant.FRAMEWORK_NS + SystemConstant.DOT + "db-inspector")
public class DbInspectorProperties {

    private String dialectClass = MysqlDialect.class.getName();

    private boolean enabled = false;

    private boolean async = false;

    private String ddlProcessorBeanName = "jdbcDdlProcessor";

}
