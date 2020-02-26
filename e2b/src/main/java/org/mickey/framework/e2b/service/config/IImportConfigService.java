package org.mickey.framework.e2b.service.config;

import org.mickey.framework.e2b.dto.config.ReportConfigDto;

import java.util.Optional;

/**
 * description
 *
 * @author mickey
 * 2020-02-20
 */
public interface IImportConfigService {
    Optional<ReportConfigDto> queryByUniqueCode(String uniqueCode);
}
