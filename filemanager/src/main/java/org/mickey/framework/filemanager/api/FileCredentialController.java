package org.mickey.framework.filemanager.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.dto.ActionResult;
import org.mickey.framework.filemanager.dto.PolicyRequestDto;
import org.mickey.framework.filemanager.dto.PolicyResultDto;
import org.mickey.framework.filemanager.service.IFileCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件上传 身份识别 API
 *
 * @author mickey
 * 2020-02-12
 */
@Api(tags = "File Policy - API")
@RequestMapping("/file/policy")
@RestController
@Slf4j
public class FileCredentialController {
    @Autowired
    private IFileCredentialService credentialService;

    @ApiOperation(value = "获取文件上传 操作 policy")
    @GetMapping(value = "")
    public ActionResult<PolicyResultDto> getPolicy(@RequestBody PolicyRequestDto req) {
        return ActionResult.ok(credentialService.getPolicy(req));
    }
}
