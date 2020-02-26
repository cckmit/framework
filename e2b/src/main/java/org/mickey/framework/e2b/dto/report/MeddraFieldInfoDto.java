package org.mickey.framework.e2b.dto.report;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.e2b.entity.MeddraFieldInfo;

/**
 * description
 *
 * @author mickey
 * 2020-02-21
 */
@Slf4j
public class MeddraFieldInfoDto extends MeddraFieldInfo {
    public MeddraFieldInfoDto fetchSelf() {
        return this;
    }
}
