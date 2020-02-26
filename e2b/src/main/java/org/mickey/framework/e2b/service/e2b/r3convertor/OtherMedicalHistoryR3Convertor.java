package org.mickey.framework.e2b.service.e2b.r3convertor;

import org.mickey.framework.e2b.constant.E2BImportConfigConstant;
import org.mickey.framework.e2b.constant.ItemUniqueCodeProvider;
import org.mickey.framework.e2b.dto.config.ReportConfigDto;
import org.mickey.framework.e2b.dto.e2b.E2BR3SingleExportDto;
import org.mickey.framework.e2b.dto.report.IcsrEntityDto;
import org.mickey.framework.e2b.dto.report.OtherMedicalHistoryDto;
import org.mickey.framework.e2b.service.e2b.parameter.R3CommonDataParameter;
import org.mickey.framework.e2b.service.e2b.preview3.R3PreviewDTO;
import org.mickey.framework.e2b.service.e2b.preview3.R3PreviewItemDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mickey.framework.common.SystemContext;
import org.mickey.framework.common.util.UUIDUtils;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * description
 *
 * @author mickey
 * 24/07/2019
 */
@Slf4j
@Component(value="OtherMedicalHistoryR3Convertor")
public class OtherMedicalHistoryR3Convertor extends BaseR3Convertor implements ConvertorR3 {
    static Map<String, Set> elementNumber2Type;
    static final  String COLUMN_NAME_MEDICAL_HISTORY_TYPE = "medicalhistorytype";
    static final String COLUMN_NAME_MEDICAL_HISTORY_CONTINUE ="medicalhistorystillcontinue";
    static {
        elementNumber2Type= new HashMap<>();

        //R3 中d.7.1.r 对应了既往病史和现病史
        Set<String> mh = new HashSet<>();
        mh.add(ItemUniqueCodeProvider.PATIENT_MEDICINE_HISTORY);
        mh.add(ItemUniqueCodeProvider.PRESENT_ILLNESS_HISTORY);
        //D.8.r 对应了药物史
        Set<String> mdh = new HashSet<>();
        mdh.add(ItemUniqueCodeProvider.PATIENT_PAST_DRUG_THERAPY);

        elementNumber2Type.put(E2BImportConfigConstant.MEDICATION_NUM,mh);
        elementNumber2Type.put(E2BImportConfigConstant.MEDICATION_DRUG_NUM,mdh);
    }
    @Override
    protected String getMappingDTO(ReportConfigDto reportImportconfigWithBLOBs) {
        return reportImportconfigWithBLOBs.getOtherMedicalHistory();
    }

    @Override
    protected Object getResultInstance(R3PreviewDTO previewDTO) throws IllegalAccessException, InstantiationException {
        OtherMedicalHistoryDto dto = OtherMedicalHistoryDto.class.newInstance();
        dto.setId(UUIDUtils.getUuid());
        dto.setReportId(SystemContext.get(E2BImportConfigConstant.SC_PREVIEW_REPORT_ID));
        dto.setIsDelete(false);
        return dto;
    }

    @Override
    protected void fill(String category, IcsrEntityDto reportEntityDto, Object obj) {
        reportEntityDto.addOtherMedicalHistoryDto((OtherMedicalHistoryDto)obj);
    }

    @Override
    protected boolean isMappingMatch(Object object, String mappingKey) {
        OtherMedicalHistoryDto dto = (OtherMedicalHistoryDto) object;
        String historyType = dto.getMedicalHistoryType();
        return elementNumber2Type.get(mappingKey).contains(historyType);
    }

    @Override
    protected List getTaimeiPVObjectList(IcsrEntityDto reportEntityDto) {
        return reportEntityDto.getOtherMedicalHistoryDtoList();
    }

    @Override
    protected void customPreview(R3PreviewDTO previewDTO, String subElementNumber, R3CommonDataParameter commonDataParameter) {
        super.customPreview(previewDTO, subElementNumber, commonDataParameter);
        String value = previewDTO.fetchPreviewItemDTOValue(COLUMN_NAME_MEDICAL_HISTORY_CONTINUE);

        String type=null;

        if(StringUtils.equalsIgnoreCase(E2BImportConfigConstant.MEDICATION_NUM,subElementNumber)) {
            if(StringUtils.equalsIgnoreCase(ItemUniqueCodeProvider.MEDICAL_HISTORY_STILL_CONTINUE_YES,value)) {
                type=ItemUniqueCodeProvider.PRESENT_ILLNESS_HISTORY;
            } else {
                type=ItemUniqueCodeProvider.PATIENT_MEDICINE_HISTORY;
            }
        } else if(StringUtils.equalsIgnoreCase("D.8.r",subElementNumber)) {
            type=ItemUniqueCodeProvider.PATIENT_PAST_DRUG_THERAPY;
        }

        if(type == null) {
            log.info(" The Medication Type is NULL for:" + subElementNumber + " continue value:" + value);
        }

        R3PreviewItemDTO previewItemDTO = new R3PreviewItemDTO(COLUMN_NAME_MEDICAL_HISTORY_TYPE,type);

        previewDTO.put(COLUMN_NAME_MEDICAL_HISTORY_TYPE,previewItemDTO);
        previewDTO.setSubCategory(type);
    }

    @Override
    protected void customExport(E2BR3SingleExportDto dto, Object obj) {
        super.customExport(dto, obj);
        Map<String, String> nullflavorValueMap = dto.getNullFlavorValueMap();
        if (StringUtils.isBlank(nullflavorValueMap.get("D.7.1.r.3"))) {
            OtherMedicalHistoryDto tmOthermedicalhistoryDto = (OtherMedicalHistoryDto) obj;
            String medicalhistorystillcontinue = tmOthermedicalhistoryDto.getMedicalHistoryStillContinue();
            if (StringUtils.equals(medicalhistorystillcontinue, ItemUniqueCodeProvider.OTHERHISTORY_CONTINUE_UK)) {
                dto.getValueMap().remove("D.7.1.r.3");
                nullflavorValueMap.put("D.7.1.r.3", "UNK");
            }
        }
    }
}
