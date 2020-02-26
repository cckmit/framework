package org.mickey.framework.e2b.service.config.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mickey.framework.common.util.CollectionConverter;
import org.mickey.framework.e2b.dto.config.EstriConfigDto;
import org.mickey.framework.e2b.dto.config.EstriItemConfigDto;
import org.mickey.framework.e2b.mapper.config.R3EstriItemConfigMapper;
import org.mickey.framework.e2b.mapper.config.R3M2EstriConfigMapper;
import org.mickey.framework.e2b.service.config.IE2bR3EstriConfigService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 2020-02-20
 */
@Slf4j
@Service
public class E2bR3EstriConfigServiceImpl implements IE2bR3EstriConfigService {

    @Autowired
    private R3M2EstriConfigMapper m2EstriConfigMapper;
    @Autowired
    private R3EstriItemConfigMapper estriItemConfigMapper;

    @Override
    public List<EstriConfigDto> query() {
        List<EstriConfigDto> e2bR3M2estriConfigList = CollectionConverter.listToLists(m2EstriConfigMapper.findAll(), EstriConfigDto.class);
        List<EstriItemConfigDto> estriItemsConfigs = CollectionConverter.listToLists(estriItemConfigMapper.findAll(), EstriItemConfigDto.class);

        Map<String, EstriConfigDto> e2bR3M2estriConfigMap = CollectionConverter.listToMap(e2bR3M2estriConfigList, key -> key.getId(), value -> value);
        List<EstriConfigDto> estriConfigDtos = new ArrayList<>();

        for (EstriItemConfigDto estriItemsConfig : estriItemsConfigs) {
            EstriConfigDto estriConfigDto = new EstriConfigDto();
            estriConfigDto.setElementNumber(StringUtils.trim(estriItemsConfig.getElementNumber()));
            estriConfigDto.setItemUniqueCode(StringUtils.trim(estriItemsConfig.getItemUniqueCode()));
            EstriConfigDto e2bR3EstriItemsConfig = e2bR3M2estriConfigMap.get(estriItemsConfig.getEstriId());
            if (e2bR3EstriItemsConfig != null) {
                BeanUtils.copyProperties(e2bR3EstriItemsConfig, estriConfigDto);
            }
            estriConfigDto.setCode(StringUtils.trim(StringUtils.lowerCase(estriConfigDto.getCode())));
            estriConfigDto.setCanonicalUri(StringUtils.lowerCase(estriConfigDto.getCanonicalUri()));
            estriConfigDtos.add(estriConfigDto);
        }
        return estriConfigDtos;
    }
}
