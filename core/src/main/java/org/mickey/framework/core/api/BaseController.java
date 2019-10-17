package org.mickey.framework.core.api;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.dto.ActionResult;
import org.mickey.framework.common.groups.Groups;
import org.mickey.framework.common.po.CommonPo;
import org.mickey.framework.core.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public abstract class BaseController<S extends BaseService, T extends CommonPo> {
    @Autowired
    private S service;

    @PostMapping("")
    @ApiOperation(value = "insert user", notes = "注意关注点")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "字段验证失败"),
            @ApiResponse(code = 417, message = "逻辑异常", response = ActionResult.class)
    })
    public ActionResult insert(@Validated(value = Groups.Insert.class) @RequestBody T dto) {
        service.insert(dto);
        return ActionResult.Created();
    }

    @PutMapping("")
    @ApiOperation(value = "update user", notes = "注意关注点")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Updated"),
            @ApiResponse(code = 400, message = "字段验证失败"),
            @ApiResponse(code = 417, message = "逻辑异常", response = ActionResult.class)
    })
    public ActionResult update(@Validated(value = Groups.Update.class) @RequestBody T dto) {
        service.update(dto);
        return ActionResult.Updated();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get user by id", notes = "id 不能为空")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "字段验证失败"),
            @ApiResponse(code = 417, message = "逻辑异常", response = ActionResult.class)
    })
    public ActionResult<T> query(@PathVariable String id) {
        T query = (T)service.query(id);
        return ActionResult.Ok(query);
    }

    @GetMapping("/")
    @ApiOperation(value = "query all list")
    public ActionResult<PageInfo<T>> queryList(@RequestParam(required = false, defaultValue = "1") int pageNo, @RequestParam(required = false, defaultValue = "10") int pageSize) {
        PageInfo<T> query = service.find(pageNo, pageSize);
        return ActionResult.Ok(query);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete by id")
    public ActionResult delete(@PathVariable String id) {
        service.delete(id);
        return ActionResult.Ok();
    }
}
