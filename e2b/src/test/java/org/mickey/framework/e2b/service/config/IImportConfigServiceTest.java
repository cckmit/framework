package org.mickey.framework.e2b.service.config;

import org.junit.Test;
import org.mickey.framework.core.test.BaseSpringTest;
import org.mickey.framework.e2b.constant.E2BImportConfigConstant;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * description
 *
 * @author mickey
 * 2020-02-21
 */
public class IImportConfigServiceTest extends BaseSpringTest {

    @Autowired
    private IImportConfigService service;

    @Test
    public void queryByUniqueCode() {
        print(service.queryByUniqueCode(E2BImportConfigConstant.E2B_CONFIG_R3_UNIQUECODE));
    }
}