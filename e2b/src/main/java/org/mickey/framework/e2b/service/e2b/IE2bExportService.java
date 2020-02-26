package org.mickey.framework.e2b.service.e2b;

import org.mickey.framework.e2b.dto.e2b.E2BR3ExportQueryDto;
import org.mickey.framework.e2b.dto.e2b.E2bR3CompanyInfoDto;

/**
 * description
 *
 * @author mickey
 * 2020-02-22
 */
public interface IE2bExportService {

    String exportR3(E2BR3ExportQueryDto exportQueryDto);

    String exportR3(E2BR3ExportQueryDto exportQueryDto, boolean isEU);

    byte[] exportR3File(E2BR3ExportQueryDto exportQueryDto);

    byte[] exportR3File(E2BR3ExportQueryDto exportQueryDto, boolean isEU);

    byte[] exportR3File(E2BR3ExportQueryDto exportQueryDto, E2bR3CompanyInfoDto companyInfo);

    byte[] exportR3File(E2BR3ExportQueryDto exportQueryDto, E2bR3CompanyInfoDto companyInfoDto, boolean isEU);
}
