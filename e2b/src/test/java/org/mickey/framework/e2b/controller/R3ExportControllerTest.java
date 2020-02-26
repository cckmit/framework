package org.mickey.framework.e2b.controller;

import org.junit.Test;
import org.mickey.framework.core.test.BaseSpringTest;
import org.mickey.framework.e2b.dto.e2b.E2BR3ExportQueryDto;
import org.mickey.framework.e2b.dto.e2b.E2bR3CompanyInfoDto;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * description
 *
 * @author mickey
 * 2020-02-25
 */
public class R3ExportControllerTest extends BaseSpringTest {

    @Resource
    private R3ExportController controller;

    @Test
    public void exportR3() {
        E2BR3ExportQueryDto exportQueryDto = new E2BR3ExportQueryDto();

        E2bR3CompanyInfoDto r3CompanyInfoDto = new E2bR3CompanyInfoDto();

        exportQueryDto.setR3CompanyInfoDto(r3CompanyInfoDto);

        controller.exportR3(exportQueryDto);
    }
}