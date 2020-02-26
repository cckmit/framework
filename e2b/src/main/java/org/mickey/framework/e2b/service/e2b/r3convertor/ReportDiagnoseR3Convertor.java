package org.mickey.framework.e2b.service.e2b.r3convertor;


import org.mickey.framework.e2b.constant.E2BImportConfigConstant;
import org.mickey.framework.e2b.service.e2b.preview3.R3MappingDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 24/07/2019
 */
@Slf4j
@Component(value = "ReportDiagnoseR3Convertor")
public class ReportDiagnoseR3Convertor extends DeathCauseR3Convertor implements ConvertorR3 {
    @Override
    protected Map<String, List<R3MappingDTO>> rebuildConfig(String mappingMapString) {
        Map<String, List<R3MappingDTO>> mappings = baseRebuildConfig(mappingMapString);
        mappings.remove(E2BImportConfigConstant.DEATH_CAUSE_NUM);
        mappings.remove(E2BImportConfigConstant.AUTOPSY_NUM);
        return mappings;
    }
}
