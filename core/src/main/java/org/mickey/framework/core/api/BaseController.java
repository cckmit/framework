package org.mickey.framework.core.api;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.dto.ActionResult;
import org.mickey.framework.common.groups.Groups;
import org.mickey.framework.common.po.AbstractCommonPo;
import org.mickey.framework.core.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public abstract class BaseController<S extends IBaseService, T extends AbstractCommonPo> {
    @Autowired
    private S service;

    @PostMapping("")
    @ApiOperation(value = "insert po")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "created"),
            @ApiResponse(code = 400, message = "字段验证失败"),
            @ApiResponse(code = 417, message = "逻辑异常")
    })
    public ActionResult insert(@Validated(value = Groups.Insert.class)
                               @RequestBody
                               @ApiParam(
                                       example = "{\n" +
                                               "  \"userName\": \"string\"\n" +
                                               "}"
                               ) T dto) {
        service.insert(dto);
        return ActionResult.created();
    }

    @PutMapping("")
    @ApiOperation(value = "update po")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "updated"),
            @ApiResponse(code = 400, message = "字段验证失败"),
            @ApiResponse(code = 417, message = "逻辑异常")
    })
    public ActionResult update(@Validated(value = Groups.Update.class) @RequestBody T dto) {
        service.update(dto);
        return ActionResult.updated();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "query po by id", notes = "id 不能为空")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "字段验证失败"),
            @ApiResponse(code = 417, message = "逻辑异常")
    })
    public ActionResult<T> query(@PathVariable String id) {
        T query = (T)service.query(id);
        return ActionResult.ok(query);
    }

    @GetMapping("")
    @ApiOperation(value = "query list by page info")
    public ActionResult<PageInfo<T>> queryList(@RequestParam(required = false, defaultValue = "1") int pageNo, @RequestParam(required = false, defaultValue = "10") int pageSize) {
        PageInfo<T> query = service.find(pageNo, pageSize);
        return ActionResult.ok(query);
    }

    @GetMapping("/all")
    @ApiOperation(value = "query all list")
    public ActionResult<List<T>> queryAll() {
        return ActionResult.ok(service.findAll());
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete by id")
    public ActionResult delete(@PathVariable String id) {
        service.delete(id);
        return ActionResult.ok();
    }
}
