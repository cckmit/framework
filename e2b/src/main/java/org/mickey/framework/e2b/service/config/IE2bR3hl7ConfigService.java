package org.mickey.framework.e2b.service.config;

import org.mickey.framework.e2b.dto.config.HL7ConfigDto;

import java.util.List;

/**
 * description
 *
 * @author mickey
 * 2020-02-20
 */
public interface IE2bR3hl7ConfigService {
    List<HL7ConfigDto> query();
}
