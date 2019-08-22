package org.mickey.framework.dbinspector.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "db-inspector")
public class DbInspectorProperties {

    private boolean enabled = false;
    private String packagesToScan = "";
}
