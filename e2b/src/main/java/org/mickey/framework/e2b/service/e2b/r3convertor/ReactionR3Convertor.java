package org.mickey.framework.e2b.service.e2b.r3convertor;

import org.mickey.framework.e2b.constant.E2BImportConfigConstant;
import org.mickey.framework.e2b.constant.ItemUniqueCodeProvider;
import org.mickey.framework.e2b.dto.config.ReportConfigDto;
import org.mickey.framework.e2b.dto.e2b.E2BR3SingleExportDto;
import org.mickey.framework.e2b.dto.report.AdverseEventDto;
import org.mickey.framework.e2b.dto.report.IcsrEntityDto;
import org.mickey.framework.e2b.service.e2b.helper.ReactionExportR3Helper;
import org.mickey.framework.e2b.service.e2b.parameter.R3CommonDataParameter;
import org.mickey.framework.e2b.service.e2b.preview3.R3PreviewDTO;
import org.mickey.framework.e2b.service.e2b.preview3.R3PreviewItemDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.mickey.framework.common.SystemContext;
import org.mickey.framework.common.util.UUIDUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Component(value="ReactionR3Convertor")
public class ReactionR3Convertor extends BaseR3Convertor implements ConvertorR3{

    public static Map<String,String> SeverityExportMap = new HashMap<>();
    static {
        SeverityExportMap.put(ItemUniqueCodeProvider.Severity_Death,"E.i.3.2a");
        SeverityExportMap.put(ItemUniqueCodeProvider.Severity_LifeThreatening,"E.i.3.2b");
        SeverityExportMap.put(ItemUniqueCodeProvider.Severity_Hospitalisation,"E.i.3.2c");
        SeverityExportMap.put(ItemUniqueCodeProvider.Severity_Disabling,"E.i.3.2d");
        SeverityExportMap.put(ItemUniqueCodeProvider.Severity_CongenitalAnomaly,"E.i.3.2e");
        SeverityExportMap.put(ItemUniqueCodeProvider.Severity_OtherMedically,"E.i.3.2f");
    }

    public static final String COLUMN_NAME_STANDARDOFSERIOUSNESS = "standardofseriousness";
    public static String[] tandardofseriousnessKey = {"a", "b", "c", "d", "e", "f"};

    @Override
    protected void customExport(E2BR3SingleExportDto dto, Object obj) {
        Map<String, String> valueMap = dto.getValueMap();
        AdverseEventDto tmAdverseeventDto = (AdverseEventDto)obj;
        String seriousness = tmAdverseeventDto.getStandardofSeriousness();
        if(StringUtils.isNotBlank(seriousness)) {
            String[] pvItems=  StringUtils.split(seriousness,",");
            for(String pvItem:pvItems) {
                String r3Value = SeverityExportMap.get(pvItem);
                setTemplateValue(valueMap,r3Value, BooleanUtils.toStringTrueFalse(true));
            }
        }
        ReactionExportR3Helper.singleCustomProcess(dto);

    }

    @Override
    protected String getMappingDTO(ReportConfigDto reportImportconfigWithBLOBs) {
        return reportImportconfigWithBLOBs.getAdverseEvent();
    }

    @Override
    protected void customPreview(R3PreviewDTO previewDTO, String subElementNumber, R3CommonDataParameter commonDataParameter) {
        super.customPreview(previewDTO, subElementNumber, commonDataParameter);
        seriousnessLogic(previewDTO);
    }

    private void seriousnessLogic(R3PreviewDTO previewDTO) {
        R3PreviewItemDTO seriousnessItem = new R3PreviewItemDTO();
        seriousnessItem.setName(COLUMN_NAME_STANDARDOFSERIOUSNESS);
        for (String suffix : tandardofseriousnessKey) {
            String key = COLUMN_NAME_STANDARDOFSERIOUSNESS + "_" + suffix;
            R3PreviewItemDTO item = previewDTO.fetchPreviewItemDTO(key);
            if (item == null) {
                continue;
            }
            //将对应做出来的数据全部merge到总节点下
            seriousnessItem.mergeItem(item);
            previewDTO.removePreviewItemDTO(key);
        }
        if(getValidItemDTO(seriousnessItem) != null) {
            previewDTO.put(COLUMN_NAME_STANDARDOFSERIOUSNESS, seriousnessItem);
        }
    }

    @Override
    protected Object getResultInstance(R3PreviewDTO previewDTO) throws IllegalAccessException, InstantiationException {
        AdverseEventDto dto = AdverseEventDto.class.newInstance();
        dto.setId(UUIDUtils.getUuid());
        dto.setReportId(SystemContext.get(E2BImportConfigConstant.SC_PREVIEW_REPORT_ID));
        dto.setIsDelete(false);
        return dto;
    }

    @Override
    public void fill(String category, IcsrEntityDto reportEntityDto, Object obj) {
        reportEntityDto.addAdverseEventDto((AdverseEventDto)obj);
    }

    @Override
    protected boolean isMappingMatch(Object object, String mappingKey) {
        return true;
    }

    @Override
    public List getTaimeiPVObjectList(IcsrEntityDto reportEntityDto) {
        return reportEntityDto.getAdverseEventDtoList();
    }
}
