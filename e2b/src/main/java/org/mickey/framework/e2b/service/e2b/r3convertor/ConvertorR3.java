package org.mickey.framework.e2b.service.e2b.r3convertor;

import org.dom4j.Document;
import org.mickey.framework.e2b.dto.e2b.E2BR3SingleExportDto;
import org.mickey.framework.e2b.dto.report.IcsrEntityDto;
import org.mickey.framework.e2b.service.e2b.parameter.R3CommonDataParameter;
import org.mickey.framework.e2b.service.e2b.preview3.R3PreviewDTO;

import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 2020-02-24
 */
public interface ConvertorR3 {
    List<R3PreviewDTO> previewRecords(String flag, String category, int nodeIndex, Document document, R3CommonDataParameter parameter);

    void build(String category, IcsrEntityDto reportEntityDto, List<R3PreviewDTO> list);

    void updateOriginalSource(String category, IcsrEntityDto reportEntityDto, List<R3PreviewDTO> list);

    Map<String, List<E2BR3SingleExportDto>> exportTemplateObject(String category, IcsrEntityDto reportEntityDto, R3CommonDataParameter parameter);
}
