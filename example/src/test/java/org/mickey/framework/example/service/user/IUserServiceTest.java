package org.mickey.framework.example.service.user;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.mickey.framework.example.po.UserPo;
import org.mickey.framework.example.test.BaseSpringTest;

import javax.annotation.Resource;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class IUserServiceTest extends BaseSpringTest {

    @Resource
    private IUserService userService;

    @Test
    public void testMapper() {
        UserPo userPo = new UserPo();
        userPo.setUserName("mickey");
        userPo.setNickname("mickey");
        userPo.setPassword("password");
        userPo.setAge(18);
        userService.insert(userPo);
        userService.query(userPo.getId());
        userService.findAll();
        userPo.setAge(20);
        userService.update(userPo);
        userService.query(userPo.getId());
        userService.delete(userPo.getId());
        userService.query(userPo.getId());
    }
}