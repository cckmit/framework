package org.mickey.framework.e2b.service.e2b.r3convertor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mickey.framework.common.SystemContext;
import org.mickey.framework.e2b.constant.E2BImportConfigConstant;
import org.mickey.framework.e2b.dto.config.ReportConfigDto;
import org.mickey.framework.e2b.dto.report.CauseOfDeathDto;
import org.mickey.framework.e2b.dto.report.IcsrEntityDto;
import org.mickey.framework.e2b.service.e2b.parameter.R3CommonDataParameter;
import org.mickey.framework.e2b.service.e2b.preview3.R3MappingDTO;
import org.mickey.framework.e2b.service.e2b.preview3.R3PreviewDTO;
import org.mickey.framework.e2b.service.e2b.preview3.R3PreviewItemDTO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * description
 *
 * @author mickey
 * 24/07/2019
 */
@Slf4j
@Component(value="DeathCauseR3Convertor")
public class DeathCauseR3Convertor extends BaseR3Convertor implements ConvertorR3 {
    static final String COLUMN_NAME_TYPE="type";
    protected static Map<String,String> elementNumber2Type;
    static {
        elementNumber2Type = new HashMap<>();
        elementNumber2Type.put(E2BImportConfigConstant.DEATH_CAUSE_NUM,"死亡原因");
        elementNumber2Type.put(E2BImportConfigConstant.AUTOPSY_NUM,"尸检结果");
        elementNumber2Type.put(E2BImportConfigConstant.REPORT_DIAGNOSE_NUM,"报告诊断");
    }

    @Override
    protected String getMappingDTO(ReportConfigDto reportImportconfigWithBLOBs) {
        return reportImportconfigWithBLOBs.getCauseOfDeath();
    }

    @Override
    protected Map<String, List<R3MappingDTO>> rebuildConfig(String mappingMapString) {
        Map<String, List<R3MappingDTO>> mappingMap = super.rebuildConfig(mappingMapString);
        mappingMap.remove(E2BImportConfigConstant.REPORT_DIAGNOSE_NUM);
        return mappingMap;
    }

    @Override
    protected Object getResultInstance(R3PreviewDTO previewDTO) throws IllegalAccessException, InstantiationException {
        CauseOfDeathDto dto = CauseOfDeathDto.class.newInstance();
        dto.setId(UUID.randomUUID().toString());
        dto.setReportId(SystemContext.get(E2BImportConfigConstant.SC_PREVIEW_REPORT_ID));
        dto.setIsDelete(false);
        return dto;
    }

    @Override
    protected void fill(String category, IcsrEntityDto reportEntityDto, Object obj) {
        reportEntityDto.addCauseOfDeathDto((CauseOfDeathDto)obj);
    }

    @Override
    protected boolean isMappingMatch(Object object, String mappingKey) {
        CauseOfDeathDto dto = (CauseOfDeathDto) object;
        String type = elementNumber2Type.get(mappingKey);
        return StringUtils.equalsIgnoreCase(dto.getType(),type);
    }

    @Override
    protected List getTaimeiPVObjectList(IcsrEntityDto reportEntityDto) {
        return reportEntityDto.getCauseOfDeathDtoList();
    }

    @Override
    protected void customPreview(R3PreviewDTO previewDTO, String subElementNumber, R3CommonDataParameter commonDataParameter) {
        super.customPreview(previewDTO, subElementNumber, commonDataParameter);
        String type = elementNumber2Type.get(subElementNumber);
        R3PreviewItemDTO previewItemDTO = new R3PreviewItemDTO(COLUMN_NAME_TYPE,type);
        previewDTO.put(COLUMN_NAME_TYPE,previewItemDTO);
        previewDTO.setSubCategory(type);
    }
}
