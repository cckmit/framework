package org.mickey.framework.e2b.service.e2b.r3convertor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.mickey.framework.e2b.dto.e2b.E2BR3SingleExportDto;
import org.mickey.framework.e2b.dto.report.IcsrEntityDto;
import org.mickey.framework.e2b.service.e2b.parameter.R3CommonDataParameter;
import org.mickey.framework.e2b.service.e2b.preview3.R3PreviewDTO;
import org.mickey.framework.e2b.util.SpringContextUtils;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 2020-02-24
 */
@Slf4j
@Component
public class ConvertorR3Factory {
    final static Map<String,String> serviceMap;
    final static Map<String,String> specialMap;
    static {
        serviceMap = new LinkedHashMap<>();
        specialMap=new LinkedHashMap<>();

        //注意这里是有顺序要求的reaction必须在drug的前面，因为需要设置causality
        //reportValue 需要在最后面

        serviceMap.put("Reaction","ReactionR3Convertor");
        serviceMap.put("DeathCause","DeathCauseR3Convertor");
        serviceMap.put("ReportDiagnose","ReportDiagnoseR3Convertor");
        serviceMap.put("Labdata","LabdataR3Convertor");
        serviceMap.put("Othermedicalhistory","OtherMedicalHistoryR3Convertor");
        serviceMap.put("RelatedToCode","RelatedToCodeR3Convertor");
        serviceMap.put("Reporter","ReporterR3Convertor");
        serviceMap.put("CaseSummary","CaseSummaryR3Convertor");
        serviceMap.put("Research","ResearchR3SpecialConvertor");
        serviceMap.put("Attachment","AttachmentR3SpecialConvertor");
        serviceMap.put("Drug","DrugR3Convertor");
        serviceMap.put("patient","PatientR3Convertor");
        serviceMap.put("ReportValue","ReportBaseR3Convertor");

        //specialMap.put("Research","ResearchR3SpecialConvertor");
        specialMap.put("Attachment","AttachmentR3SpecialConvertor");
        specialMap.put("ReportValue","ReportBaseR3Convertor");
    }

    public static Map<String,String> getServiceMap() {
        return serviceMap;
    }

    public void build(String category, IcsrEntityDto reportEntityDto, List<R3PreviewDTO> previewDTOS) {
        ConvertorR3 service = getConvertor(category);
        service.build(category,reportEntityDto,previewDTOS);
    }

    public void updateOriginalSource(String category, IcsrEntityDto reportEntityDto, List<R3PreviewDTO> previewDTOS) {
        ConvertorR3 service = getConvertor(category);
        service.updateOriginalSource(category,reportEntityDto,previewDTOS);
    }

    public List<R3PreviewDTO> preview(String flag, String category, int nodeIndex, Document document, R3CommonDataParameter parameter) {
        ConvertorR3 service = getConvertor(category);
        return service.previewRecords(flag,category,nodeIndex,document,parameter);
    }


    public Map<String,List<E2BR3SingleExportDto>> export(String category, IcsrEntityDto reportEntityDto, R3CommonDataParameter parameter) {
        ConvertorR3 service = getConvertor(category);
        return service.exportTemplateObject(category,reportEntityDto,parameter);
    }

    private ConvertorR3 getConvertor(String category) {
        return getConvertor(category,null);
    }

    private ConvertorR3 getConvertor(String category, String version) {
        String serviceName = serviceMap.get(category);
        if(StringUtils.equalsIgnoreCase("r3",version)) {
            serviceName= specialMap.get(category);
        }
        ConvertorR3 service = (ConvertorR3) SpringContextUtils.getBeanById(serviceName);
        return service;
    }

    public static  Map<String,String> getSpecialServiceMap() {
        return specialMap;
    }

}
