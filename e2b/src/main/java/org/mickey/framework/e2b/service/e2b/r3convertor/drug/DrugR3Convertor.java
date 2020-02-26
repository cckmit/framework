package org.mickey.framework.e2b.service.e2b.r3convertor.drug;

import org.mickey.framework.e2b.constant.E2BImportConfigConstant;
import org.mickey.framework.e2b.constant.ItemUniqueCodeProvider;
import org.mickey.framework.e2b.dto.report.*;
import org.mickey.framework.e2b.dto.config.ReportConfigDto;
import org.mickey.framework.e2b.dto.e2b.E2BR3SingleExportDto;
import org.mickey.framework.e2b.dto.config.HL7ConfigDto;
import org.mickey.framework.e2b.service.e2b.handler.R3PreviewItemDTOHandler;
import org.mickey.framework.e2b.service.e2b.helper.DrugDoseExportR3Helper;
import org.mickey.framework.e2b.service.e2b.impl.E2BDocumentUtil;
import org.mickey.framework.e2b.service.e2b.parameter.R3CommonDataParameter;
import org.mickey.framework.e2b.service.e2b.preview3.R3MappingDTO;
import org.mickey.framework.e2b.service.e2b.preview3.R3PreviewDTO;
import org.mickey.framework.e2b.service.e2b.preview3.R3PreviewItemDTO;
import org.mickey.framework.e2b.service.e2b.r3convertor.BaseR3Convertor;
import org.mickey.framework.e2b.service.e2b.r3convertor.ConvertorR3;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Node;
import org.mickey.framework.common.SystemContext;
import org.mickey.framework.common.util.CollectionConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Component(value = "DrugR3Convertor")
public class DrugR3Convertor extends BaseR3Convertor implements ConvertorR3 {
    final static Logger logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    private static Map<String, String> causalityItemSourceMap = new HashMap<>();
    private static Map<String, String> causalityItemResultMap = new HashMap<>();

    static {
        causalityItemSourceMap.put("7a497da4-5b64-11e9-8df3-000c29ee981b", "1");
        causalityItemSourceMap.put("9a6e9534-5b64-11e9-8df3-000c29ee981b", "2");
        causalityItemSourceMap.put("9a6e9546-5b64-11e9-8df3-000c29ee981b", "3");
        causalityItemSourceMap.put("9a6e954b-5b64-11e9-8df3-000c29ee981b", "4");
        causalityItemSourceMap.put("9a6e9550-5b64-11e9-8df3-000c29ee981b", "5");
        causalityItemSourceMap.put("9a6e9554-5b64-11e9-8df3-000c29ee981b", "6");
    }

    static {
        causalityItemResultMap.put("2162f72c-c154-11e8-a962-000c29399d7c", "1");
        causalityItemResultMap.put("2162ee06-c154-11e8-a962-000c29399d7c", "2");
    }

    @Override
    protected String getMappingDTO(ReportConfigDto reportImportconfigWithBLOBs) {
        return reportImportconfigWithBLOBs.getDrug();
    }

    @Override
    protected void customExport(E2BR3SingleExportDto dto, Object obj) {
        super.customExport(dto, obj);
        DrugDto drug = (DrugDto) obj;
        String hname = drug.getHolderName();
        if (StringUtils.isNotBlank(hname)) {
            dto.putValue("G.k.3.3", hname);
        }
        DrugDoseExportR3Helper.singleCustomProcess(dto);
    }

    @Override
    protected IcsrDtoInterface buildSingleObject(R3PreviewDTO r3PreviewDTO, Object obj) throws InstantiationException, IllegalAccessException {
        DrugDto tmDrugDto = (DrugDto) super.buildSingleObject(r3PreviewDTO, obj);

        Map<String, List<R3PreviewDTO>> levelOneMap = r3PreviewDTO.getSubR3PreviewDTOMap();
        if (MapUtils.isEmpty(levelOneMap)) {
            return tmDrugDto;
        }
        for (Map.Entry<String, List<R3PreviewDTO>> singleEntry : levelOneMap.entrySet()) {
            String key = singleEntry.getKey();
            List<R3PreviewDTO> previewDTOList = singleEntry.getValue();
            for (R3PreviewDTO r3PreviewDTOSub : previewDTOList) {
                buildChildrenObject(key, tmDrugDto, r3PreviewDTOSub);
            }
        }
        return tmDrugDto;
    }

    /**
     * 追加Drug中的多记录，包括用药剂量，产品上市，用药原因
     *
     * @param key
     * @param tmDrugDto
     * @param subPreviewDTO
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private Object buildChildrenObject(String key, DrugDto tmDrugDto, R3PreviewDTO subPreviewDTO) throws IllegalAccessException, InstantiationException {
        switch (key) {
            case E2BImportConfigConstant.PSUR_DRUG_ITEM:
                ProductDrugItemDto psur = new ProductDrugItemDto();
                psur.setId(UUID.randomUUID().toString());
                psur.setIsDeleted(Boolean.FALSE);
                super.buildSingleObject(subPreviewDTO, psur);
                tmDrugDto.addPsurDrugItemDto(psur);
                break;
            case E2BImportConfigConstant.MEDICATION_REASON:
                DrugMedicationReasonDto reason = new DrugMedicationReasonDto();
                reason.setId(UUID.randomUUID().toString());
                reason.setDrugId(tmDrugDto.getId());
                reason.setIsDelete(Boolean.FALSE);
                super.buildSingleObject(subPreviewDTO, reason);
                tmDrugDto.addMedicationReasonDto(reason);
                break;
            case E2BImportConfigConstant.DRUG_DOSE:
                DrugDoseDto dose = new DrugDoseDto();
                dose.setIsDelete(Boolean.FALSE);
                dose.setId(UUID.randomUUID().toString());
                dose.setDrugId(tmDrugDto.getId());
                super.buildSingleObject(subPreviewDTO, dose);
                tmDrugDto.addDrugDoseDto(dose);
                break;
            case E2BImportConfigConstant.CAUSALITY:
                CausalityDto causalityDto = new CausalityDto();
                causalityDto.setIsDelete(Boolean.FALSE);
                causalityDto.setId(UUID.randomUUID().toString());
                causalityDto.setDrugId(tmDrugDto.getId());
                //这里不生成drugID 和 eventID 在外面统一生成
                super.buildSingleObject(subPreviewDTO, causalityDto);
                tmDrugDto.addRCausalityDto(causalityDto);

                Map<String, List<R3PreviewDTO>> subMap = subPreviewDTO.getSubR3PreviewDTOMap();
                if (MapUtils.isEmpty(subMap)) {
                    break;
                }
                String causalityItemKey = subMap.keySet().iterator().next();
                List<R3PreviewDTO> items = subMap.get(causalityItemKey);
                if (CollectionUtils.isEmpty(items)) {
                    break;
                }

                for (R3PreviewDTO item : items) {
                    CausalityItemDto tmCausalityItemDto = new CausalityItemDto();
                    tmCausalityItemDto.setId(UUID.randomUUID().toString());
                    tmCausalityItemDto.setCausalityId(causalityDto.getId());
                    tmCausalityItemDto.setIsDeleted(Boolean.FALSE);
                    super.buildSingleObject(item, tmCausalityItemDto);
                    causalityDto.addCausalityItemDto(tmCausalityItemDto);
                }
                break;
            default:
                break;
        }
        return null;
    }

    @Override
    protected Object updateSingleObject(R3PreviewDTO r3PreviewDTO, Object obj) {
        super.updateSingleObject(r3PreviewDTO, obj);
        if (obj instanceof DrugDto) {
            DrugDto tmDrugDto = (DrugDto) obj;
            Map<String, List<R3PreviewDTO>> levelOneMap = r3PreviewDTO.getSubR3PreviewDTOMap();
            if (MapUtils.isEmpty(levelOneMap)) {
                return tmDrugDto;
            }
            for (Map.Entry<String, List<R3PreviewDTO>> singleEntry : levelOneMap.entrySet()) {
                String key = singleEntry.getKey();
                List<R3PreviewDTO> previewDTOList = singleEntry.getValue();
                if (CollectionUtils.isEmpty(previewDTOList)) {
                    continue;
                }
                updateChildrenObject(key, tmDrugDto, previewDTOList);
            }
        }
        return obj;
    }

    private Object updateChildrenObject(String key, DrugDto tmDrugDto, List<R3PreviewDTO> previewDTOList) {
        switch (key) {
            case E2BImportConfigConstant.PSUR_DRUG_ITEM:
                updateListObject(tmDrugDto.getProductDrugItemDtoList(), previewDTOList);
                break;
            case E2BImportConfigConstant.MEDICATION_REASON:
                updateListObject(tmDrugDto.getMedicationReasonDtoList(), previewDTOList);
                break;
            case E2BImportConfigConstant.DRUG_DOSE:
                updateListObject(tmDrugDto.getDrugDoseDtoList(), previewDTOList);
                break;
            case E2BImportConfigConstant.CAUSALITY:
                List<CausalityDto> tmCausalityDtos = tmDrugDto.getRCausalityDtoList();
                if (CollectionUtils.isEmpty(tmCausalityDtos)) {
                    break;
                }
                if (CollectionUtils.isEmpty(previewDTOList)) {
                    break;
                }
                Map<String, R3PreviewDTO> previewDTOMap = CollectionConverter.listToMap(previewDTOList, R3PreviewDTO::getItemId, previewDTO -> previewDTO);

                for (CausalityDto causalityDto : tmCausalityDtos) {
                    R3PreviewDTO previewDTO = previewDTOMap.get(causalityDto.getId());
                    super.updateSingleObject(previewDTO, causalityDto);
                    Map<String, List<R3PreviewDTO>> subMap = previewDTO.getSubR3PreviewDTOMap();
                    if (MapUtils.isEmpty(subMap)) {
                        continue;
                    }
                    String causalityItemKey = subMap.keySet().iterator().next();
                    List<R3PreviewDTO> items = subMap.get(causalityItemKey);
                    updateListObject(causalityDto.getCausalityItemDtoList(), items);
                }
                break;
            default:
                break;
        }
        return tmDrugDto;
    }

    @Override
    protected Object getResultInstance(R3PreviewDTO previewDTO) throws IllegalAccessException, InstantiationException {
        DrugDto dto = DrugDto.class.newInstance();
        dto.setId(UUID.randomUUID().toString());
        dto.setReportId(SystemContext.get(E2BImportConfigConstant.SC_PREVIEW_REPORT_ID));
        dto.setIsDelete(false);
        return dto;
    }

    @Override
    protected void fill(String category, IcsrEntityDto reportEntityDto, Object obj) {
        reportEntityDto.addDrugDto((DrugDto) obj);
    }


    @Override
    protected boolean isMappingMatch(Object object, String mappingKey) {
        DrugDto dto = (DrugDto) object;
        return !StringUtils.equalsIgnoreCase(ItemUniqueCodeProvider.DRUG_TYPE_Treatment, dto.getDrugType());
    }

    @Override
    protected List getTaimeiPVObjectList(IcsrEntityDto reportEntityDto) {
        return reportEntityDto.getDrugDtoList();
    }

    @Override
    protected E2BR3SingleExportDto generateSingleExportDTO(Object obj, List<R3MappingDTO> mappingList, R3CommonDataParameter parameter) {
        E2BR3SingleExportDto drugExportDTO = super.generateSingleExportDTO(obj, mappingList, parameter);
        //获得对应的mapping
        Map<String, List<R3MappingDTO>> subMappings = getLevelTwoMappingMap(parameter);
        DrugDto drugDTO = (DrugDto) obj;
        //获得对应的数据，key和mapping一样，
        Map<String, List> subLists = getLevelTwoListMap(drugDTO);
        for (Map.Entry<String, List> subListEntry : subLists.entrySet()) {
            String key = subListEntry.getKey();
            List subList = subListEntry.getValue();
            //获取该类型对应的mapping例如 既往病史获得既往病史的mapping,既往药物获得既往药物的mapping
            List<R3MappingDTO> subMapping = subMappings.get(key);
            if (CollectionUtils.isEmpty(subMapping)) {
                logger.warn("there is no mapping result for " + key);
                continue;
            }
            for (Object subObj : subList) {
                E2BR3SingleExportDto subDTO = super.generateSingleExportDTO(subObj, subMapping, parameter);
                drugExportDTO.addChild(key, subDTO);
                if (StringUtils.equalsIgnoreCase(key, E2BImportConfigConstant.CAUSALITY_NUM)) {
                    //存在下一级CausalityItem
                    Map<String, List<R3MappingDTO>> drugItemMapping = getDrugCausalityItemConfig(parameter);
                    List<R3MappingDTO> ssubMappings = drugItemMapping.get(E2BImportConfigConstant.CAUSALITY_ITEM_NUM);
                    CausalityDto tmCausalityDto = (CausalityDto) subObj;
                    List<CausalityItemDto> itemList = tmCausalityDto.getCausalityItemDtoList();
                    if (CollectionUtils.isEmpty(itemList)) {
                        continue;
                    }
                    for (Object ssObj : itemList) {
                        CausalityItemDto ssObj1 = (CausalityItemDto) ssObj;
                        String sourceItem = ssObj1.getSourceItem();
                        if (StringUtils.isNotBlank(sourceItem)) {
                            String code = causalityItemSourceMap.get(sourceItem);
                            ssObj1.setSource(code);
                            if (StringUtils.isBlank(ssObj1.getResultItem())) {
                                ssObj1.setResult("");
                            } else {
                                String code1 = causalityItemResultMap.get(ssObj1.getResultItem());
                                ssObj1.setResult(code1);
                            }
                        }
                        E2BR3SingleExportDto ssubDTO = super.generateSingleExportDTO(ssObj, ssubMappings, parameter);
                        drugExportDTO.addChild(E2BImportConfigConstant.CAUSALITY_ITEM_NUM, ssubDTO);
                    }
                }
            }
        }
        return drugExportDTO;
    }

    @Override
    protected void customPreview(R3PreviewDTO previewDTO, String subElementNumber, R3CommonDataParameter commonDataParameter) {
        super.customPreview(previewDTO, subElementNumber, commonDataParameter);
        R3PreviewItemDTO previewItemDTO = new R3PreviewItemDTO();
        previewItemDTO.setIsDisplay(false);
        previewItemDTO.setName("manufacture");
        previewDTO.put("manufacture", previewItemDTO);
        R3PreviewItemDTO drugtype = previewDTO.getPreviewItemDTOMap().get("drugtype");
        if (drugtype != null) {
            R3PreviewItemDTO medicineinteractions = previewDTO.getPreviewItemDTOMap().get("medicineinteractions");
            R3PreviewItemDTO untreated = previewDTO.getPreviewItemDTOMap().get("untreated");
            String value = drugtype.fetchOneSourceValue();
            if (StringUtils.equals(value, "3")) {
                for (String s : medicineinteractions.getProcessedValueMap().keySet()) {
                    medicineinteractions.getProcessedValueMap().put(s, ItemUniqueCodeProvider.DRUG_CONTINUED_YES);
                }
                for (String s : drugtype.getProcessedValueMap().keySet()) {
                    drugtype.getProcessedValueMap().put(s, ItemUniqueCodeProvider.DRUG_TYPE_DOUBT);
                }
                for (String s : untreated.getProcessedValueMap().keySet()) {
                    untreated.getProcessedValueMap().put(s, "");
                }
            } else if (StringUtils.equals(value, "4")) {
                for (String s : untreated.getProcessedValueMap().keySet()) {
                    untreated.getProcessedValueMap().put(s, ItemUniqueCodeProvider.YES);
                }
                for (String s : drugtype.getProcessedValueMap().keySet()) {
                    drugtype.getProcessedValueMap().put(s, ItemUniqueCodeProvider.DRUG_TYPE_DOUBT);
                }
                for (String s : medicineinteractions.getProcessedValueMap().keySet()) {
                    medicineinteractions.getProcessedValueMap().put(s, "");
                }
            } else {
                for (String s : untreated.getProcessedValueMap().keySet()) {
                    untreated.getProcessedValueMap().put(s, "");
                }
                for (String s : medicineinteractions.getProcessedValueMap().keySet()) {
                    medicineinteractions.getProcessedValueMap().put(s, "");
                }
            }
        }
    }

    /**
     * 获取causality 方式， 是通过 报告下标 + 药物下标 + reactionID 外键进行解析
     * 获取其他reason方式 通过报告下标 + 药物下标 + 具体item 的下标进行解析
     */
    @Override
    public List<R3PreviewDTO> preview(String flag, String category, String subElementNumber, Document doc, R3CommonDataParameter commonDataParameter, List<R3MappingDTO> mappings, int reportIter) {
        List<R3PreviewDTO> levelOneResult = super.preview(flag, category, subElementNumber, doc, commonDataParameter, mappings, reportIter);
        Map<String, List<R3MappingDTO>> subMappings = getLevelTwoMappingMap(commonDataParameter);
        if (CollectionUtils.isEmpty(levelOneResult)) {
            return null;
        }

        List<R3PreviewDTO> result = new ArrayList<>();
        int componentIter = 0;
        for (R3PreviewDTO levelOneDTO : levelOneResult) {
            String fileDrugId = levelOneDTO.fetchFileDrugId();
            componentIter++;
            for (Map.Entry<String, List<R3MappingDTO>> levelTwoEntry : subMappings.entrySet()) {
                List<R3MappingDTO> levelTwoMappingList = levelTwoEntry.getValue();
                String twoKeyElementNumber = levelTwoEntry.getKey();
                List<R3PreviewDTO> levelTwoPreviewList = null;
                if (StringUtils.containsIgnoreCase(twoKeyElementNumber, E2BImportConfigConstant.CAUSALITY_NUM)) {

                    //Causality 's Parser
                    levelTwoPreviewList = getCausalityList(commonDataParameter, doc, flag, reportIter, componentIter, fileDrugId, twoKeyElementNumber, levelTwoMappingList);
                } else {
                    //Reason Psur Dose MoreInfo 's Parser
                    levelTwoPreviewList = getCommonList(commonDataParameter, doc, flag, reportIter, componentIter, fileDrugId, null, twoKeyElementNumber, levelTwoMappingList);
                }
                String subCategory = elementNumberToCategory(twoKeyElementNumber);
                if (CollectionUtils.isNotEmpty(levelTwoPreviewList) && StringUtils.isNotBlank(subCategory)) {
                    if (StringUtils.containsIgnoreCase(twoKeyElementNumber, E2BImportConfigConstant.DRUG_MORE_INFO_REPEAT_NUM)) {
                        buildAdditionalInfo(levelOneDTO, levelTwoPreviewList);
                        continue;
                    }
                    levelOneDTO.putSub(subCategory, levelTwoPreviewList);
                }

            }
            result.add(levelOneDTO);
        }
        return result;
    }

    /**
     * 暂时不开放的方法 :)))) 无法解释的逻辑
     *
     * @param drugDTO
     * @param addtionPreviewList
     */
    private void buildAdditionalInfo(R3PreviewDTO drugDTO, List<R3PreviewDTO> addtionPreviewList) {
        R3MappingDTO moreInfoMapping = getMoreInfoConfigMap();
        Map<String, String> sourceMap = new LinkedHashMap<>();
        Map<String, String> processMap = new LinkedHashMap<>();
        int index = 0;
        R3PreviewItemDTO dto = new R3PreviewItemDTO(moreInfoMapping);
        for (R3PreviewDTO info : addtionPreviewList) {
            String sourceKey = info.getPreviewItemDTOMap().values().iterator().next().getSourceValueMap().keySet().iterator().next();
            String sourceValue = info.getPreviewItemDTOMap().values().iterator().next().getSourceValueMap().values().iterator().next();
            int iter = index++;
            sourceMap.put(sourceKey + (iter), sourceValue);

            String key = info.getPreviewItemDTOMap().values().iterator().next().getProcessedValueMap().keySet().iterator().next();
            String value = info.getPreviewItemDTOMap().values().iterator().next().getProcessedValueMap().values().iterator().next();

            processMap.put(key + (iter), value);
        }
        dto.setSourceValueMap(sourceMap);
        dto.setProcessedValueMap(processMap);

        drugDTO.put(moreInfoMapping.getTarget(), dto);
    }

    //DrugReason DrugDose DrugPsur 解析方式
    private List<R3PreviewDTO> getCommonList(R3CommonDataParameter parameter, Document doc, String flag, int reportIter, int componentIter, String fileDrugId, String fileEventId, String twoKeyElementNumber, List<R3MappingDTO> levelTwoMappingList) {
        HL7ConfigDto componentXPath = parameter.getE2bR3HL7Config(twoKeyElementNumber);
        String levelTwoPath = E2BDocumentUtil.buildPath(flag, twoKeyElementNumber, componentXPath.getXpath(), reportIter, componentIter, 0, fileDrugId, fileEventId);
        List<Node> levelTwoNodes = E2BDocumentUtil.getXpathNodes(doc, levelTwoPath);
        if (CollectionUtils.isEmpty(levelTwoNodes)) {
            return null;
        }
        int subComponentIndex = 0;
        List<R3PreviewDTO> levelTwoPreviewList = new ArrayList<>();
        String subCategory = elementNumberToCategory(twoKeyElementNumber);
        for (Node levelTwoNode : levelTwoNodes) {
            subComponentIndex++;
            R3PreviewDTO levelTwoPreviewDTO = toPreviewDTO(flag, subCategory, twoKeyElementNumber, doc, parameter, levelTwoMappingList, reportIter, componentIter, subComponentIndex, fileDrugId, fileEventId);
            if (levelTwoPreviewDTO != null) {
                levelTwoPreviewList.add(levelTwoPreviewDTO);
            }
        }
        return levelTwoPreviewList;
    }

    //Causality解析方式 通过 reactionIdSet 来循环,因为drugCausalityItem 是在drug外部，所以解析的范式不
    private List<R3PreviewDTO> getCausalityList(R3CommonDataParameter parameter, Document doc, String flag, int reportIter, int componentIter, String fileDrugId, String elementNumber, List<R3MappingDTO> levelTwoMappingList) {
        R3PreviewItemDTOHandler handler = parameter.getR3PreviewItemDTOHandler();
        List<R3PreviewDTO> levelTwoPreviewList = new ArrayList<>();
        Map<String, List<R3MappingDTO>> itemConfig = getDrugCausalityItemConfig(parameter);
        String key = itemConfig.keySet().iterator().next();
        List<R3MappingDTO> levelThreeMappingList = itemConfig.get(key);
        for (String fileEventId : handler.getFileEventIdSet()) {
            R3PreviewDTO levelTwoPreviewDTO = toPreviewDTO(flag, E2BImportConfigConstant.CAUSALITY, elementNumber, doc, parameter, levelTwoMappingList, reportIter, componentIter, 0, fileDrugId, fileEventId);
            if (levelTwoPreviewDTO == null) {
                logger.warn("Causality Info is NULL for fileDrugId:" + fileDrugId + " fileEventId:" + fileEventId);
                continue;
            }

            levelTwoPreviewList.add(levelTwoPreviewDTO);
            //TODO 这里在解析Causality Item 是有错误的，存在实现逻辑上的错误
            List<R3PreviewDTO> drugCausalityItemList = buildDrugCausalityItemList(parameter, doc, flag, reportIter, fileDrugId, fileEventId, key, levelThreeMappingList);
            String subCategory = elementNumberToCategory(key);
            if (CollectionUtils.isEmpty(drugCausalityItemList)) {
                logger.error("Causality Item Info is NULL for fileDrugId:" + fileDrugId + " fileEventId:" + fileEventId);
                continue;
            } else {
                logger.error("found causality items for fileDrugId:" + fileDrugId + " fileEventId:" + fileEventId);
                levelTwoPreviewDTO.putSub(subCategory, drugCausalityItemList);
            }

        }
        return levelTwoPreviewList;
    }

    //这里的代码和getCommonList中高度重合，可能需要抽象 TODO
    private List<R3PreviewDTO> buildDrugCausalityItemList(R3CommonDataParameter parameter, Document doc, String flag, int reportIter, String fileDrugId, String fileEventId, String twoKeyElementNumber, List<R3MappingDTO> levelTwoMappingList) {
        HL7ConfigDto componentXPath = parameter.getE2bR3HL7Config(twoKeyElementNumber);
        String levelTwoPath = E2BDocumentUtil.buildPath(flag, twoKeyElementNumber, componentXPath.getXpath(), reportIter, 0, 0, fileDrugId, fileEventId);
        List<Node> levelTwoNodes = E2BDocumentUtil.getXpathNodes(doc, levelTwoPath);
        if (CollectionUtils.isEmpty(levelTwoNodes)) {
            return null;
        }
        int subComponentIndex = 0;
        List<R3PreviewDTO> levelTwoPreviewList = new ArrayList<>();
        String subCategory = elementNumberToCategory(twoKeyElementNumber);
        int componentIter = 0;
        for (Node levelTwoNode : levelTwoNodes) {
            componentIter++;
            R3PreviewDTO levelTwoPreviewDTO = toPreviewDTO(flag, subCategory, twoKeyElementNumber, doc, parameter, levelTwoMappingList, reportIter, componentIter, subComponentIndex, fileDrugId, fileEventId);
            if (levelTwoPreviewDTO != null) {
                levelTwoPreviewList.add(levelTwoPreviewDTO);
            }
        }
        return levelTwoPreviewList;
    }

    private String elementNumberToCategory(String elementNumber) {
        return E2BImportConfigConstant.drugSubElementNumber2DrugSubCategory.get(elementNumber);
    }

    private Map<String, List<R3MappingDTO>> getLevelTwoMappingMap(R3CommonDataParameter commonDataParameter) {
        ReportConfigDto importconfig = commonDataParameter.getReportImportconfigWithBLOBs();
        Map<String, List<R3MappingDTO>> config = new HashMap<>();
        /**
         * more information 是一个多记录但是需要拼接成一个串，目前只有的case只有一个，所以通过特殊定义
         * 实现方式可以多定义一个父节点的elementNumber获得循环的次数，然后进行循环
         */
        config.put(E2BImportConfigConstant.DRUG_MORE_INFO_REPEAT_NUM, Collections.singletonList(getMoreInfoConfigMap()));

        config.putAll(rebuildConfig(importconfig.getDrugDose()));
        config.putAll(rebuildConfig(importconfig.getDrugPsur()));
        config.putAll(rebuildConfig(importconfig.getDrugMedicationReason()));
        config.putAll(rebuildConfig(importconfig.getCausality()));

        return config;
    }

    private R3MappingDTO getMoreInfoConfigMap() {
        R3MappingDTO moreInfoConfig = new R3MappingDTO();
        moreInfoConfig.setElementNumbers(E2BImportConfigConstant.DRUG_MORE_INFO_NUM);
        moreInfoConfig.setTarget("drugAdditionalInfo");
        return moreInfoConfig;
    }

    //level three -> drugCausalityItem
    private Map<String, List<R3MappingDTO>> getDrugCausalityItemConfig(R3CommonDataParameter commonDataParameter) {
        ReportConfigDto importconfig = commonDataParameter.getReportImportconfigWithBLOBs();
        Map<String, List<R3MappingDTO>> config = new HashMap<>();
        config.putAll(rebuildConfig(importconfig.getDrugCausalityItem()));
        return config;
    }

    private Map<String, List> getLevelTwoListMap(DrugDto tmDrugDto) {
        Map<String, List> subListMap = new HashMap<>();
        if (tmDrugDto.getDrugDoseDtoList() != null) {
            subListMap.put(E2BImportConfigConstant.DRUG_DOSE_NUM, tmDrugDto.getDrugDoseDtoList());
        }
        if (tmDrugDto.getMedicationReasonDtoList() != null) {
            subListMap.put(E2BImportConfigConstant.MEDICATION_REASON_NUM, tmDrugDto.getMedicationReasonDtoList());
        }
        if (tmDrugDto.getProductDrugItemDtoList() != null) {
            subListMap.put(E2BImportConfigConstant.PSUR_DRUG_ITEM_NUM, tmDrugDto.getProductDrugItemDtoList());
        }
        if (tmDrugDto.getRCausalityDtoList() != null) {
            subListMap.put(E2BImportConfigConstant.CAUSALITY_NUM, tmDrugDto.getRCausalityDtoList());
        }

        //TODO 这里可以通过进行转换
        String drugaddtions = tmDrugDto.getDrugAddItionalInfo();
        if (StringUtils.isNotBlank(drugaddtions)) {
            List<DrugAdditionalR3DTO> additions = new ArrayList<>();
            String[] values = StringUtils.split(drugaddtions, ",");
            for (String value : values) {
                if (StringUtils.isNotBlank(value)) {
                    DrugAdditionalR3DTO drugAdditionalR3DTO = new DrugAdditionalR3DTO();
                    drugAdditionalR3DTO.setDrugAdditionalInfo(value);
                    additions.add(drugAdditionalR3DTO);
                }
            }
            subListMap.put(E2BImportConfigConstant.DRUG_MORE_INFO_REPEAT_NUM, additions);
        }

        return subListMap;
    }
}
