package org.mickey.framework.e2b.service.e2b;

import org.mickey.framework.e2b.dto.e2b.E2BR3SingleExportDto;
import org.mickey.framework.e2b.dto.report.IcsrEntityDto;
import org.mickey.framework.e2b.service.e2b.parameter.R3CommonDataParameter;

import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 2020-02-22
 */
public interface E2BR3ConvertService {
    R3CommonDataParameter generateR3CommonDataParameter();

    Map<String, List<E2BR3SingleExportDto>> exportAllTemplateObject(R3CommonDataParameter r3Parameter, IcsrEntityDto entityDto);
}
