package org.mickey.framework.e2b.dto.report;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.e2b.entity.DrugDose;

import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 2020-02-21
 */
@Slf4j
@Data
public class DrugDoseDto extends DrugDose implements IcsrDtoInterface {
    private Map<String, MeddraFieldInfoDto> meddraMap;
    private Map<String, NullFlavorInfoDto> nullFlavorMap;


    @Override
    public String getCompanyId() {
        return null;
    }

    @Override
    public void setCompanyId(String companyId) {

    }

    @Override
    public String getReportId() {
        return null;
    }

    @Override
    public void setReportId(String reportId) {

    }
}
