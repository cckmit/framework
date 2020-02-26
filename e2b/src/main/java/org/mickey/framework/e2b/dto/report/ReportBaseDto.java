package org.mickey.framework.e2b.dto.report;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mickey.framework.e2b.entity.ReportBase;

import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 2020-02-21
 */
@Slf4j
@Data
public class ReportBaseDto extends ReportBase implements IcsrDtoInterface {
    private Map<String, MeddraFieldInfoDto> meddraMap;
    private Map<String, NullFlavorInfoDto> nullFlavorMap;

    @Override
    public String getReportId() {
        return getId();
    }

    @Override
    public void setReportId(String reportId) {

    }

    public String getSafetyReportNoAndVersion(){
        String safetyReportNo = this.getSafetyReportId();
        if(StringUtils.isNotBlank(this.getVersionNumber())){
            safetyReportNo+="-"+this.getVersionNumber();
        }
        return safetyReportNo;
    }
}
