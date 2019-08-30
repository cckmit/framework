package org.mickey.framework.example.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mickey.framework.common.SystemContext;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseSpringTest {
    @Before
    public void setSystemContext() {
        SystemContext.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36");

        SystemContext.setTenantId("test_tenant_id");
        SystemContext.setUserId("test_user_id");
        SystemContext.setUserName("test_user_name");
        SystemContext.setAppId("test_app_id");
    }
}
