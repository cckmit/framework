package org.mickey.framework.i18n.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.dto.ActionResult;
import org.mickey.framework.core.api.BaseController;
import org.mickey.framework.i18n.po.I18nDictionary;
import org.mickey.framework.i18n.service.i18ndictionary.II18nDictionaryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * description
 *
 * @author codeGeneric
 * 2019/11/11
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/I18nDictionary", produces = APPLICATION_JSON_VALUE)
@Api(tags = "I18nDictionary API")
public class I18nDictionaryController extends BaseController<II18nDictionaryService, I18nDictionary> {

    @Resource
    private II18nDictionaryService i18nService;

    @GetMapping("/appId/{appId}")
    @ApiOperation(value = "query po by id", notes = "id 不能为空")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "字段验证失败"),
            @ApiResponse(code = 417, message = "逻辑异常", response = ActionResult.class)
    })
    public ActionResult<Map<String, String>> queryByAppId(@PathVariable String appId) {
        return ActionResult.ok(i18nService.queryByAppId(appId));
    }
}