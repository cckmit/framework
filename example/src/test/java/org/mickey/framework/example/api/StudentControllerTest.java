package org.mickey.framework.example.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mickey.framework.example.constant.GenderEnum;
import org.mickey.framework.example.po.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * description
 *
 * @author wangmeng
 * @version 1.0.0
 * @ClassName StudentControllerTest.java
 * @Description TODO
 * @createTime 2020年06月19日 11:27:00
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentControllerTest {

    @Autowired
    private StudentController api;

    @Test
    public void parseJson() {
        Student student = new Student();

        student.setName("祝某人");
        student.setAge(18);
        student.setGender(GenderEnum.男);
        student.setHeight(184.43);
        student.setWeight(72.2);

        log.info(JSON.toJSONString(student, SerializerFeature.PrettyFormat));
    }

    @Test
    public void insert() {

        Student student = JSON.parseObject("{\n" +
                        "\t\"age\":15,\n" +
                        "\t\"gender\":\"1\",\n" +
                        "\t\"height\":164.43,\n" +
                        "\t\"name\":\"猪妹妹\",\n" +
                        "\t\"weight\":52.2\n" +
                        "}"
                , Student.class);

        api.insert(student);
    }

    @Test
    public void update() {
    }

    @Test
    public void query() {
    }

    @Test
    public void queryList() {

    }

    @Test
    public void queryAll() {
        log.info(
                JSON.toJSONString(
                        api.queryAll(), SerializerFeature.PrettyFormat));
    }

    @Test
    public void delete() {
    }
}