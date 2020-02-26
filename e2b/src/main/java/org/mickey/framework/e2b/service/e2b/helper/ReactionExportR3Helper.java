package org.mickey.framework.e2b.service.e2b.helper;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
@Data
public class ReactionExportR3Helper {
    private static void customProcess(Map<String, List<E2BR3SingleExportDto>> data) {
        List<E2BR3SingleExportDto> result = data.get("E.i");
        if(CollectionUtils.isEmpty(result)) {
            return;
        }
        for(E2BR3SingleExportDto exportDto: result) {
            singleCustomProcess(exportDto);
        }
    }

    public static void singleCustomProcess(E2BR3SingleExportDto exportDto) {
        String value4 = exportDto.getValueMap().get("E.i.4");
        String value5 = exportDto.getValueMap().get("E.i.5");
        String value6a = exportDto.getValueMap().get("E.i.6a");
        if(StringUtils.isNotBlank(value5) && StringUtils.isNotBlank(value6a)) {
            exportDto.putValue("E.i.format","2COMP");
        }
    }
}
