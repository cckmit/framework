package org.mickey.framework.e2b.service.e2b.r3convertor.special;

import org.mickey.framework.e2b.constant.ItemUniqueCodeProvider;
import org.mickey.framework.e2b.dto.report.AttachmentR3Dto;
import org.mickey.framework.e2b.dto.report.IcsrEntityDto;
import org.mickey.framework.e2b.dto.config.ReportConfigDto;
import org.mickey.framework.e2b.service.e2b.preview3.R3PreviewDTO;
import org.mickey.framework.e2b.service.e2b.r3convertor.BaseR3Convertor;
import org.mickey.framework.e2b.service.e2b.r3convertor.ConvertorR3;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
@Component(value="AttachmentR3SpecialConvertor")
public class AttachmentR3SpecialConvertor extends BaseR3Convertor implements ConvertorR3 {
    static Map<String,String> elementNumber2Type;
    static {
        elementNumber2Type = new HashMap<>();
        elementNumber2Type.put("C.1.6.1.r", ItemUniqueCodeProvider.RAWDATA_FILECLASSIFY_SourceData);
        elementNumber2Type.put("C.4.r",ItemUniqueCodeProvider.RAWDATA_FILECLASSIFY_Document);
    }
    @Override
    protected String getMappingDTO(ReportConfigDto reportImportconfigWithBLOBs) {
        return reportImportconfigWithBLOBs.getAttachment();
    }

    @Override
    protected Object getResultInstance(R3PreviewDTO previewDTO) throws IllegalAccessException, InstantiationException {
        String subElementNumber = previewDTO.getSubElementNumber();
        AttachmentR3Dto object = new AttachmentR3Dto();
        object.setId(UUIDUtils.getUuid());
        object.setType(elementNumber2Type.get(subElementNumber));
        return object;
    }

    @Override
    protected void fill(String category, IcsrEntityDto reportEntityDto, Object obj) {
        AttachmentR3Dto dto = (AttachmentR3Dto) obj;
        reportEntityDto.addAttachmentR3DTO(dto);
    }

    @Override
    protected boolean isMappingMatch(Object object, String mappingKey) {
        AttachmentR3Dto attachmentR3DTO =(AttachmentR3Dto) object;
        return StringUtils.equalsIgnoreCase(attachmentR3DTO.getType(),elementNumber2Type.get(mappingKey));
    }

    @Override
    protected List getTaimeiPVObjectList(IcsrEntityDto reportEntityDto) {
        return reportEntityDto.getAttachmentR3DtoList();
    }
}
