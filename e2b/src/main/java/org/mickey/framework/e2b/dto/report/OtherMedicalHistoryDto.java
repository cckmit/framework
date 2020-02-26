package org.mickey.framework.e2b.dto.report;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.e2b.entity.OtherMedicalHistory;

import java.util.Date;
import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 2020-02-21
 */
@Slf4j
@Data
public class OtherMedicalHistoryDto extends OtherMedicalHistory implements IcsrDtoInterface {
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
    public String getCreateBy() {
        return null;
    }

    @Override
    public void setCreateBy(String createBy) {

    }

    @Override
    public String getUpdateBy() {
        return null;
    }

    @Override
    public void setUpdateBy(String updateBy) {

    }

    @Override
    public Date getUpdateTime() {
        return null;
    }

    @Override
    public void setUpdateTime(Date updateTime) {

    }
}
