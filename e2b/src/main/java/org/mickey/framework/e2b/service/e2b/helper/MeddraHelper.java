package org.mickey.framework.e2b.service.e2b.helper;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.util.StringUtil;
import org.mickey.framework.e2b.dto.report.IcsrDtoInterface;
import org.mickey.framework.e2b.dto.report.MeddraFieldInfoDto;

import java.util.HashMap;
import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 2020-02-24
 */
@Slf4j
@Data
public class MeddraHelper {
    public static void addMeddra(IcsrDtoInterface mi, String name, MeddraFieldInfoDto meddrafidldinfoDto){
        if(meddrafidldinfoDto == null) {
            return;
        }
        Map<String, MeddraFieldInfoDto> meddraMap = mi.getMeddraMap();
        if(meddraMap == null) {
            meddraMap = new HashMap<>();
        }
        mi.setMeddraMap(meddraMap);

        meddraMap.put(name,meddrafidldinfoDto);
    }

    public static String ptName(IcsrDtoInterface dto, String ptField) {
        if (dto == null || dto.getMeddraMap() == null) {
            return "";
        }
        MeddraFieldInfoDto orDefault = dto.getMeddraMap().getOrDefault(ptField, new MeddraFieldInfoDto());
        return StringUtil.valueOf(orDefault.getPtName());
    }
}
