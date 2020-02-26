package org.mickey.framework.e2b.service.e2b.preview3;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mickey.framework.common.util.MapUtils;
import org.mickey.framework.common.util.UUIDUtils;
import org.mickey.framework.e2b.dto.report.MeddraFieldInfoDto;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 2020-02-22
 */
@Slf4j
public class R3PreviewItemDTO {
    private R3MappingDTO r3MappingDTO;

    private String id;
    /**
     * Field Name could be same to the Mapping Field Name
     */
    private String name;
    /**
     * value without translated
     */
    private String value="";
    /**
     * if need translate, this is translated value
     */
    private String finalValue="";
    /**
     * Null flavor value
     */
    private String nullFlavorValue;
    /**
     * Final Null Flavor Value
     */
    private String finalNullFlavorValue;

    /**
     * 配置多个elementnumber时候，理论上只有一个生效，记录下这个生效的节点
     */
    private String catchDataElementNumber;

    /**
     * Meddra Version
     */
    private String meddraVersion;
    /**
     * Meddra Value from file
     */
    private String meddraValue;

    private String sourceValue;

    /**
     * Meddra Data DTO
     */
    private MeddraFieldInfoDto meddrafidldinfoDto;
    private MeddraFieldInfoDto finalMeddrafidldinfoDto;

    /**
     * Key: related xpath in file
     */
    private Map<String, String> sourceValueMap = new LinkedHashMap<>(10);
    private Map<String, String> processedValueMap = new LinkedHashMap<>(10);

    private boolean isDisplay=true;

    private boolean plainText;

    public boolean isPlainText() {
        return plainText;
    }

    public void setPlainText(boolean plainText) {
        this.plainText = plainText;
    }

    public String getCatchDataElementNumber() {
        return catchDataElementNumber;
    }

    public void setCatchDataElementNumber(String catchDataElementNumber) {
        this.catchDataElementNumber = catchDataElementNumber;
    }


    public String getFinalNullFlavorValue() {
        return finalNullFlavorValue;
    }

    public void setFinalNullFlavorValue(String finalNullFlavorValue) {
        this.finalNullFlavorValue = finalNullFlavorValue;
    }

    public String getNullFlavorValue() {
        return nullFlavorValue;
    }

    public void setNullFlavorValue(String nullFlavorValue) {
        this.nullFlavorValue = nullFlavorValue;
    }

    public String getMeddraValue() {
        return meddraValue;
    }

    public void setMeddraValue(String meddraValue) {
        this.meddraValue = meddraValue;
    }

    public void setProcessedValueMap(Map<String, String> processedValueMap) {
        this.processedValueMap = processedValueMap;
    }

    public Map<String, String> getProcessedValueMap() {
        return processedValueMap;
    }

    public boolean getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(boolean display) {
        isDisplay = display;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String fetchOneSourceValue() {
        if(MapUtils.isEmpty(sourceValueMap)) {
            return null;
        }
        return sourceValueMap.values().iterator().next();
    }

    public String fetchOneSourceKey() {
        if(MapUtils.isEmpty(sourceValueMap)) {
            return null;
        }
        return sourceValueMap.keySet().iterator().next();
    }

    public String fetchOneProcessKey() {
        if(MapUtils.isEmpty(processedValueMap)) {
            return null;
        }
        return processedValueMap.keySet().iterator().next();
    }

    public String getSourceValue() {
        if (StringUtils.isNotEmpty(sourceValue)) {
            return sourceValue;
        } else {
            return getMapValue(sourceValueMap);
        }
    }

    public void setSourceValue(String sourceValue) {
        this.sourceValue = sourceValue;
    }

    private String getMapValue(Map<String,String> resultMap) {
        if(MapUtils.isEmpty(resultMap)) {
            return null;
        } else {
            String split = r3MappingDTO != null ? r3MappingDTO.getSplit():",";
            String dValue = resultMap.values().stream().filter(StringUtils::isNotBlank).reduce((r, n) -> r + split+ n).orElse(null);
            return dValue;
        }
    }

    public String getValue() {
        if(StringUtils.isNotEmpty(value)) {
            return value;
        } else {
            return getMapValue(processedValueMap);
        }
    }


    public void setValue(String value) {
        this.value = value;
    }

    public String getFinalValue() {
        return finalValue;
    }

    public void setFinalValue(String finalValue) {
        this.finalValue = finalValue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getSourceValueMap() {
        return sourceValueMap;
    }

    public void setSourceValueMap(Map<String, String> sourceValueMap) {
        this.sourceValueMap = sourceValueMap;
    }

    public R3PreviewItemDTO(R3MappingDTO r3MappingDTO) {
        this.id= UUIDUtils.getUuid();
        this.name = r3MappingDTO.getTarget();
    }

    public R3PreviewItemDTO(String name,String value) {
        this.name=name;
        this.value=value;
        isDisplay=false;
    }

    public R3PreviewItemDTO() {

    }

    public void mergeItem(R3PreviewItemDTO previewItemDTO) {
        sourceValueMap.putAll(previewItemDTO.getSourceValueMap());
        processedValueMap.putAll(previewItemDTO.getProcessedValueMap());
    }
    public void putSourceValue(String path, String value) {
        if(sourceValueMap == null) {
            sourceValueMap = new LinkedHashMap<>();
        }
        sourceValueMap.put(path,value);
    }

    public void putProcessedSourceValue(String path, String value) {
        if(processedValueMap == null) {
            processedValueMap = new LinkedHashMap<>();
        }
        processedValueMap.put(path,value);
    }

    public String getMeddraVersion() {
        return meddraVersion;
    }

    public void setMeddraVersion(String meddraVersion) {
        this.meddraVersion = meddraVersion;
    }

    public MeddraFieldInfoDto getMeddrafidldinfoDto() {
        return meddrafidldinfoDto;
    }

    public void setMeddrafidldinfoDto(MeddraFieldInfoDto meddrafidldinfoDto) {
        this.meddrafidldinfoDto = meddrafidldinfoDto;
    }

    public MeddraFieldInfoDto getFinalMeddrafidldinfoDto() {
        return finalMeddrafidldinfoDto;
    }

    public void setFinalMeddrafidldinfoDto(MeddraFieldInfoDto finalMeddrafidldinfoDto) {
        this.finalMeddrafidldinfoDto = finalMeddrafidldinfoDto;
    }
}
