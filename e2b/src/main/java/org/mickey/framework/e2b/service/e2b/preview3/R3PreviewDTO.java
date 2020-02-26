package org.mickey.framework.e2b.service.e2b.preview3;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.util.MapUtils;
import org.mickey.framework.e2b.constant.E2BImportConfigConstant;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 2020-02-24
 */
@Slf4j
@Data
public class R3PreviewDTO {
    /**
     * Drug, ReportValue, Reaction
     */
    String category;
    /**
     * Default NULL, 表示更多细的分类
     */
    String subCategory;

    /**
     *
     */
    String subElementNumber;
    Map<String, R3PreviewItemDTO> previewItemDTOMap;
    Map<String, List<R3PreviewDTO>> subR3PreviewDTOMap;
    private String itemId;

    public void put(String key, R3PreviewItemDTO item) {
        if (previewItemDTOMap == null) {
            previewItemDTOMap = new LinkedHashMap<>();
        }
        previewItemDTOMap.put(key, item);
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String fetchFileDrugId() {
        if (MapUtils.isEmpty(previewItemDTOMap)) {
            return null;
        }
        R3PreviewItemDTO item = previewItemDTOMap.get(E2BImportConfigConstant.TARGET_FILE_DRUG_ID_KEY);
        if (item == null) {
            return null;
        }
        return item.getValue();
    }

    public String fetchFileEventId() {
        if (MapUtils.isEmpty(previewItemDTOMap)) {
            return null;
        }
        R3PreviewItemDTO item = previewItemDTOMap.get(E2BImportConfigConstant.TARGET_FILE_EVENT_ID_KEY);
        if (item == null) {
            return null;
        }
        return item.getValue();
    }

    public void putSub(String key, List<R3PreviewDTO> list) {
        if (subR3PreviewDTOMap == null) {
            subR3PreviewDTOMap = new LinkedHashMap<>();
        }
        subR3PreviewDTOMap.put(key, list);
    }

    public Map<String, List<R3PreviewDTO>> getSubR3PreviewDTOMap() {
        return subR3PreviewDTOMap;
    }

    public void setSubR3PreviewDTOMap(Map<String, List<R3PreviewDTO>> subR3PreviewDTOMap) {
        this.subR3PreviewDTOMap = subR3PreviewDTOMap;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public Map<String, R3PreviewItemDTO> getPreviewItemDTOMap() {
        return previewItemDTOMap;
    }

    public void setPreviewItemDTOMap(Map<String, R3PreviewItemDTO> previewItemDTOMap) {
        this.previewItemDTOMap = previewItemDTOMap;
    }

    public String fetchPreviewItemDTOValue(String key) {
        R3PreviewItemDTO item = previewItemDTOMap.get(key);
        if (item == null) {
            return null;
        } else {
            return item.getValue();
        }
    }

    public R3PreviewItemDTO fetchPreviewItemDTO(String key) {
        return previewItemDTOMap.get(key);
    }

    public R3PreviewItemDTO removePreviewItemDTO(String key) {
        return previewItemDTOMap.remove(key);
    }

    public String getSubElementNumber() {
        return subElementNumber;
    }

    public void setSubElementNumber(String subElementNumber) {
        this.subElementNumber = subElementNumber;
    }
}
