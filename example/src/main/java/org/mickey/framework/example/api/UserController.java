package org.mickey.framework.example.api;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.dto.ActionResult;
import org.mickey.framework.common.groups.Groups;
import org.mickey.framework.example.po.UserPo;
import org.mickey.framework.example.service.user.IUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/user", produces = APPLICATION_JSON_VALUE)
@Api(tags = "user api")
public class UserController {

    @Resource
    private IUserService userService;

    @PostMapping("")
    @ApiOperation(value = "insert user", notes = "注意关注点")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = java.lang.Void.class),
            @ApiResponse(code = 400, message = "字段验证失败"),
            @ApiResponse(code = 417, message = "逻辑异常", response = ActionResult.class)
    })
    public ActionResult insert(@Validated(value = Groups.Insert.class) @RequestBody UserPo user) {
        userService.insert(user);
        ActionResult result = ActionResult.Created();
        return result;
    }

    @PutMapping("")
    @ApiOperation(value = "update user", notes = "注意关注点")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Updated"),
            @ApiResponse(code = 400, message = "字段验证失败"),
            @ApiResponse(code = 417, message = "逻辑异常", response = ActionResult.class)
    })
    public ActionResult update(@Validated(value = Groups.Update.class) @RequestBody UserPo user) {
        userService.update(user);
        return ActionResult.Updated();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get user by id", notes = "id 不能为空")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserPo.class),
            @ApiResponse(code = 400, message = "字段验证失败"),
            @ApiResponse(code = 417, message = "逻辑异常", response = ActionResult.class)
    })
    public ActionResult<UserPo> query(@PathVariable String id) {
        UserPo query = userService.query(id);
        return ActionResult.Ok(query);
    }

    @GetMapping("/")
    @ApiOperation(value = "query all list")
    public ActionResult<PageInfo<UserPo>> queryList(@RequestParam(required = false, defaultValue = "1") int pageNo, @RequestParam(required = false, defaultValue = "10") int pageSize) {
        PageInfo<UserPo> query = userService.find(pageNo, pageSize);
        return ActionResult.Ok(query);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete by id")
    public ActionResult delete(@PathVariable String id) {
        userService.delete(id);
        return ActionResult.Ok();
    }
}
