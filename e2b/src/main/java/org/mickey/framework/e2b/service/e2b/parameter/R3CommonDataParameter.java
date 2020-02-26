package org.mickey.framework.e2b.service.e2b.parameter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mickey.framework.common.util.CollectionConverter;
import org.mickey.framework.common.util.MapUtils;
import org.mickey.framework.e2b.dto.config.EstriConfigDto;
import org.mickey.framework.e2b.dto.config.HL7ConfigDto;
import org.mickey.framework.e2b.dto.config.ReportConfigDto;
import org.mickey.framework.e2b.service.e2b.handler.R3PreviewItemDTOHandler;

import java.util.Date;
import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 2020-02-22
 */
@Slf4j
public class R3CommonDataParameter {
    private R3PreviewItemDTOHandler r3PreviewItemDTOHandler = new R3PreviewItemDTOHandler();
    /**
     * Key:ElementNumber_CodeValue
     * Value: EstriConfigDto
     */
    private Map<String, EstriConfigDto> r3ValueMapping;

    /**
     * Key: ElementNumber_PVUniqueCode
     * Value: EstriConfigDto
     */
    private Map<String, EstriConfigDto> pvUniqueCodeMapping;
    /**
     * E2B Config Map from tech. PDF
     */
    private Map<String, HL7ConfigDto> e2bR3HL7ConfigMap;
    /**
     * E2B R3 vs PV column
     */
    private ReportConfigDto reportImportconfigWithBLOBs;

    /**
     * Key ElementNumber_OID
     * Value :EstriConfigDto
     */
    private Map<String,EstriConfigDto> oidMapping;

    /**
     * 文档是否需要翻译
     */
    private boolean needTranslate;

    private boolean useFileReceiveReportDate;
    private Date receiveDate;

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public boolean isUseFileReceiveReportDate() {
        return useFileReceiveReportDate;
    }

    public void setUseFileReceiveReportDate(boolean useFileReceiveReportDate) {
        this.useFileReceiveReportDate = useFileReceiveReportDate;
    }

    public boolean isNeedTranslate() {
        return needTranslate;
    }

    public void setNeedTranslate(boolean needTranslate) {
        this.needTranslate = needTranslate;
    }

    public void setOidMapping(Map<String, EstriConfigDto> oidMapping) {
        this.oidMapping = oidMapping;
    }



    public void setPvUniqueCodeMapping(Map<String, EstriConfigDto> pvUniqueCodeMapping) {
        this.pvUniqueCodeMapping = pvUniqueCodeMapping;
    }

    public EstriConfigDto getEstriConfigByOID(String elementNumber,String OID)  {
        if(MapUtils.isEmpty(oidMapping)) {
            return null;
        }
        elementNumber = toOrignalElementNumber(elementNumber);
        String key = CollectionConverter.buildCombineKey(new String[]{elementNumber, OID.toLowerCase()});
        return oidMapping.get(key);
    }

    private String toOrignalElementNumber(String elementNumber) {
        int delimiterIndex = elementNumber.indexOf("_");
        if(delimiterIndex < 2) {
            return elementNumber;
        }
        String realElementNumber = elementNumber.substring(0,delimiterIndex);
        return realElementNumber;
    }

    //理论上应该在通用部分全部转uniqueCode全部转小写，但是由于在全局并没这样做，所以····
    public String getR3ValuebyMapping(String elementNumber,String uniqueCode) {
        if(MapUtils.isEmpty(pvUniqueCodeMapping)) {
            return null;
        }
        elementNumber = toOrignalElementNumber(elementNumber);
        EstriConfigDto obj = pvUniqueCodeMapping.get(CollectionConverter.buildCombineKey(new String[]{StringUtils.trim(elementNumber), StringUtils.trim(uniqueCode)}));
        if(obj == null) {
            return null;
        }
        return obj.getCode();
    }

    public String getItemUniqueCodeByMapping(String elementNumber,String codeValue) {
        if(MapUtils.isEmpty(r3ValueMapping)) {
            return null;
        }
        elementNumber = toOrignalElementNumber(elementNumber);
        EstriConfigDto obj = r3ValueMapping.get(CollectionConverter.buildCombineKey(new String[]{StringUtils.trim(elementNumber), StringUtils.trim(StringUtils.lowerCase(codeValue))}));
        if(obj == null) {
            return null;
        }
        return obj.getItemUniqueCode();
    }

    public ReportConfigDto getReportImportconfigWithBLOBs() {
        return reportImportconfigWithBLOBs;
    }


    public HL7ConfigDto getE2bR3HL7Config(String elementNumber) {
        return e2bR3HL7ConfigMap.get(elementNumber);
    }

    public Map<String, HL7ConfigDto> getE2bR3HL7ConfigMap() {
        return e2bR3HL7ConfigMap;
    }

    public void setE2bR3HL7ConfigMap(Map<String, HL7ConfigDto> e2bR3HL7ConfigMap) {
        this.e2bR3HL7ConfigMap = e2bR3HL7ConfigMap;
    }

    public void setReportImportconfigWithBLOBs(ReportConfigDto reportImportconfigWithBLOBs) {
        this.reportImportconfigWithBLOBs = reportImportconfigWithBLOBs;
    }
    public R3PreviewItemDTOHandler getR3PreviewItemDTOHandler() {
        return r3PreviewItemDTOHandler;
    }

    public Map<String, EstriConfigDto> getR3ValueMapping() {
        return r3ValueMapping;
    }

    public void setR3ValueMapping(Map<String, EstriConfigDto> r3ValueMapping) {
        this.r3ValueMapping = r3ValueMapping;
    }
}
