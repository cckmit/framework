package org.mickey.framework.example.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.dto.ActionResult;
import org.mickey.framework.core.api.BaseController;
import org.mickey.framework.core.web.SystemRestTemplate;
import org.mickey.framework.example.po.Test1;
import org.mickey.framework.example.service.test.ITest1Service;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@RestController
@RequestMapping(value = "/test", produces = APPLICATION_JSON_VALUE)
@Api(tags = "test 1 api")
public class Test1Controller extends BaseController<ITest1Service, Test1> {

    @Resource
    private SystemRestTemplate restTemplate;

    @GetMapping("/rest/demo")
    public ActionResult<Object> get() throws URISyntaxException {

        String forObject = restTemplate.getForObject(new URI("https://www.giuem.com/bing-image-get-php/"), String.class);
        return ActionResult.ok(forObject);
    }
}
