package org.mickey.framework.e2b.service.e2b.helper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.mickey.framework.common.util.CollectionConverter;
import org.mickey.framework.common.util.CollectionUtil;
import org.mickey.framework.common.util.DateUtils;
import org.mickey.framework.e2b.dto.report.*;
import org.mickey.framework.e2b.util.R3UUIDUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 2020-02-24
 */
@Slf4j
public class E2BR3ReportEntityHelper {
    /**
     * 将Causality从drug下放置回到reportEntity下面
     * 给causalityDto 设置eventID
     * @param reportEntityDto
     */
    public static void buildCausalityEventIDAndSet2ReportEntityForImport(IcsrEntityDto reportEntityDto) {
        //构建fileEventID -> EventID 的对应关系
        List<AdverseEventDto> reactions = reportEntityDto.getAdverseEventDtoList();
        Map<String,AdverseEventDto> reactionsMap= CollectionConverter.list2Map(reactions, r -> r.getFileEventId());
        //设置DrugID
        List<DrugDto> drugs = reportEntityDto.getDrugDtoList();
        List<CausalityDto> wholeList = new ArrayList<>();
        for(DrugDto drug: drugs) {
            List<CausalityDto> causalityDtoList = drug.getRCausalityDtoList();
            if(CollectionUtil.isNotEmpty(causalityDtoList)) {
                wholeList.addAll(causalityDtoList);
                for(CausalityDto causalityDto:causalityDtoList) {
                    causalityDto.setCalDrugType(drug.getDrugType());
                    AdverseEventDto reactionDTO = reactionsMap.get(causalityDto.getFileEventId());
                    if(reactionDTO != null) {
                        String eventId = reactionDTO.getId();
                        causalityDto.setAdverseEventId(eventId);
                    }
                    //这个其实在前面的过程已经设置过了，写在这里就是为了看起来安心
                    causalityDto.setDrugId(drug.getId());
                    fillCausalityWithDrugAndEvent(causalityDto,drug,reactionDTO);
                }
            }
        }
        reportEntityDto.setCausalityDtoList(wholeList);
    }

    /**
     将reaction 和 drug 信息填充到causality中，其实这个方法是可以重复使用
     **/
    private static void fillCausalityWithDrugAndEvent(CausalityDto tmCausalityDto,DrugDto drugItem,AdverseEventDto eventItem) {
        tmCausalityDto.setDechallenge(drugItem.getDechallenge());
        tmCausalityDto.setRechallenge(drugItem.getRechallenge());
        tmCausalityDto.setManufacturer(drugItem.getManufacture());
        tmCausalityDto.setCalDrugType(drugItem.getDrugType());
        tmCausalityDto.setCalActionTakenforSuspectDrug(drugItem.getActionTakenforSuspectDrug());
        tmCausalityDto.setReportId(drugItem.getReportId());
        tmCausalityDto.setDrugId(drugItem.getId());
        tmCausalityDto.setProduct(drugItem.getGenericName());

        tmCausalityDto.setCausalityAssessmentAmendment("");
        tmCausalityDto.setCausalityAssessmentNaranjoquestionnaire("");
        tmCausalityDto.setReporterCausality("");
        tmCausalityDto.setReporterEvaluation("");
        tmCausalityDto.setCreateTime(DateUtils.getCurrentDate());
        tmCausalityDto.setCanReset(0);
        tmCausalityDto.setIsDelete(false);

        if(eventItem == null) {
            return;
        }

        tmCausalityDto.setAdverseEventId(eventItem.getId());
        tmCausalityDto.setEvent(eventItem.getEventTerm());
    }


    /**
     * 对reportEntity进行重整
     * 1. 将Causality 放置到Drug 下面
     * 2. 将Causality 赋值fileDrugId fileEventId
     * 3  将Causality 赋值
     * @param reportEntityDto
     */
    public static void rebuildReportEntityForExport(IcsrEntityDto reportEntityDto) {
        List<DrugDto> tmDrugDtos = reportEntityDto.getDrugDtoList();
        Map<String, DrugDto> drugMap = CollectionConverter.list2Map(tmDrugDtos, d -> d.getId());
        if(CollectionUtil.isNotEmpty(tmDrugDtos)) {
            tmDrugDtos.forEach(p -> p.setFileDrugId(R3UUIDUtil.uuidR3Format(p.getId())));
        }

        List<AdverseEventDto> tmEventList = reportEntityDto.getAdverseEventDtoList();
        if(CollectionUtil.isNotEmpty(tmEventList)) {
            tmEventList.forEach(p -> p.setFileEventId(R3UUIDUtil.uuidR3Format(p.getId())));
        }

        List<CausalityDto> tmCausalityList = reportEntityDto.getCausalityDtoList();
        for(CausalityDto causalityDto : tmCausalityList) {
            String drugID=causalityDto.getDrugId();
            String eventID=causalityDto.getAdverseEventId();
            DrugDto drugDto = drugMap.get(drugID);
            if(drugDto != null) {
                drugDto.addRCausalityDto(causalityDto);
            } else {
                log.error("rebuildReportEntity didn't found related drugID:" + drugID);
            }

            causalityDto.setFileDrugId(R3UUIDUtil.uuidR3Format(drugID));
            causalityDto.setFileEventId(R3UUIDUtil.uuidR3Format(eventID));
            List<CausalityItemDto> itemList = causalityDto.getCausalityItemDtoList();
            if(CollectionUtils.isEmpty(itemList)) {
                continue;
            }
            for(CausalityItemDto itemDTO: itemList) {
                itemDTO.setFileDrugId(R3UUIDUtil.uuidR3Format(drugID));
                itemDTO.setFileEventId(R3UUIDUtil.uuidR3Format(eventID));
            }
        }
    }
}
