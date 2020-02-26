package org.mickey.framework.e2b.service.config.impl;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.util.CollectionConverter;
import org.mickey.framework.e2b.dto.config.HL7ConfigDto;
import org.mickey.framework.e2b.mapper.config.R3HL7ConfigMapper;
import org.mickey.framework.e2b.service.config.IE2bR3hl7ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description
 *
 * @author mickey
 * 2020-02-20
 */
@Slf4j
@Service
public class E2bR3hl7ConfigServiceImpl implements IE2bR3hl7ConfigService {

    @Autowired
    private R3HL7ConfigMapper mapper;

    @Override
    public List<HL7ConfigDto> query() {
        return CollectionConverter.listToLists(mapper.findAll(), HL7ConfigDto.class);
    }
}
