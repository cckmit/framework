package org.mickey.framework.e2b.service.e2b.helper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mickey.framework.common.util.MapUtils;
import org.mickey.framework.e2b.dto.e2b.E2BR3SingleExportDto;

import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 2020-02-24
 */
@Slf4j
public class DrugDoseExportR3Helper {

    public static void singleCustomProcess(E2BR3SingleExportDto drugExportDTO) {
        Map<String, List<E2BR3SingleExportDto>> drugChilds = drugExportDTO.getChildrenMap();
        if(MapUtils.isEmpty(drugChilds)) {
            return;
        }
        List<E2BR3SingleExportDto> doseInfos = drugChilds.get("G.k.4.r");
        if(CollectionUtils.isEmpty(doseInfos)) {
            return;
        }
        for(E2BR3SingleExportDto doseInfo: doseInfos) {
            String dose2 = doseInfo.getValueMap().get("G.k.4.r.2");
            if (StringUtils.isBlank(dose2)) {
                dose2 = doseInfo.getNullFlavorValueMap().get("G.k.4.r.2");
            }
            String dose4 = doseInfo.getValueMap().get("G.k.4.r.4");
            if (StringUtils.isBlank(dose4)) {
                dose4 = doseInfo.getNullFlavorValueMap().get("G.k.4.r.4");
            }
            String dose5 = doseInfo.getValueMap().get("G.k.4.r.5");
            if (StringUtils.isBlank(dose5)) {
                dose5 = doseInfo.getNullFlavorValueMap().get("G.k.4.r.5");
            }
            String dose6a = doseInfo.getValueMap().get("G.k.4.r.6a");
            if (StringUtils.isBlank(dose6a)) {
                dose6a = doseInfo.getNullFlavorValueMap().get("G.k.4.r.6a");
            }
            if(StringUtils.isBlank(dose2) && StringUtils.isBlank(dose4) && StringUtils.isBlank(dose5) && StringUtils.isBlank(dose6a)) {
                doseInfo.putValue("G.k.4.r.format","BLANK");
                continue;
            }

            if(StringUtils.isNotBlank(dose2) && StringUtils.isNotBlank(dose4) && StringUtils.isNotBlank(dose5) && StringUtils.isNotBlank(dose6a)) {
                //<effectiveTime xsi:type="SXPR_TS">  这里有3个comp 节点   A
                doseInfo.putValue("G.k.4.r.format","3COMP");
                continue;
            }

            if (StringUtils.isNotBlank(dose2) && StringUtils.isBlank(dose4) && StringUtils.isBlank(dose5) && StringUtils.isBlank(dose6a) ) {
                //<effectiveTime xsi:type="PIVL_TS">  1节点  C
                doseInfo.putValue("G.k.4.r.format","PIVL");
                continue;
            }

            if (StringUtils.isNotBlank(dose2)) { //在这里表示dose2不为空，但是一定存在dose4/dose5/dose6a其中一个
                //<effectiveTime xsi:type="SXPR_TS"> 两个comp 节点  B
                doseInfo.putValue("G.k.4.r.format","2COMP");
                continue;
            }

            if(StringUtils.isNotBlank(dose4) && StringUtils.isNotBlank(dose5) && StringUtils.isNotBlank(dose6a)) {
                doseInfo.putValue("G.k.4.r.format","2COMP_IVL");
                continue;
            }

            doseInfo.putValue("G.k.4.r.format","IVL");
        }
    }

    private static void customProcess(Map<String, List<E2BR3SingleExportDto>> data) {
        List<E2BR3SingleExportDto> drugInfos = data.get("G.k");
        if(CollectionUtils.isEmpty(drugInfos)) {
            return;
        }
        for(E2BR3SingleExportDto drugExportDTO: drugInfos) {
            singleCustomProcess(drugExportDTO);
        }
    }

}
