package org.mickey.framework.e2b.dto.e2b;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.e2b.dto.report.IcsrEntityDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 2020-02-23
 */
@Slf4j
@Data
public class E2BR3ExportDto {
    private int isDebug=1;
    private List<Map<String, List<E2BR3SingleExportDto>>> dataList = new ArrayList<>();
    private E2bR3CompanyInfoDto company;
    private E2bR3SenderOperatingDto pvSender;
    private List<IcsrEntityDto> icsrEntityDtoList = new ArrayList<>();

    public void addIcsrEntityDto(IcsrEntityDto dto) {
        icsrEntityDtoList.add(dto);
    }

    public void addData(Map<String, List<E2BR3SingleExportDto>> data) {
        dataList.add(data);
    }
}
