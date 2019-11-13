package org.mickey.framework.i18n.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mickey.framework.common.SystemContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class I18nDictionaryControllerTest {
    @Autowired
    private I18nDictionaryController controller;

    private String id;

    @Before
    public void setUp() throws Exception {
        SystemContext.setTenantId("tenantId");
        SystemContext.setLocale("en-US");

        id = "40289FC76E5916BB016E592DBA6C0005";
    }

    @Test
    public void delete() {
        controller.delete(id);
    }
}