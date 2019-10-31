package org.mickey.framework.example.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.dto.ActionResult;
import org.mickey.framework.common.groups.Groups;
import org.mickey.framework.core.api.BaseController;
import org.mickey.framework.core.service.BaseService;
import org.mickey.framework.example.po.Test;
import org.mickey.framework.example.service.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
public class TestController extends BaseController<BaseService, Test> {

}
