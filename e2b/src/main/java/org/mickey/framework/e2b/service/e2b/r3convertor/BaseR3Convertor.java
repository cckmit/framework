package org.mickey.framework.e2b.service.e2b.r3convertor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.dom4j.Document;
import org.dom4j.Node;
import org.mickey.framework.common.dto.ErrorInfo;
import org.mickey.framework.common.exception.BusinessException;
import org.mickey.framework.common.util.CollectionConverter;
import org.mickey.framework.common.util.DateUtils;
import org.mickey.framework.common.util.MapUtils;
import org.mickey.framework.common.util.ReflectionUtil;
import org.mickey.framework.e2b.constant.E2BImportConfigConstant;
import org.mickey.framework.e2b.dto.config.EstriConfigDto;
import org.mickey.framework.e2b.dto.config.HL7ConfigDto;
import org.mickey.framework.e2b.dto.config.ReportConfigDto;
import org.mickey.framework.e2b.dto.e2b.E2BR3SingleExportDto;
import org.mickey.framework.e2b.dto.report.IcsrDtoInterface;
import org.mickey.framework.e2b.dto.report.IcsrEntityDto;
import org.mickey.framework.e2b.dto.report.MeddraFieldInfoDto;
import org.mickey.framework.e2b.dto.report.NullFlavorInfoDto;
import org.mickey.framework.e2b.service.e2b.handler.R3PreviewItemDTOHandler;
import org.mickey.framework.e2b.service.e2b.helper.CountryLanguageHelper;
import org.mickey.framework.e2b.service.e2b.helper.MeddraHelper;
import org.mickey.framework.e2b.service.e2b.helper.NullFlavorHelper;
import org.mickey.framework.e2b.service.e2b.impl.E2BDocumentUtil;
import org.mickey.framework.e2b.service.e2b.parameter.R3CommonDataParameter;
import org.mickey.framework.e2b.service.e2b.preview3.R3MappingDTO;
import org.mickey.framework.e2b.service.e2b.preview3.R3PreviewDTO;
import org.mickey.framework.e2b.service.e2b.preview3.R3PreviewItemDTO;

import java.util.*;

/**
 * description
 *
 * @author mickey
 * 2020-02-24
 */
@Slf4j
public abstract class BaseR3Convertor {
//    @Resource
//    protected Mapper mapper;


    /**
     * preview related function
     * buildRecords ???????????????????????????previewALL????????????
     * previewAll ????????????subCategory????????????
     * preview ????????????subjectCategory?????????
     * generatePreviewItemDTO ???????????????????????????
     * generateFileDrugIdDTO ??????????????????Causilty?????????????????????
     * generateFileEventIdDTO ??????????????????Causilty?????????????????????
     */

    public List<R3PreviewDTO> previewRecords(String flag, String category, int reportIter, Document document, R3CommonDataParameter parameter) {
        List<R3PreviewDTO> result = previewAll(flag, category, reportIter, document, parameter);
        return result;
    }

    private List<R3PreviewDTO> previewAll(String flag, String category, int reportIter, Document doc, R3CommonDataParameter commonDataParameter) {
        String mappingMapString = getMappingDTO(commonDataParameter.getReportImportconfigWithBLOBs());
        Map<String, List<R3MappingDTO>> mappingMap = rebuildConfig(mappingMapString);
        List<R3PreviewDTO> previewDTOList = new ArrayList<>();
        for (Map.Entry<String, List<R3MappingDTO>> singleEntry : mappingMap.entrySet()) {
            String subElementNumber = singleEntry.getKey();
            List<R3MappingDTO> mappings = singleEntry.getValue();
            List<R3PreviewDTO> subPreviewList = preview(flag, category, subElementNumber, doc, commonDataParameter, mappings, reportIter);
            if (CollectionUtils.isEmpty(subPreviewList)) {
                continue;
            }
            previewDTOList.addAll(subPreviewList);
        }
        return previewDTOList;
    }

    protected void customPreview(R3PreviewDTO previewDTO, String subElementNumber, R3CommonDataParameter commonDataParameter) {

    }

    protected R3PreviewDTO toPreviewDTO(String flag, String category, String subElementNumber, Document doc, R3CommonDataParameter commonDataParameter, List<R3MappingDTO> mappings, int reportIter, int componentIter, int subcomponentIter, String fileDrugId, String fileEventId) {
        R3PreviewItemDTOHandler handler = commonDataParameter.getR3PreviewItemDTOHandler();
        Map<String, R3PreviewItemDTO> previewItemDTOMap = new LinkedHashMap<>();
        String innerFileDrugId = fileDrugId;
        String innerFileEventId = fileEventId;
        for (R3MappingDTO r3MappingDTO : mappings) {
            //??????????????????, reportIter,componentIter,subcompoentIter,innerFileEventId,innerFileDrugId ?????????elemntNumber???????????????E2BDocumentUtils???
            R3PreviewItemDTO previewItemDTO = generatePreviewItemDTO(flag, doc, commonDataParameter, r3MappingDTO, reportIter, componentIter, subcomponentIter, innerFileDrugId, innerFileEventId);

            //TODO get null flavor information

            if (previewItemDTO == null) {
                continue;
            }

            //?????????????????????fileDrugId?????????fileDrugId ??????????????????????????????????????????
            if (StringUtils.equalsIgnoreCase(r3MappingDTO.getTarget(), E2BImportConfigConstant.TARGET_FILE_DRUG_ID_KEY)) {
                if (StringUtils.isBlank(innerFileDrugId)) {
                    innerFileDrugId = previewItemDTO.getValue();
                }
                handler.addDrugId(innerFileDrugId);
            }
            //?????????????????????fileEventId??????????????????????????????fileEventId???????????????????????????????????????
            if (StringUtils.equalsIgnoreCase(r3MappingDTO.getTarget(), E2BImportConfigConstant.TARGET_FILE_EVENT_ID_KEY)) {
                if (StringUtils.isBlank(innerFileEventId)) {
                    innerFileEventId = previewItemDTO.getValue();
                }
                handler.addReactionId(innerFileEventId);
            }

            previewItemDTOMap.put(r3MappingDTO.getTarget(), previewItemDTO);
        }

        if (MapUtils.isEmpty(previewItemDTOMap)) {
            return null;
        }

        //TODO ???????????????????????????????????????????????????????????????????????????????????????fileDrugId,fileEventId????????????????????????????????????????????????
        if (StringUtils.isNotBlank(innerFileDrugId)) {
            R3PreviewItemDTO fileDrugDTO = generateFileDrugIdItem(innerFileDrugId);
            previewItemDTOMap.put(E2BImportConfigConstant.TARGET_FILE_DRUG_ID_KEY, fileDrugDTO);
        }

        if (StringUtils.isNotBlank(innerFileEventId)) {
            R3PreviewItemDTO fileEventDTO = generateFileEventIdItem(innerFileEventId);
            previewItemDTOMap.put(E2BImportConfigConstant.TARGET_FILE_EVENT_ID_KEY, fileEventDTO);
        }

        R3PreviewDTO previewDTO = new R3PreviewDTO();
        previewDTO.setCategory(category);
        previewDTO.setSubElementNumber(subElementNumber);
        previewDTO.setPreviewItemDTOMap(previewItemDTOMap);

        customPreview(previewDTO, subElementNumber, commonDataParameter);

        return previewDTO;
    }

    public List<R3PreviewDTO> preview(String flag, String category, String subElementNumber, Document doc, R3CommonDataParameter commonDataParameter, List<R3MappingDTO> mappings, int reportIter) {
        List<R3PreviewDTO> previewDTOList = new ArrayList<>();
        R3PreviewItemDTOHandler handler = commonDataParameter.getR3PreviewItemDTOHandler();
        //Message Identifier & compare N.2.r.1 to get first repeat number: r
        HL7ConfigDto componentXPath = commonDataParameter.getE2bR3HL7Config(subElementNumber);
        if (componentXPath == null) {
            log.warn(" element :" + subElementNumber + " didnit get xpath value");
            return null;
        }
        String modulePath = E2BDocumentUtil.buildPath(flag, subElementNumber, componentXPath.getXpath(), reportIter, 0, 0);
        List<Node> moduleIdentyNode = E2BDocumentUtil.getXpathNodes(doc, modulePath);
        int moduleIter = 0;

        if (CollectionUtils.isEmpty(moduleIdentyNode)) {
            log.warn(" there is no result for:" + category + " flag:" + flag + " subElement:" + subElementNumber + " report Index:" + reportIter);
            return null;
        }

        for (Node moduleNode : moduleIdentyNode) {
            moduleIter++;
            String moduleID = moduleNode.getText();
            R3PreviewDTO previewDTO = toPreviewDTO(flag, category, subElementNumber, doc, commonDataParameter, mappings, reportIter, moduleIter, 0, null, null);

            if (previewDTO == null) {
                log.warn("++++Detect empty previewDTO for:" + flag + ":::category:::" + category + ":::" + subElementNumber + " reportIter:" + reportIter + " moduleIter::" + moduleIter);
                continue;
            }

            previewDTOList.add(previewDTO);
        }
        return previewDTOList;
    }

    protected R3PreviewItemDTO generateFileDrugIdItem(String fileDrugId) {
        if (StringUtils.isBlank(fileDrugId)) {
            return null;
        }
        R3PreviewItemDTO dto = new R3PreviewItemDTO(E2BImportConfigConstant.TARGET_FILE_DRUG_ID_KEY, fileDrugId);
        dto.setIsDisplay(false);
        return dto;
    }

    protected R3PreviewItemDTO generateFileEventIdItem(String fileEventId) {
        if (StringUtils.isBlank(fileEventId)) {
            return null;
        }
        R3PreviewItemDTO dto = new R3PreviewItemDTO(E2BImportConfigConstant.TARGET_FILE_EVENT_ID_KEY, fileEventId);
        dto.setIsDisplay(false);
        return dto;
    }

    private void meddraProcess(R3PreviewItemDTO previewItemDTO, String flag, Document doc, R3CommonDataParameter commonDataParameter, R3MappingDTO r3MappingDTO, int reportIter, int moduleIter, int subCompnoentIter, String drugId, String reactionId) {
        Map<String, HL7ConfigDto> xpathMap = commonDataParameter.getE2bR3HL7ConfigMap();
        R3PreviewItemDTOHandler handler = commonDataParameter.getR3PreviewItemDTOHandler();
        String meddraElementNumber = r3MappingDTO.getMeddraElementNumber();
        HL7ConfigDto meddraValueXpath = xpathMap.get(meddraElementNumber);
        String meddraVersionElementNumber = r3MappingDTO.getMeddraVersionElementNumber();
        HL7ConfigDto meddraVersionValueXpath = xpathMap.get(meddraVersionElementNumber);
        if (StringUtils.isNotBlank(meddraElementNumber)) {
            String builtPath = E2BDocumentUtil.buildPath(flag, meddraElementNumber, meddraValueXpath.getXpath(), reportIter, moduleIter, subCompnoentIter, drugId, reactionId);
            String nodeValue = E2BDocumentUtil.getXpathSingleValue(doc, builtPath);
            if (StringUtils.isNotBlank(nodeValue)) {
                String versionBuildPath = E2BDocumentUtil.buildPath(flag, meddraVersionElementNumber, meddraVersionValueXpath.getXpath(), reportIter, moduleIter, subCompnoentIter);
                String versionValue = E2BDocumentUtil.getXpathSingleValue(doc, versionBuildPath);
                previewItemDTO.setMeddraValue(nodeValue);
                previewItemDTO.setMeddraVersion(versionValue);
                handler.addMeddra(previewItemDTO);
            }
        }
    }

    protected R3PreviewItemDTO generatePreviewItemDTO(String flag, Document doc, R3CommonDataParameter commonDataParameter, R3MappingDTO r3MappingDTO, int reportIter, int moduleIter, int subCompnoentIter, String drugId, String reactionId) {
        Map<String, HL7ConfigDto> xpathMap = commonDataParameter.getE2bR3HL7ConfigMap();
        R3PreviewItemDTOHandler handler = commonDataParameter.getR3PreviewItemDTOHandler();
        String target = r3MappingDTO.getTarget();
        R3PreviewItemDTO previewItemDTO = new R3PreviewItemDTO(r3MappingDTO);
        previewItemDTO.setName(target);
        previewItemDTO.setIsDisplay(r3MappingDTO.getDisplay());

        if (StringUtils.equalsIgnoreCase(E2BImportConfigConstant.debugTarget, target)) {
            System.currentTimeMillis();
        }

        //meddra????????????????????????????????????meddra?????????????????????????????????????????????
        meddraProcess(previewItemDTO, flag, doc, commonDataParameter, r3MappingDTO, reportIter, moduleIter, subCompnoentIter, drugId, reactionId);
        //??????elementNumber??????????????????meddraValue ???????????????????????????
        if (StringUtils.isNotEmpty(previewItemDTO.getMeddraValue())) {
            //?????????meddra????????????elment number???????????????
            HL7ConfigDto e2bxpath = xpathMap.get(r3MappingDTO.getFirstElementNumber());
            //????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????meddra????????????freetext???????????????
            String columnKey = buildE2BColumnKey(e2bxpath, r3MappingDTO);
            previewItemDTO.putSourceValue(columnKey, "");
            previewItemDTO.putProcessedSourceValue(columnKey, "");
        }

        if (StringUtils.isBlank(r3MappingDTO.getElementNumbers())) {
            return getValidItemDTO(previewItemDTO);
        }

        String[] elementArray = r3MappingDTO.getElementNumberArray();
        for (String elementNumber : elementArray) {
            HL7ConfigDto e2bxpath = xpathMap.get(elementNumber);
            if (e2bxpath == null) {
                log.warn(" element :" + elementNumber + " didnit get xpath value");
                continue;
            }
            String columnKey = buildE2BColumnKey(e2bxpath, r3MappingDTO);
            String pathString = e2bxpath.getXpath();
            String builtPath = E2BDocumentUtil.buildPath(flag, elementNumber, pathString, reportIter, moduleIter, subCompnoentIter, drugId, reactionId);
            if (StringUtils.containsIgnoreCase(e2bxpath.getCategory(), "repeat")) {

            } else {
                String nodeValue = E2BDocumentUtil.getXpathSingleValue(doc, builtPath);
                if (nodeValue == null) {
                    continue;
                }

                if (new Integer(1).equals(e2bxpath.getNeedTranslate())) {
                    handler.addYoudao(previewItemDTO);
                }
                previewItemDTO.putSourceValue(columnKey, nodeValue);
                String OID = e2bxpath.getOid();

                /**
                 * ??????????????????????????????elementNumber,???????????????????????????elementNumber ??????????????????????????????????????????????????????????????????????????????????????????F.r.3.2
                 * ??????????????????????????????????????????
                 */
                previewItemDTO.setCatchDataElementNumber(elementNumber);

                if (StringUtils.containsIgnoreCase(e2bxpath.getHl7DataType(), "Telecommunication Address (TEL)")) {
                    String processedValue = buildTelFunction(e2bxpath, nodeValue);
                    previewItemDTO.putProcessedSourceValue(columnKey, processedValue);
                    previewItemDTO.setPlainText(true);
                    continue;
                }

                if (StringUtils.containsIgnoreCase(e2bxpath.getIchDataType(), "Date")) {
                    String processedValue = DateUtils.e2bDateImportConvert(nodeValue);
                    previewItemDTO.putProcessedSourceValue(columnKey, processedValue);
                    continue;
                }
                /**
                 * ??????OID??????
                 * 1.?????????????????????
                 * 2.????????????????????????
                 * 3.?????????booleanPV
                 * 4.????????????
                 */
                if (CountryLanguageHelper.isCountryR3OID(OID)) {
                    String processedValue = CountryLanguageHelper.getCountryUniqueCode(nodeValue);
                    previewItemDTO.putProcessedSourceValue(columnKey, processedValue);
                    continue;
                }
                if (CountryLanguageHelper.isLanguageR3OID(OID)) {
                    String processedValue = CountryLanguageHelper.getLanguageUnqueCode(nodeValue);
                    previewItemDTO.putProcessedSourceValue(columnKey, processedValue);
                    continue;
                }
                if (isBooleanPVLogic(elementNumber, OID)) {
                    String processedValue = StringUtils.equalsIgnoreCase(nodeValue, "1") || StringUtils.equalsIgnoreCase(nodeValue, "true") ? "true" : "false";
                    previewItemDTO.putProcessedSourceValue(columnKey, processedValue);
                    continue;
                }

                if (isDictionaryLogicOID(commonDataParameter, elementNumber, OID)) {
                    String processedValue = commonDataParameter.getItemUniqueCodeByMapping(elementNumber, nodeValue);
                    if (StringUtils.isBlank(processedValue)) {
                        log.warn("NO Item Value for element:" + elementNumber + "  node value:" + nodeValue + " OID:" + OID);
                        processedValue = StringUtils.EMPTY;
                    }
                    previewItemDTO.putProcessedSourceValue(columnKey, processedValue);
                    continue;
                }
                previewItemDTO.putProcessedSourceValue(columnKey, nodeValue);
                previewItemDTO.setPlainText(true);
            }
        }

        if (StringUtils.isNotBlank(r3MappingDTO.getNullflavorElementNumbers())) {
            for (String nElementNumber : r3MappingDTO.fetchNullflavorElementNumberArray()) {
                HL7ConfigDto e2bxpath = xpathMap.get(nElementNumber);
                if (e2bxpath == null) {
                    log.warn(" null flavor element :" + nElementNumber + " didnit get xpath value");
                    continue;
                }
                String pathString = e2bxpath.getXpath();
                String builtPath = E2BDocumentUtil.buildPath(flag, nElementNumber, pathString, reportIter, moduleIter, subCompnoentIter, drugId, reactionId);
                String nodeValue = E2BDocumentUtil.getXpathSingleValue(doc, builtPath);
                if (StringUtils.isNotBlank(nodeValue)) {
                    previewItemDTO.setNullFlavorValue(nodeValue);
                    if (MapUtils.isEmpty(previewItemDTO.getSourceValueMap())) {
                        previewItemDTO.setIsDisplay(false);
                    }
                    previewItemDTO.putSourceValue(r3MappingDTO.getFirstElementNumber(), "");
                    break;
                }
            }
        }


        //????????????DTO?????????????????????
        return getValidItemDTO(previewItemDTO);
    }

    /**
     * ??????Preview??????mapping???key
     *
     * @param e2bxpath
     * @param mappingDTO
     * @return
     */
    public String buildE2BColumnKey(HL7ConfigDto e2bxpath, R3MappingDTO mappingDTO) {
        if (StringUtils.isNotBlank(mappingDTO.getMappingName())) {
            return mappingDTO.getMappingName();
        }
        if (e2bxpath == null) {
            return null;
        }

        return e2bxpath.getElementName() + "(" + e2bxpath.getElementNumber() + ")";
    }

    private String buildTelFunction(HL7ConfigDto xconfig, String nodeValue) {
        //TODO ?????? fax, mailto, tel ??????
        int idx = nodeValue.indexOf(":");
        if (idx > 0) {
            return nodeValue.substring(idx + 1);
        }
        return nodeValue;
    }

    protected R3PreviewItemDTO getValidItemDTO(R3PreviewItemDTO previewItemDTO) {
        if (previewItemDTO == null) {
            return null;
        }
        if (MapUtils.isEmpty(previewItemDTO.getSourceValueMap()) && StringUtils.isBlank(previewItemDTO.getMeddraValue()) && StringUtils.isBlank(previewItemDTO.getValue()) && StringUtils.isBlank(previewItemDTO.getNullFlavorValue())) {
            return null;
        }
        return previewItemDTO;
    }

    protected boolean isBooleanPVLogic(String elementNumber, String oid) {
        return StringUtils.containsIgnoreCase(oid, E2BImportConfigConstant.OID_BOOLEAN_PV);
    }

    protected boolean isDictionaryLogicOID(R3CommonDataParameter parameter, String elementNumber, String oid) {
        if (StringUtils.isBlank(oid)) {
            return false;
        }
        EstriConfigDto value = parameter.getEstriConfigByOID(elementNumber, oid);
        if (value == null) {
            log.error("NO CONFIG OID ITEM:" + elementNumber + " OID:" + oid);
            return false;
        }
        return true;
    }

    /**
     * ???????????????????????????????????????
     * ??????????????????????????????????????????????????????????????????????????????,???final??????
     *
     * @param mappingMapString
     * @return
     */
    protected final Map<String, List<R3MappingDTO>> baseRebuildConfig(String mappingMapString) {
        LinkedHashMap<String, List<R3MappingDTO>> result = JSON.parseObject(mappingMapString, new TypeReference<LinkedHashMap<String, List<R3MappingDTO>>>() {
        }.getType());
        return result;
    }

    /**
     * ?????????????????????mapping?????????preview ??? export ????????????
     * ?????????????????????
     */
    protected Map<String, List<R3MappingDTO>> rebuildConfig(String mappingMapString) {
        return baseRebuildConfig(mappingMapString);
    }

    protected abstract String getMappingDTO(ReportConfigDto reportImportconfigWithBLOBs);

    /**
     * ###??????reportEntityDto??????####
     * build ???????????????reportEntityDto
     * getResultInstance: ??????instance
     * buildSingleObject. ??????????????????
     * fill: ??????reportEntityDto
     */
    protected Object buildSingleObject(R3PreviewDTO r3PreviewDTO, Object obj) throws InstantiationException, IllegalAccessException {
        Map<String, R3PreviewItemDTO> previewItemDTOs = r3PreviewDTO.getPreviewItemDTOMap();
        if (MapUtils.isEmpty(previewItemDTOs)) {
            return null;
        }
        String id = ReflectionUtil.getPropertyValue2String(obj, "id");
        if (StringUtils.isBlank(id)) {
            throw new RuntimeException("id cannot be null");
        }
        r3PreviewDTO.setItemId(id);
        for (String fieldName : previewItemDTOs.keySet()) {
            R3PreviewItemDTO itemDTO = previewItemDTOs.get(fieldName);
            if (itemDTO == null) {
                continue;
            }
            String finalValue = itemDTO.getFinalValue();
            log.debug("start set value for:{}  value:{}", fieldName, finalValue);
            try {
                ReflectionUtil.setPropertyValue(obj, fieldName, finalValue);
            } catch (NumberFormatException e) {
                throw new BusinessException(new ErrorInfo("111", itemDTO.fetchOneProcessKey() + "?????????????????????"));
            }
            if (obj instanceof IcsrDtoInterface) {
                IcsrDtoInterface mi = (IcsrDtoInterface) obj;
                MeddraFieldInfoDto finalMeddraInfo = itemDTO.getFinalMeddrafidldinfoDto();
                if (finalMeddraInfo != null) {
                    //??????????????????????????????????????????????????????????????????????????????
                    if (StringUtils.isEmpty(finalMeddraInfo.getFieldName())) {
                        System.out.println("not set meddra fieldname for::" + fieldName);
                        finalMeddraInfo.setFieldName(fieldName);
                    }
                    MeddraHelper.addMeddra(mi, fieldName, finalMeddraInfo);
                }

                itemDTO.setFinalNullFlavorValue(itemDTO.getNullFlavorValue());
                if (StringUtils.isNotBlank(itemDTO.getFinalNullFlavorValue())) {
                    NullFlavorHelper.addNullflavor(mi, fieldName, itemDTO.getFinalNullFlavorValue());
                }
            }
        }
        return obj;
    }

    public void build(String category, IcsrEntityDto reportEntityDto, List<R3PreviewDTO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        Object obj = null;
        for (R3PreviewDTO previewdto : list) {
            try {
                Object outObject = getResultInstance(previewdto);
                obj = buildSingleObject(previewdto, outObject);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
            fill(category, reportEntityDto, obj);
        }
    }

    protected abstract Object getResultInstance(R3PreviewDTO previewDTO) throws IllegalAccessException, InstantiationException;

    protected abstract void fill(String category, IcsrEntityDto reportEntityDto, Object obj);


    public void updateOriginalSource(String category, IcsrEntityDto reportEntityDto, List<R3PreviewDTO> list) {
        List objectList = getTaimeiPVObjectList(reportEntityDto);
        updateListObject(objectList, list);
    }

    protected void updateListObject(List objectList, List<R3PreviewDTO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        Map<String, R3PreviewDTO> previewDTOMap = CollectionConverter.listToMap(list, R3PreviewDTO::getItemId, previewDTO -> previewDTO);
        if (CollectionUtils.isEmpty(objectList)) {
            return;
        }
        for (Object o : objectList) {
            String id = ReflectionUtil.getPropertyValue2String(o, "id");
            R3PreviewDTO previewDTO = previewDTOMap.get(id);
            updateSingleObject(previewDTO, o);
        }
    }


    /**
     * ????????????subcategory?????????????????????????????????freemarker??????
     */

    public Map<String, List<E2BR3SingleExportDto>> exportTemplateObject(String category, IcsrEntityDto reportEntityDto, R3CommonDataParameter parameter) {
        return baseExport(reportEntityDto, parameter);
    }

    protected Map<String, List<E2BR3SingleExportDto>> baseExport(IcsrEntityDto reportEntityDto, R3CommonDataParameter parameter) {
        List objectList = getTaimeiPVObjectList(reportEntityDto);
        if (CollectionUtils.isEmpty(objectList)) {
            return null;
        }
        Map<String, List<E2BR3SingleExportDto>> result = new HashMap<>();
        String mappingString = getMappingDTO(parameter.getReportImportconfigWithBLOBs());
        Map<String, List<R3MappingDTO>> mappingMap = rebuildConfig(mappingString);
        for (Map.Entry<String, List<R3MappingDTO>> mappingEntry : mappingMap.entrySet()) {
            String mappingKey = mappingEntry.getKey();
            List<R3MappingDTO> mappingList = mappingEntry.getValue();
            List<E2BR3SingleExportDto> subCategoryExportDTOList = new ArrayList<>();
            for (Object object : objectList) {
                //?????????????????????????????????mapping??????????????????????????????????????????????????????????????????????????????????????????
                boolean isMatch = isMappingMatch(object, mappingKey);
                if (!isMatch) {
                    continue;
                }

                E2BR3SingleExportDto dto = generateSingleExportDTO(object, mappingList, parameter);
                customExport(dto, object);
                subCategoryExportDTOList.add(dto);
            }
            if (CollectionUtils.isNotEmpty(subCategoryExportDTOList)) {
                result.put(mappingKey, subCategoryExportDTOList);
            }
        }
        return result;
    }

    protected abstract boolean isMappingMatch(Object object, String mappingKey);

    protected abstract List getTaimeiPVObjectList(IcsrEntityDto reportEntityDto);

    protected E2BR3SingleExportDto generateSingleExportDTO(Object obj, List<R3MappingDTO> mappingList, R3CommonDataParameter parameter) {
        E2BR3SingleExportDto dto = new E2BR3SingleExportDto();
        for (R3MappingDTO r3MappingDTO : mappingList) {
            fillSingleExportDTOField(dto, obj, r3MappingDTO, parameter);
            fillSIngleExportDTOMeddraField(dto, obj, r3MappingDTO);
            fillSingleExportDTONullflavor(dto, obj, r3MappingDTO);
        }
        return dto;
    }

    protected void customExport(E2BR3SingleExportDto dto, Object obj) {

    }

    /**
     * ??????????????????
     *
     * @param valueMap
     * @param key
     * @param value
     */
    protected void setTemplateValue(Map<String, String> valueMap, String key, String value) {
        valueMap.put(key, value);
    }


    private void fillSIngleExportDTOMeddraField(E2BR3SingleExportDto dto, Object object, R3MappingDTO mapping) {
        Map<String, String> result = dto.getValueMap();
        String meddraElementNumber = mapping.getMeddraElementNumber();

        if (StringUtils.equalsIgnoreCase(E2BImportConfigConstant.debugTarget, mapping.getTarget())) {
            System.currentTimeMillis();
        }

        if (StringUtils.isBlank(meddraElementNumber)) {
            return;
        }
        if (!(object instanceof IcsrDtoInterface)) {
            return;
        }
        IcsrDtoInterface meddraDTO = (IcsrDtoInterface) object;
        Map<String, MeddraFieldInfoDto> meddraMap = meddraDTO.getMeddraMap();
        if (MapUtils.isEmpty(meddraMap)) {
            return;
        }
        MeddraFieldInfoDto fieldInfoDTO = meddraMap.get(mapping.getTarget());
        if (fieldInfoDTO == null) {
            return;
        }
        String lltCode = fieldInfoDTO.getLltCode();
        if (!NumberUtils.isNumber(lltCode)) {
            return;
        }
        String version = fieldInfoDTO.getMeddraVersion();
        setTemplateValue(result, mapping.getMeddraVersionElementNumber(), version);
        setTemplateValue(result, mapping.getMeddraElementNumber(), lltCode);
    }

    private void fillSingleExportDTOField(E2BR3SingleExportDto dto, Object object, R3MappingDTO mapping, R3CommonDataParameter parameter) {
        if (mapping.isNotExport()) {
            return;
        }
        Map<String, String> result = dto.getValueMap();

        if (StringUtils.equalsIgnoreCase(mapping.getTarget(), E2BImportConfigConstant.debugTarget)) {
            System.currentTimeMillis();
        }
        //???????????????????????????????????????????????????????????????????????????TODO ???????????????????????????????????????????????????????????????????????????
        String elementNumber = mapping.getFirstElementNumber();
        Map<String, HL7ConfigDto> configMap = parameter.getE2bR3HL7ConfigMap();
        HL7ConfigDto e2bxpath = configMap.get(elementNumber);
        if (StringUtils.isEmpty(elementNumber)) {
            return;
        }
        String target = mapping.getTarget();
        String objectValue = ReflectionUtil.getPropertyValue2String(object, target);
        //????????????????????????
        if (StringUtils.isEmpty(objectValue)) {
            return;
        }
        String e2bValue = objectValue;

        //???????????????????????????????????????????????????????????????????????????
        if (e2bxpath == null) {
            setTemplateValue(result, elementNumber, e2bValue);
            return;
        }

        String OID = e2bxpath.getOid();
        if (StringUtils.containsIgnoreCase(e2bxpath.getIchDataType(), "Date")) {
            e2bValue = DateUtils.e2bR3DateExportConvert(objectValue);
        }
        /**
         * ??????OID??????
         * 1.?????????????????????
         * 2.????????????????????????
         * 3.????????????
         */
        if (CountryLanguageHelper.isCountryR3OID(OID)) {
            e2bValue = CountryLanguageHelper.getCountry(objectValue);
        }
        if (CountryLanguageHelper.isLanguageR3OID(OID)) {
            e2bValue = CountryLanguageHelper.getLanguage(objectValue);
        }
        if (isDictionaryLogicOID(parameter, elementNumber, OID)) {
            e2bValue = parameter.getR3ValuebyMapping(elementNumber, objectValue);
        }
        setTemplateValue(result, elementNumber, e2bValue);
    }

    private void fillSingleExportDTONullflavor(E2BR3SingleExportDto dto, Object object, R3MappingDTO mapping) {
        if (mapping.isNotExport()) {
            return;
        }
        String nullflavorElementNumbers = mapping.getNullflavorElementNumbers();
        if (StringUtils.isEmpty(nullflavorElementNumbers)) {
            return;
        }

        if (!(object instanceof IcsrDtoInterface)) {
            return;
        }

        Map<String, String> result = dto.getNullFlavorValueMap();
        if (StringUtils.equalsIgnoreCase(mapping.getTarget(), E2BImportConfigConstant.debugTarget)) {
            System.currentTimeMillis();
        }
        String fieldName = mapping.getTarget();
        IcsrDtoInterface reportDtosInterface = (IcsrDtoInterface) object;
        Map<String, NullFlavorInfoDto> nMap = reportDtosInterface.getNullFlavorMap();
        if (MapUtils.isEmpty(nMap)) {
            return;
        }
        NullFlavorInfoDto nInfo = nMap.get(fieldName);
        if (nInfo == null || StringUtils.isBlank(nInfo.getValue())) {
            return;
        }
        result.put(mapping.getFirstElementNumber(), nInfo.getValue());
    }

    protected Object updateSingleObject(R3PreviewDTO r3PreviewDTO, Object obj) {
        if (r3PreviewDTO != null) {
            Map<String, R3PreviewItemDTO> previewItemDTOs = r3PreviewDTO.getPreviewItemDTOMap();
            if (MapUtils.isEmpty(previewItemDTOs)) {
                return obj;
            }

            for (String fieldName : previewItemDTOs.keySet()) {
                R3PreviewItemDTO itemDTO = previewItemDTOs.get(fieldName);
                if (itemDTO == null) {
                    continue;
                }

                if (!itemDTO.isPlainText()) {
                    continue;
                }
                String finalValue = itemDTO.getSourceValue();
                log.debug("start set value for:{}  value:{}", fieldName, finalValue);
                try {
                    ReflectionUtil.setPropertyValue(obj, fieldName, finalValue);
                } catch (NumberFormatException e) {
                    throw new BusinessException(new ErrorInfo("111", itemDTO.fetchOneProcessKey() + "?????????????????????"));
                }
            }
        }
        return obj;
    }
}
