package org.mickey.framework.e2b.service.e2b.preview3;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * description
 *
 * @author mickey
 * 2020-02-22
 */
@Slf4j
public class R3MappingDTO {

    private String mappingName;
    /**
     * PV Name
     */
    private String target;
    /**
     * mostly this is just elementNumber, sometime it could be String with several elementNumber combined with comma
     */
    private String elementNumbers;
    private String meddraElementNumber;
    private String meddraVersionElementNumber;
    private String meddraType;
    /**
     * split
     */
    private String split;
    /**
     * 是否在预处理界面展示
     */
    private boolean display=true;
    /**
     * null flavor elemnet number
     */
    private String nullflavorElementNumbers;
    private boolean notExport;


    public void setNotExport(boolean notExport) {
        this.notExport = notExport;
    }

    public boolean isNotExport() {
        return notExport;
    }

    public String[] fetchNullflavorElementNumberArray() {
        if(StringUtils.isNotBlank(nullflavorElementNumbers)) {
            return nullflavorElementNumbers.split(",");
        }
        return null;
    }

    public String getNullflavorElementNumbers() {
        return nullflavorElementNumbers;
    }

    public void setNullflavorElementNumbers(String nullflavorElementNumbers) {
        this.nullflavorElementNumbers = nullflavorElementNumbers;
    }

    public boolean getDisplay() {
        return display;
    }

    public void setDisplay(boolean isDisplay) {
        this.display = isDisplay;
    }

    public String getMappingName() {
        return mappingName;
    }

    public void setMappingName(String mappingName) {
        this.mappingName = mappingName;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getMeddraType() {
        return meddraType;
    }

    public void setMeddraType(String meddraType) {
        this.meddraType = meddraType;
    }

    public String getSplit() {
        return split;
    }

    public void setSplit(String split) {
        this.split = split;
    }

    public boolean needCallMeddra() {
        return StringUtils.isNotEmpty(meddraType) && (meddraType.contains("name") || meddraType.contains("code"));
    }

    public String getElementNumbers() {
        return elementNumbers;
    }

    public String[] getElementNumberArray() {
        return StringUtils.split(elementNumbers,",");
    }

    public void setElementNumbers(String elementNumbers) {
        this.elementNumbers = elementNumbers;
    }
    public String getMeddraElementNumber() {
        return meddraElementNumber;
    }

    public void setMeddraElementNumber(String meddraElementNumber) {
        this.meddraElementNumber = meddraElementNumber;
    }

    public String getMeddraVersionElementNumber() {
        return meddraVersionElementNumber;
    }

    public void setMeddraVersionElementNumber(String meddraVersionElementNumber) {
        this.meddraVersionElementNumber = meddraVersionElementNumber;
    }

    public String getFirstElementNumber() {
        if(elementNumbers != null) {
            return StringUtils.substringBefore(elementNumbers, ",");
        }
        return null;
    }

    public static void main(String[] args) {
        String mappingMapString = "{\n" +
                "\t\"G.k\":[{\n" +
                "\t\t\"elementNumbers\": \"G.k.fileDrugId\",\n" +
                "\t\t\"Target\": \"fileDrugId\"\n" +
                "\t},{\n" +
                "\t\t\"elementNumbers\": \"G.k.1\",\n" +
                "\t\t\"Target\": \"drugtype\"\n" +
                "\t},{\n" +
                "\t\t\"elementNumbers\": \"G.k.1\",\n" +
                "\t\t\"notExport\":true,\n" +
                "\t\t\"Target\": \"medicineinteractions\"\n" +
                "\t},{\n" +
                "\t\t\"elementNumbers\": \"G.k.1\",\n" +
                "\t\t\"notExport\":true,\n" +
                "\t\t\"Target\": \"untreated\"\n" +
                "\t},{\n" +
                "\t\t\"elementNumbers\": \"G.k.2.2\",\n" +
                "\t\t\"Target\": \"genericname\"\n" +
                "\t},{\n" +
                "\t\t\"elementNumbers\": \"G.k.2.4\",\n" +
                "\t\t\"Target\": \"obtainedcountry\"\n" +
                "\t},{\n" +
                "\t\t\"elementNumbers\": \"G.k.2.5\",\n" +
                "\t\t\"Target\": \"investigationalblinded\"\n" +
                "\t},{\n" +
                "\t\t\"elementNumbers\": \"G.k.3.1\",\n" +
                "\t\t\"Target\": \"authorisationnumber\"\n" +
                "\t},{\n" +
                "\t\t\"elementNumbers\": \"G.k.3.2\",\n" +
                "\t\t\"Target\": \"authorisationcountry\"\n" +
                "\t},{\n" +
                "\t\t\"elementNumbers\": \"G.k.5a\",\n" +
                "\t\t\"Target\": \"cumulativedose\"\n" +
                "\t},{\n" +
                "\t\t\"elementNumbers\": \"G.k.5b\",\n" +
                "\t\t\"Target\": \"cumulativedoseunit\"\n" +
                "\t},{\n" +
                "\t\t\"elementNumbers\": \"G.k.6a\",\n" +
                "\t\t\"Target\": \"exposuregestationperiod\"\n" +
                "\t},{\n" +
                "\t\t\"elementNumbers\": \"G.k.6b\",\n" +
                "\t\t\"Target\": \"exposuregestationperiodunit\"\n" +
                "\t},{\n" +
                "\t\t\"elementNumbers\": \"G.k.8\",\n" +
                "\t\t\"Target\": \"actiontakenforsuspectdrug\"\n" +
                "\t},{\n" +
                "\t\t\"elementNumbers\": \"G.k.10.r\",\n" +
                "\t\t\"Target\": \"drugadditionalinfo\"\n" +
                "\t},{\n" +
                "\t\t\"elementNumbers\": \"G.k.11\",\n" +
                "\t\t\"Target\": \"drugotherinfo\"\n" +
                "\t}]\n" +
                "}";
        LinkedHashMap<String, List<R3MappingDTO>> result = JSON.parseObject(mappingMapString, new TypeReference<LinkedHashMap<String, List<R3MappingDTO>>>() {
        }.getType());
        System.out.println(result);
    }
}
