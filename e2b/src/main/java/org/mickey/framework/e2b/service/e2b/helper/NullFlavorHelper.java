package org.mickey.framework.e2b.service.e2b.helper;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mickey.framework.common.util.CollectionUtil;
import org.mickey.framework.common.util.MapUtils;
import org.mickey.framework.common.util.ReflectionUtil;
import org.mickey.framework.e2b.dto.report.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 2020-02-24
 */
@Slf4j
@Data
public class NullFlavorHelper {

    public static final String MSK = "MSK";
    public static final String ASKU = "ASKU";
    public static final String NASK = "NASK";
    public static final String UNK = "UNK";
    public static final String NA = "NA";
    public static final String NI = "NI";

    public static void addNullflavor(IcsrDtoInterface ni, String fieldName, String value) {
        if (StringUtils.isBlank(fieldName) || StringUtils.isBlank(value)) {
            return;
        }
        if (ni == null) {
            return;
        }
        Map<String, NullFlavorInfoDto> niMap = ni.getNullFlavorMap();
        if (niMap == null) {
            niMap = new HashMap<>();
            ni.setNullFlavorMap(niMap);
        }
        NullFlavorInfoDto nullflavorInfoDto = new NullFlavorInfoDto();
        nullflavorInfoDto.setFieldName(fieldName);
        nullflavorInfoDto.setValue(value);
        niMap.put(fieldName, nullflavorInfoDto);
    }


    public static void rebuildForNullflavor(IcsrEntityDto entityDto) {
        ReportBaseDto reportBaseDto = entityDto.getReportBaseDto();
        rebuildNullForNullflavorField(reportBaseDto);

        List<DrugDto> tmDrugDtoList = entityDto.getDrugDtoList();
        if (CollectionUtil.isNotEmpty(tmDrugDtoList)) {
            for (DrugDto dto : tmDrugDtoList) {
                List<DrugDoseDto> list1 = dto.getDrugDoseDtoList();
                if (CollectionUtil.isNotEmpty(list1)) {
                    for (DrugDoseDto dto1 : list1) {
                        rebuildNullForNullflavorField(dto1);
                    }
                }
                List<DrugMedicationReasonDto> list2 = dto.getMedicationReasonDtoList();
                if (CollectionUtil.isNotEmpty(list2)) {
                    for (DrugMedicationReasonDto dto2 : list2) {
                        rebuildNullForNullflavorField(dto2);
                    }
                }
                rebuildNullForNullflavorField(dto);
            }
        }

        List<AdverseEventDto> dtoList = entityDto.getAdverseEventDtoList();
        if (CollectionUtil.isNotEmpty(dtoList)) {
            for (AdverseEventDto tmAdverseeventDto : dtoList) {
                rebuildNullForNullflavorField(tmAdverseeventDto);
            }
        }

        List<CausalityDto> tmCausalityDtoList = entityDto.getCausalityDtoList();
        if (CollectionUtil.isNotEmpty(tmCausalityDtoList)) {
            for (CausalityDto tmCausalityDto : tmCausalityDtoList) {
                rebuildNullForNullflavorField(tmCausalityDto);
            }
        }

        List<LabDataDto> tmLabdataDtoList = entityDto.getLabDataDtoList();
        if (CollectionUtil.isNotEmpty(tmLabdataDtoList)) {
            for (LabDataDto tmLabdataDto : tmLabdataDtoList) {
                rebuildNullForNullflavorField(tmLabdataDto);
            }
        }

        List<ReporterDto> tmReporterDtoList = entityDto.getReporterDtoList();
        if (CollectionUtil.isNotEmpty(tmReporterDtoList)) {
            for (ReporterDto tmReporterDto : tmReporterDtoList) {
                rebuildNullForNullflavorField(tmReporterDto);
            }
        }

        List<OtherMedicalHistoryDto> tmOthermedicalhistoryDtoList = entityDto.getOtherMedicalHistoryDtoList();
        if (CollectionUtil.isNotEmpty(tmOthermedicalhistoryDtoList)) {
            for (OtherMedicalHistoryDto tmOthermedicalhistoryDto : tmOthermedicalhistoryDtoList) {
                rebuildNullForNullflavorField(tmOthermedicalhistoryDto);
            }
        }

        List<DocumentRetrievalDto> tmDocumentretrievalDtoList = entityDto.getDocumentRetrievalDtoList();
        if (CollectionUtil.isNotEmpty(tmDocumentretrievalDtoList)) {
            for (DocumentRetrievalDto tmDocumentretrievalDto : tmDocumentretrievalDtoList) {
                rebuildNullForNullflavorField(tmDocumentretrievalDto);
            }
        }

        List<RelatedToCodeDto> tmRelatedtocodeDtoList = entityDto.getRelatedToCodeDtoList();
        if (CollectionUtil.isNotEmpty(tmRelatedtocodeDtoList)) {
            for (RelatedToCodeDto tmRelatedtocodeDto : tmRelatedtocodeDtoList) {
                rebuildNullForNullflavorField(tmRelatedtocodeDto);
            }
        }

        List<CauseOfDeathDto> tmCauseOfDeathDtoList = entityDto.getCauseOfDeathDtoList();
        if (CollectionUtil.isNotEmpty(tmCauseOfDeathDtoList)) {
            for (CauseOfDeathDto tmCauseOfDeathDto : tmCauseOfDeathDtoList) {
                rebuildNullForNullflavorField(tmCauseOfDeathDto);
            }
        }

        List<BabyInfoDto> tmBabyInfoDtoList = entityDto.getBabyInfoDtoList();
        if (CollectionUtil.isNotEmpty(tmBabyInfoDtoList)) {
            for (BabyInfoDto tmBabyInfoDto : tmBabyInfoDtoList) {
                rebuildNullForNullflavorField(tmBabyInfoDto);
            }
        }

        List<AttachmentR3Dto> attachmentR3DTOList = entityDto.getAttachmentR3DtoList();
        if (CollectionUtil.isNotEmpty(attachmentR3DTOList)) {
            for (AttachmentR3Dto attachmentR3DTO : attachmentR3DTOList) {
                rebuildNullForNullflavorField(attachmentR3DTO);
            }
        }
    }

    private static void rebuildNullForNullflavorField(IcsrDtoInterface dtoInterface) {
        if (dtoInterface == null) {
            return;
        }
        Map<String, NullFlavorInfoDto> nMap = dtoInterface.getNullFlavorMap();
        if (MapUtils.isEmpty(nMap)) {
            return;
        }
        for (NullFlavorInfoDto nullflavorInfoDto : nMap.values()) {
            if (nullflavorInfoDto == null) {
                continue;
            }
            String fieldName = nullflavorInfoDto.getFieldName();
            String value = nullflavorInfoDto.getValue();
            if (StringUtils.isBlank(value)) {
                continue;
            }
            ReflectionUtil.setPropertyValue(dtoInterface, fieldName, null);
        }
    }
}
