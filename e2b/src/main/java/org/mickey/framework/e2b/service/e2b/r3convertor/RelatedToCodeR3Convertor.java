package org.mickey.framework.e2b.service.e2b.r3convertor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mickey.framework.common.SystemContext;
import org.mickey.framework.common.util.UUIDUtils;
import org.mickey.framework.e2b.constant.E2BImportConfigConstant;
import org.mickey.framework.e2b.constant.ItemUniqueCodeProvider;
import org.mickey.framework.e2b.dto.config.ReportConfigDto;
import org.mickey.framework.e2b.dto.report.IcsrEntityDto;
import org.mickey.framework.e2b.dto.report.RelatedToCodeDto;
import org.mickey.framework.e2b.service.e2b.parameter.R3CommonDataParameter;
import org.mickey.framework.e2b.service.e2b.preview3.R3PreviewDTO;
import org.mickey.framework.e2b.service.e2b.preview3.R3PreviewItemDTO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 24/07/2019
 */
@Slf4j
@Component(value = "RelatedToCodeR3Convertor")
public class RelatedToCodeR3Convertor extends BaseR3Convertor implements ConvertorR3 {
    public static Map<String, String> elementNumber2Type;
    public static Map<String, String> exportType2ElementNumber;

    static {
        elementNumber2Type = new HashMap<>();
        exportType2ElementNumber = new HashMap<>();
        elementNumber2Type.put(E2BImportConfigConstant.RELATETOCODE_PRESYSTEM_NUM, ItemUniqueCodeProvider.E2BPRESYTEMCODE);
        elementNumber2Type.put(E2BImportConfigConstant.RELATETOCODE_OTHER_NUM, ItemUniqueCodeProvider.RELATED_CODE);
        elementNumber2Type.put(E2BImportConfigConstant.RELATETOCODE_IMPORTE2B_NUM, ItemUniqueCodeProvider.E2BReportCode);

        exportType2ElementNumber.put(ItemUniqueCodeProvider.E2BPRESYTEMCODE, E2BImportConfigConstant.RELATETOCODE_PRESYSTEM_NUM);
        exportType2ElementNumber.put(ItemUniqueCodeProvider.SAME_PATIENT_CODE, E2BImportConfigConstant.RELATETOCODE_OTHER_NUM);
        exportType2ElementNumber.put(ItemUniqueCodeProvider.SAME_REPORTER_CODE, E2BImportConfigConstant.RELATETOCODE_OTHER_NUM);
        exportType2ElementNumber.put(ItemUniqueCodeProvider.RELATED_CODE, E2BImportConfigConstant.RELATETOCODE_OTHER_NUM);
        exportType2ElementNumber.put(ItemUniqueCodeProvider.CONSIN_CODE, E2BImportConfigConstant.RELATETOCODE_OTHER_NUM);
        exportType2ElementNumber.put(ItemUniqueCodeProvider.CHILD_CODE, E2BImportConfigConstant.RELATETOCODE_OTHER_NUM);
        exportType2ElementNumber.put(ItemUniqueCodeProvider.E2BReportCode, E2BImportConfigConstant.RELATETOCODE_OTHER_NUM);
    }

    @Override
    protected void customPreview(R3PreviewDTO previewDTO, String subElementNumber, R3CommonDataParameter commonDataParameter) {
        super.customPreview(previewDTO, subElementNumber, commonDataParameter);
        //typeitemid
        String elementType = elementNumber2Type.get(subElementNumber);
        R3PreviewItemDTO dto = new R3PreviewItemDTO("typeItemId", elementType);
        dto.setIsDisplay(false);
        previewDTO.put(dto.getName(), dto);
    }

    @Override
    protected String getMappingDTO(ReportConfigDto reportImportconfigWithBLOBs) {
        return reportImportconfigWithBLOBs.getRelatedToCode();
    }

    @Override
    protected Object getResultInstance(R3PreviewDTO previewDTO) throws IllegalAccessException, InstantiationException {
        RelatedToCodeDto dto = RelatedToCodeDto.class.newInstance();
        dto.setId(UUIDUtils.getUuid());
        dto.setReportId(SystemContext.get(E2BImportConfigConstant.SC_PREVIEW_REPORT_ID));
        dto.setIsDelete(false);
        return dto;
    }

    @Override
    protected void fill(String category, IcsrEntityDto reportEntityDto, Object obj) {
        reportEntityDto.addRelatedToCodeDto((RelatedToCodeDto) obj);
    }

    @Override
    protected boolean isMappingMatch(Object object, String mappingKey) {
        RelatedToCodeDto dto = (RelatedToCodeDto) object;
        String elementNumber = exportType2ElementNumber.get(dto.getTypeItemId());
        return StringUtils.equalsIgnoreCase(elementNumber, mappingKey);
    }

    @Override
    protected List getTaimeiPVObjectList(IcsrEntityDto reportEntityDto) {
        return reportEntityDto.getRelatedToCodeDtoList();
    }
}
