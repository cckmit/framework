package org.mickey.framework.e2b.service.e2b.r3convertor.special;


import org.mickey.framework.e2b.dto.report.IcsrEntityDto;
import org.mickey.framework.e2b.dto.report.ResearchR3Dto;
import org.mickey.framework.e2b.dto.config.ReportConfigDto;
import org.mickey.framework.e2b.service.e2b.preview3.R3PreviewDTO;
import org.mickey.framework.e2b.service.e2b.r3convertor.BaseR3Convertor;
import org.mickey.framework.e2b.service.e2b.r3convertor.ConvertorR3;
import org.mickey.framework.common.util.UUIDUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "ResearchR3SpecialConvertor")
public class ResearchR3SpecialConvertor extends BaseR3Convertor implements ConvertorR3 {

    @Override
    protected Object getResultInstance(R3PreviewDTO previewDTO) throws IllegalAccessException, InstantiationException {
        ResearchR3Dto researchR3DTO = new ResearchR3Dto();
        researchR3DTO.setId(UUIDUtils.getUuid());
        return researchR3DTO;
    }

    @Override
    protected void fill(String category, IcsrEntityDto reportEntityDto, Object obj) {
        ResearchR3Dto dto = (ResearchR3Dto) obj;
        reportEntityDto.addResearchR3DTO(dto);
    }

    @Override
    protected boolean isMappingMatch(Object object, String mappingKey) {
        return true;
    }

    @Override
    protected List getTaimeiPVObjectList(IcsrEntityDto reportEntityDto) {
        return reportEntityDto.getResearchR3DtoList();
    }

    @Override
    protected String getMappingDTO(ReportConfigDto reportImportconfigWithBLOBs) {
        return reportImportconfigWithBLOBs.getResearch();
    }
}