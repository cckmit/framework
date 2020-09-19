package org.mickey.framework.example.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mickey.framework.common.SystemContext;
import org.mickey.framework.common.util.FileUtil;
import org.mickey.framework.common.util.HttpUtil;
import org.mickey.framework.common.util.StringUtil;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
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

    /**
     * base64加密测试
     * @throws IOException
     */
    @Test
    public void base64Test() throws IOException {
        File file = new File("/Users/wangmeng/Pictures/Snipaste_2020-06-03_10-40-57.png");
//        String sunBase64 = new BASE64Encoder().encode(FileUtil.getByteArray(file));
        String encodeBase64String = Base64.encodeBase64String(FileUtil.getByteArray(file));

//        log.info(sunBase64);
        log.info(encodeBase64String);

//        log.info(String.valueOf(StringUtil.equals(sunBase64, encodeBase64String)));
    }
}