package org.mickey.framework.e2b.service.e2b.r3convertor;

import org.mickey.framework.e2b.constant.E2BImportConfigConstant;
import org.mickey.framework.e2b.dto.report.IcsrEntityDto;
import org.mickey.framework.e2b.dto.report.ReporterDto;
import org.mickey.framework.e2b.dto.config.ReportConfigDto;
import org.mickey.framework.e2b.service.e2b.preview3.R3PreviewDTO;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.SystemContext;
import org.mickey.framework.common.util.UUIDUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * description
 *
 * @author mickey
 * 24/07/2019
 */
@Slf4j
@Component(value = "ReporterR3Convertor")
public class ReporterR3Convertor extends BaseR3Convertor implements ConvertorR3 {
    @Override
    protected String getMappingDTO(ReportConfigDto reportImportconfigWithBLOBs) {
        return reportImportconfigWithBLOBs.getReporter();
    }

    @Override
    protected Object getResultInstance(R3PreviewDTO previewDTO) throws IllegalAccessException, InstantiationException {
        ReporterDto dto = ReporterDto.class.newInstance();
        dto.setId(UUIDUtils.getUuid());
        dto.setReportId(SystemContext.get(E2BImportConfigConstant.SC_PREVIEW_REPORT_ID));
        dto.setIsDelete(false);

        return dto;
    }

    @Override
    protected void fill(String category, IcsrEntityDto reportEntityDto, Object obj) {
        reportEntityDto.addReporterDto((ReporterDto) obj);
    }


    @Override
    protected boolean isMappingMatch(Object object, String mappingKey) {
        return true;
    }

    @Override
    protected List getTaimeiPVObjectList(IcsrEntityDto reportEntityDto) {
        return reportEntityDto.getReporterDtoList();
    }
}
