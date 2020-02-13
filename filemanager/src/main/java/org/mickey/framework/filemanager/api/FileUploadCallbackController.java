package org.mickey.framework.filemanager.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.dto.ActionResult;
import org.mickey.framework.filemanager.dto.UploadCallbackDto;
import org.mickey.framework.filemanager.service.IFileCallbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件上传后，回调保存上传文件信息
 *
 * @author mickey
 * 2020-02-12
 */
@Api(tags = "File upload Callback - API")
@RequestMapping("/file/upload/callback")
@RestController
@Slf4j
public class FileUploadCallbackController {
    @Autowired
    private IFileCallbackService callbackService;

    @ApiOperation(value = "文件上传后回调接口(用于保存文件上传记录)")
    @PostMapping(value = "")
    public ActionResult callback(@RequestBody UploadCallbackDto callbackDto) {
        callbackService.callback(callbackDto);

        return ActionResult.ok();
    }

    public ActionResult delete(String id) {
        callbackService.delete(id);

        return ActionResult.updated();
    }
}
