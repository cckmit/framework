package org.mickey.framework.e2b.dto.report;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.e2b.entity.Causality;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 2020-02-21
 */
@Slf4j
@Data
public class CausalityDto extends Causality implements IcsrDtoInterface {
    private Map<String, NullFlavorInfoDto> nullFlavorMap;
    private List<CausalityItemDto> causalityItemDtoList;

    /**
     * E2B 带入的fileDrugID 和 fileEventID
     */
    private String fileDrugId;
    private String fileEventId;


    public void addCausalityItemDto(CausalityItemDto dto) {
        if (causalityItemDtoList == null) {
            causalityItemDtoList = new ArrayList<>();
        }
        causalityItemDtoList.add(dto);
    }

    @Override
    public String getCreateBy() {
        return null;
    }

    @Override
    public void setCreateBy(String createBy) {

    }

    @Override
    public String getUpdateBy() {
        return null;
    }

    @Override
    public void setUpdateBy(String updateBy) {

    }

    @Override
    public Date getUpdateTime() {
        return null;
    }

    @Override
    public void setUpdateTime(Date updateTime) {

    }

    @Override
    public Map<String, MeddraFieldInfoDto> getMeddraMap() {
        return null;
    }

    @Override
    public void setMeddraMap(Map<String, MeddraFieldInfoDto> meddraMap) {

    }
}
