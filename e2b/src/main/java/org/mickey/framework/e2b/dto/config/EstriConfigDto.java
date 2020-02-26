package org.mickey.framework.e2b.dto.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.e2b.po.config.E2bR3M2estriConfig;

/**
 * description
 *
 * @author mickey
 * 2020-02-20
 */
@Slf4j
@Data
public class EstriConfigDto extends E2bR3M2estriConfig {
    private String itemUniqueCode;
    private String elementNumber;
}
