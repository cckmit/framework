package org.mickey.framework.example.api;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.core.api.BaseController;
import org.mickey.framework.example.po.Test2;
import org.mickey.framework.example.service.test.ITest2Service;
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
@RequestMapping(value = "/api/test/2", produces = APPLICATION_JSON_VALUE)
@Api(tags = "test 2 api")
public class Test2Controller extends BaseController<ITest2Service, Test2> {

}
