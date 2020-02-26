package org.mickey.framework.e2b.service.e2b.r3convertor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mickey.framework.common.SystemContext;
import org.mickey.framework.common.util.UUIDUtils;
import org.mickey.framework.e2b.constant.E2BImportConfigConstant;
import org.mickey.framework.e2b.dto.config.ReportConfigDto;
import org.mickey.framework.e2b.dto.e2b.E2BR3SingleExportDto;
import org.mickey.framework.e2b.dto.report.IcsrEntityDto;
import org.mickey.framework.e2b.dto.report.LabDataDto;
import org.mickey.framework.e2b.service.e2b.parameter.R3CommonDataParameter;
import org.mickey.framework.e2b.service.e2b.preview3.R3PreviewDTO;
import org.mickey.framework.e2b.service.e2b.preview3.R3PreviewItemDTO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * description
 *
 * @author mickey
 * 24/07/2019
 */
@Slf4j
@Component(value = "LabdataR3Convertor")
public class LabdataR3Convertor extends BaseR3Convertor implements ConvertorR3 {
//    @Resource
//    private IItemService itemService;

    @Override
    protected String getMappingDTO(ReportConfigDto reportImportconfigWithBLOBs) {
        return reportImportconfigWithBLOBs.getLabData();
    }

    @Override
    protected Object getResultInstance(R3PreviewDTO previewDTO) throws IllegalAccessException, InstantiationException {
        LabDataDto dto = LabDataDto.class.newInstance();
        dto.setId(UUIDUtils.getUuid());
        dto.setReportId(SystemContext.get(E2BImportConfigConstant.SC_PREVIEW_REPORT_ID));
        dto.setIsDelete(false);
        return dto;
    }

    @Override
    protected void customPreview(R3PreviewDTO previewDTO, String subElementNumber, R3CommonDataParameter commonDataParameter) {
        super.customPreview(previewDTO, subElementNumber, commonDataParameter);
        R3PreviewItemDTO labdataresultsItem = previewDTO.fetchPreviewItemDTO("labdataresults");
        if (labdataresultsItem != null) {
            String catchNumber = labdataresultsItem.getCatchDataElementNumber();
            if (StringUtils.isNotBlank(catchNumber)) {
                String op = E2BImportConfigConstant.labdataResultOperatorMap.get(catchNumber);
                labdataresultsItem.setValue(op + labdataresultsItem.getValue());
            }
        }

    }

    @Override
    protected void customExport(E2BR3SingleExportDto dto, Object obj) {
        Map<String, String> valueMap = dto.getValueMap();
        LabDataDto tmLabdataDto = (LabDataDto) obj;
        String result = tmLabdataDto.getLabDataResults();
        if (StringUtils.isNotBlank(result) && result.length() > 1) {
            String op = result.substring(0, 1);
            String format = E2BImportConfigConstant.operatorLabdataResultMap.get(op);
            if (StringUtils.isNotBlank(format)) {
                String value = result.substring(1);
                dto.putValue("F.r.3.2", value);
                dto.putValue("F.r.3.2.format", format);
            } else {
                dto.putValue("F.r.3.2", result);
                dto.putValue("F.r.3.2.format", "eq");
            }
        } else {
            dto.putValue("F.r.3.2", null);
            dto.putValue("F.r.3.2.format", null);
        }
        if (valueMap != null) {
            String resultCode = valueMap.get("F.r.3.2");
            String unitCode = valueMap.get("F.r.3.3");
            String units = tmLabdataDto.getUnits();
            if (StringUtils.isBlank(unitCode) && StringUtils.isNotBlank(units)) {
//                Map<String, String> itemsByUniCodes = itemService.getItemsByUniCodes(Collections.singletonList(units));
//                valueMap.put("F.r.3.4", resultCode + "[" + Objects.toString(itemsByUniCodes.get(units), "") + "]");
                valueMap.put("F.r.3.4", resultCode + "[" + units + "]");
                valueMap.remove("F.r.3.2");
                valueMap.remove("F.r.3.3");
            }
        }
    }

    @Override
    protected void fill(String category, IcsrEntityDto reportEntityDto, Object obj) {
        reportEntityDto.addLabDataDto((LabDataDto) obj);
    }

    @Override
    protected boolean isMappingMatch(Object object, String mappingKey) {
        return true;
    }

    @Override
    protected List getTaimeiPVObjectList(IcsrEntityDto reportEntityDto) {
        return reportEntityDto.getLabDataDtoList();
    }
}
