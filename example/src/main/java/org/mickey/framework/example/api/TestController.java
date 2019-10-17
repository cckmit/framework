package org.mickey.framework.example.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.dto.ActionResult;
import org.mickey.framework.common.groups.Groups;
import org.mickey.framework.core.api.BaseController;
import org.mickey.framework.example.po.Test;
import org.mickey.framework.example.service.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/test", produces = APPLICATION_JSON_VALUE)
@Api(tags = "test base api")
public class TestController {

    //  extends BaseController<TestService, Test>

    @Autowired
    private TestService service;

    @PostMapping("")
    @ApiOperation(value = "insert user", notes = "注意关注点")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "字段验证失败"),
            @ApiResponse(code = 417, message = "逻辑异常", response = ActionResult.class)
    })
    public ActionResult insert(@Validated(value = Groups.Insert.class) @RequestBody Test dto) {
        service.insert(dto);
        return ActionResult.Created();
    }

}
