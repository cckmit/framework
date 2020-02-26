package org.mickey.framework.e2b.service.e2b.r3convertor;


import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.SystemContext;
import org.mickey.framework.common.util.UUIDUtils;
import org.mickey.framework.e2b.constant.E2BImportConfigConstant;
import org.mickey.framework.e2b.dto.config.ReportConfigDto;
import org.mickey.framework.e2b.dto.report.IcsrEntityDto;
import org.mickey.framework.e2b.dto.report.PatientInfoDto;
import org.mickey.framework.e2b.service.e2b.preview3.R3PreviewDTO;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component(value = "PatientR3Convertor")
public class PatientR3Convertor extends BaseR3Convertor implements ConvertorR3 {
    @Override
    protected String getMappingDTO(ReportConfigDto reportImportconfigWithBLOBs) {
        return reportImportconfigWithBLOBs.getReportPatientInformation();
    }

    @Override
    protected Object getResultInstance(R3PreviewDTO previewDTO) throws IllegalAccessException, InstantiationException {
        PatientInfoDto dto = PatientInfoDto.class.newInstance();
        dto.setId(UUIDUtils.getUuid());
        dto.setReportId(SystemContext.get(E2BImportConfigConstant.SC_PREVIEW_REPORT_ID));
        dto.setIsDelete(false);

        return dto;
    }

    @Override
    protected void fill(String category, IcsrEntityDto reportEntityDto, Object obj) {
        reportEntityDto.setPatientInfoDto((PatientInfoDto) obj);
    }


    @Override
    protected boolean isMappingMatch(Object object, String mappingKey) {
        return true;
    }

    @Override
    protected List getTaimeiPVObjectList(IcsrEntityDto reportEntityDto) {
        return Collections.singletonList(reportEntityDto.getPatientInfoDto());
    }
}
