package org.mickey.framework.e2b.dto.report;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.e2b.entity.CaseSummary;

import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 2020-02-21
 */
@Slf4j
@Data
public class CaseSummaryDto extends CaseSummary implements IcsrDtoInterface {
    Map<String, NullFlavorInfoDto> nullFlavorMap;

    @Override
    public String getCompanyId() {
        return null;
    }

    @Override
    public void setCompanyId(String companyId) {

    }

    @Override
    public Boolean getIsDelete() {
        return null;
    }

    @Override
    public void setIsDelete(Boolean isDelete) {

    }

    @Override
    public Map<String, MeddraFieldInfoDto> getMeddraMap() {
        return null;
    }

    @Override
    public void setMeddraMap(Map<String, MeddraFieldInfoDto> meddraMap) {

    }
}
