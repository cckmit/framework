package org.mickey.framework.e2b.service.e2b.r3convertor;

import org.mickey.framework.e2b.constant.E2BImportConfigConstant;
import org.mickey.framework.e2b.dto.report.IcsrEntityDto;
import org.mickey.framework.e2b.dto.report.ReportBaseDto;
import org.mickey.framework.e2b.dto.config.ReportConfigDto;
import org.mickey.framework.e2b.service.e2b.parameter.R3CommonDataParameter;
import org.mickey.framework.e2b.service.e2b.preview3.R3PreviewDTO;
import org.mickey.framework.e2b.service.e2b.preview3.R3PreviewItemDTO;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.SystemContext;
import org.mickey.framework.common.util.DateUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Component(value="ReportBaseR3Convertor")
public class ReportBaseR3Convertor extends BaseR3Convertor implements ConvertorR3 {
    @Override
    protected String getMappingDTO(ReportConfigDto reportImportconfigWithBLOBs) {
        return reportImportconfigWithBLOBs.getReportBase();
    }

    @Override
    protected void customPreview(R3PreviewDTO previewDTO, String subElementNumber, R3CommonDataParameter commonDataParameter) {
        super.customPreview(previewDTO, subElementNumber, commonDataParameter);
        if (!commonDataParameter.isUseFileReceiveReportDate()) {
            R3PreviewItemDTO reportReceiveDate = previewDTO.getPreviewItemDTOMap().get("reportReceiveDate");
            if (reportReceiveDate != null) {
                for (String key : reportReceiveDate.getProcessedValueMap().keySet()) {
                    reportReceiveDate.getProcessedValueMap().put(key, DateUtils.format(commonDataParameter.getReceiveDate(), DateUtils.YDMW));
                }
            }
        }
    }

    @Override
    protected Object getResultInstance(R3PreviewDTO previewDTO) throws IllegalAccessException, InstantiationException {
        ReportBaseDto dto = ReportBaseDto.class.newInstance();
        dto.setId(SystemContext.get(E2BImportConfigConstant.SC_PREVIEW_REPORT_ID));
//        dto.setFlevelstatus(0);
        dto.setReportStatus(0);
//        dto.setIsknow(false);
        dto.setIsDelete(false);
        dto.setIsInvalid("");
        dto.setCompanyId(SystemContext.getTenantId());
        dto.setCreateTime(new Date());
        dto.setCreateBy(SystemContext.getUserId());
        dto.setReportSource(0);
        dto.setPsurDrugIds("");
        return dto;
    }

    @Override
    protected void fill(String category, IcsrEntityDto reportEntityDto, Object obj) {
        ReportBaseDto dto = (ReportBaseDto)obj;
        reportEntityDto.setReportBaseDto(dto);
    }

    @Override
    protected boolean isMappingMatch(Object object, String mappingKey) {
        return true;
    }

    @Override
    protected List getTaimeiPVObjectList(IcsrEntityDto reportEntityDto) {
        return Collections.singletonList(reportEntityDto.getReportBaseDto());
    }
}

