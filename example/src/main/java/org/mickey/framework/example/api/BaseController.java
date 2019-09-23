package org.mickey.framework.example.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.dto.ActionResult;
import org.mickey.framework.common.util.RedisUtil;
import org.mickey.framework.example.po.UserPo;
import org.mickey.framework.example.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@RestController("/base")
@Api(tags = "test")
public class BaseController {

    @Autowired
    private IUserService userService;
    @Autowired
    private RedisUtil redisUtil;

    private final String Redis_key = "test_redis_key";

    @GetMapping("/hello")
    @ApiOperation("hello func")
    public ActionResult<String> helloWorld() {
        return new ActionResult<>("hello world");
    }

    @PostMapping("")
    @ApiOperation("add user")
    public ActionResult add(UserPo userPo) {
        userService.insert(userPo);
        return new ActionResult();
    }

    @GetMapping("/list")
    public ActionResult<List<UserPo>> queryAll() {
        List<UserPo> poList = userService.findAll();
        redisUtil.set(Redis_key, poList);
        return new ActionResult<>(poList);
    }
}
