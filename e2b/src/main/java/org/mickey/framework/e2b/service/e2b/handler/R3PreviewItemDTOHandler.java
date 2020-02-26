package org.mickey.framework.e2b.service.e2b.handler;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.e2b.service.e2b.preview3.R3PreviewItemDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * description
 *
 * @author mickey
 * 2020-02-22
 */
@Slf4j
public class R3PreviewItemDTOHandler {
    private List<R3PreviewItemDTO> needMeddra = new ArrayList<>();
    private List<R3PreviewItemDTO> needYoudao = new ArrayList<>();
    private Set<String> eventIdList = new HashSet<>();
    private Set<String> drugIdList = new HashSet<>();

    public void addReactionId(String reactionId) {
        eventIdList.add(reactionId);
    }

    public void addDrugId(String drugId) {
        drugIdList.add(drugId);
    }

    public Set<String> getFileEventIdSet() {
        return eventIdList;
    }

    public Set<String> getFileDrugIdSet() {
        return drugIdList;
    }

    public void addMeddra(R3PreviewItemDTO dto) {
        needMeddra.add(dto);
    }

    public void addYoudao(R3PreviewItemDTO dto) {
        needYoudao.add(dto);
    }

    public List<R3PreviewItemDTO> getNeedMeddra() {
        return needMeddra;
    }

    public List<R3PreviewItemDTO> getNeedYoudao() {
        return needYoudao;
    }
}
