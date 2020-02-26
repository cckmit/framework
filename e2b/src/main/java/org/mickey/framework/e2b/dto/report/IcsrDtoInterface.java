package org.mickey.framework.e2b.dto.report;

import org.mickey.framework.common.util.ReflectionUtil;
import org.mickey.framework.e2b.dto.report.MeddraFieldInfoDto;
import org.mickey.framework.e2b.dto.report.NullFlavorInfoDto;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 2020-02-21
 */
public interface IcsrDtoInterface extends Serializable {

    String getId();
    void setId(String id);

    String getCompanyId();
    void setCompanyId(String companyId);

    String getCreateBy();
    void setCreateBy(String createBy);

    Date getCreateTime();
    void setCreateTime(Date createTime);

    String getUpdateBy();
    void setUpdateBy(String updateBy);

    Date getUpdateTime();
    void setUpdateTime(Date updateTime);

    String getReportId();
    void setReportId(String reportId);

    Boolean getIsDelete();
    void setIsDelete(Boolean isDelete);

    Map<String, MeddraFieldInfoDto> getMeddraMap();
    void setMeddraMap(Map<String, MeddraFieldInfoDto> meddraMap);

    Map<String, NullFlavorInfoDto> getNullFlavorMap();
    void setNullFlavorMap(Map<String, NullFlavorInfoDto> nullFlavorInfoDtoMap);

    default MeddraFieldInfoDto getMeddraFieldInfoDto(String fieldName) {
        if (getMeddraMap() != null && getMeddraMap().size() > 0) {
            return getMeddraMap().get(fieldName);
        }
        return null;
    }

    default String getMeddraFieldInfoDtoValue(String fieldName, String meddraFieldName) {
        MeddraFieldInfoDto meddraFieldInfoDto = getMeddraFieldInfoDto(fieldName);
        if (meddraFieldInfoDto != null) {
            return ReflectionUtil.getPropertyValue2String(meddraFieldInfoDto, meddraFieldName);
        }
        return "";
    }
}
