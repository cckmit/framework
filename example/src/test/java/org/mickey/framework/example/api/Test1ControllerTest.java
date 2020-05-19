package org.mickey.framework.example.api;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mickey.framework.common.SystemContext;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test1ControllerTest {

    @Resource
    private Test1Controller controller;

    @Before
    public void setUp() throws Exception {
        SystemContext.setLocale("zh-CN");
        SystemContext.setAppId("equipment");
    }

//    @Test
//    public void get() {
//        JsonUtil.print(controller.get());
//    }
}