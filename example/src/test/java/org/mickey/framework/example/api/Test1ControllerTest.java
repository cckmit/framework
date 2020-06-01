package org.mickey.framework.example.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mickey.framework.common.SystemContext;
import org.mickey.framework.common.util.HttpUtil;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;

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

    @Test
    public void get() throws IOException {
        HttpUtil.Response response = HttpUtil.get("http://t.weather.sojson.com/api/weather/city/101030100", null, null);
        Object parse = JSON.parse(response.getResult());

        System.out.println(JSON.toJSONString(parse, SerializerFeature.PrettyFormat));
    }
}