package org.mickey.framework.e2b.dto.e2b;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * description
 *
 * @author mickey
 * 2020-02-22
 */
@Slf4j
@Data
public class E2BR3SingleExportDto {
    /**
     * 该节点下的单记录对象
     * C.1.2 -> name
     */
    private Map<String,String> valueMap = new LinkedHashMap<>();
    private Map<String,String> nullFlavorValueMap = new LinkedHashMap<>();
    /**
     * 该节点下的多记录对象例如
     *  附件
     *  C.1.6.1.r  ->  附件信息
     *  C.4.r -> 另外一种附件的信息
     */
    private Map<String, List<E2BR3SingleExportDto>> childrenMap;

    public void putChildren(String key,List<E2BR3SingleExportDto> list) {
        if(childrenMap == null) {
            childrenMap = new HashMap<>();
        }
        childrenMap.put(key,list);
    }

    public void addChild(String key,E2BR3SingleExportDto dto) {
        if(childrenMap == null) {
            childrenMap = new HashMap<>();
        }

        List<E2BR3SingleExportDto> list = childrenMap.get(key);

        if(list == null) {
            list =new ArrayList<>();
            childrenMap.put(key,list);
        }

        list.add(dto);
    }

    public void putValue(String key,String value) {
        valueMap.put(key,value);
    }
}
