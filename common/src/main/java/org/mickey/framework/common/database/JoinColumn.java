package org.mickey.framework.common.database;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * description
 *
 * @author mickey
 * 05/07/2019
 */
@Data
public class JoinColumn {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private String name;
    private String referencedColumnName = "id";

    public JoinColumn(String name) {
        this.name = name;
    }

    public JoinColumn(String name, String referencedColumnName) {
        this.name = name;
        this.referencedColumnName = referencedColumnName;
    }
}
