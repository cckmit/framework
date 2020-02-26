package org.mickey.framework.e2b.service.e2b.helper;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.util.CollectionConverter;
import org.mickey.framework.e2b.dto.config.EstriConfigDto;

import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 2020-02-24
 */
@Slf4j
public class E2BR3EstriConfigHelper {
    public static Map<String, EstriConfigDto> getElementInfoKeyMap(List<EstriConfigDto> estrList) {
        Map<String, EstriConfigDto> estriConfigMap = CollectionConverter.listToMap(estrList, new String[]{"elementNumber", "code"});
        return estriConfigMap;
    }

    public static Map<String, EstriConfigDto> getUniqueCodeKeyMap(List<EstriConfigDto> estrList) {
        Map<String, EstriConfigDto> estriConfigMap = CollectionConverter.listToMap(estrList, new String[] {"elementNumber","itemUniqueCode"});
        return estriConfigMap;
    }

    public static Map<String, EstriConfigDto> getOIDMapping(List<EstriConfigDto> estrList) {
        Map<String, EstriConfigDto> estriConfigMap = CollectionConverter.listToMap(estrList, new String[] {"elementNumber","canonicalUri"});
        return estriConfigMap;
    }
}
