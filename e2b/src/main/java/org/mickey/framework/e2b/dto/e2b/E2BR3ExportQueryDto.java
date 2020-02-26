package org.mickey.framework.e2b.dto.e2b;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.e2b.constant.ReportLanguageEnum;
import org.mickey.framework.e2b.dto.report.IcsrEntityDto;

import java.util.ArrayList;
import java.util.List;

/**
 * description
 *
 * @author mickey
 * 2020-02-21
 */
@Slf4j
@Data
public class E2BR3ExportQueryDto {
    @ApiModelProperty("报告id")
    private List<String> reportIds;
    @ApiModelProperty("发送者id")
    private String senderId;
    @ApiModelProperty("接收方id")
    private String receiveMessageId;
    @ApiModelProperty("需要导出的附件信息")
    private List<E2BR3ExportFileDto> e2BR3ExportFileDtoList;
    @ApiModelProperty("中文：cn，英文：en")
    private String language = ReportLanguageEnum.CN.getLanguage();

    @ApiModelProperty("需要导出的报告整体数据")
    private List<IcsrEntityDto> entityDtoList;

    private E2bR3CompanyInfoDto r3CompanyInfoDto;

    public void addIcsrEntityDto(IcsrEntityDto entityDto) {
        if (entityDtoList == null) {
            entityDtoList = new ArrayList<>();
        }
        entityDtoList.add(entityDto);
    }
}
