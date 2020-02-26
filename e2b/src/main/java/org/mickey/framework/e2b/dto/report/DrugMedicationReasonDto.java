package org.mickey.framework.e2b.dto.report;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.e2b.entity.DrugMedicationReason;

import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 2020-02-21
 */
@Slf4j
@Data
public class DrugMedicationReasonDto extends DrugMedicationReason implements IcsrDtoInterface {
    String reportId;
    private Map<String, MeddraFieldInfoDto> meddraMap;
    private Map<String, NullFlavorInfoDto> nullFlavorMap;

    @Override
    public String getCompanyId() {
        return null;
    }

    @Override
    public void setCompanyId(String companyId) {

    }
}
