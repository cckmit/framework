package org.mickey.framework.e2b.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.dto.ActionResult;
import org.mickey.framework.e2b.dto.e2b.E2BR3ExportQueryDto;
import org.mickey.framework.e2b.service.e2b.IE2bExportService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * description
 *
 * @author mickey
 * 2020-02-21
 */
@Slf4j
@Api(tags = "E2B-R3-Export")
@RestController
@RequestMapping("/e2b/r3")
public class R3ExportController {

    @Resource
    private IE2bExportService exportService;

    @PostMapping("/export")
    @ApiOperation("导出E2B R3")
    public ActionResult<String> exportR3(@RequestBody E2BR3ExportQueryDto exportQueryDto) {
        String exportR3 = exportService.exportR3(exportQueryDto);
//        return new ActionResult(exportR3);
        return ActionResult.ok(exportR3);
    }
}
