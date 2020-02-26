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
     * buildRecords 实现接口方法其实和previewALL基本一样
     * previewAll 解析所有subCategory中的对象
     * preview 解析单个subjectCategory下对象
     * generatePreviewItemDTO 生成具体属性字段值
     * generateFileDrugIdDTO 主要是为了在Causilty中创建这个字段
     * generateFileEventIdDTO 主要是为了在Causilty中创建这个字段
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
            //具体是否使用, reportIter,componentIter,subcompoentIter,innerFileEventId,innerFileDrugId 取决于elemntNumber，需要参考E2BDocumentUtils类
            R3PreviewItemDTO previewItemDTO = generatePreviewItemDTO(flag, doc, commonDataParameter, r3MappingDTO, reportIter, componentIter, subcomponentIter, innerFileDrugId, innerFileEventId);

            //TODO get null flavor information

            if (previewItemDTO == null) {
                continue;
            }

            //通过配置初始化fileDrugId，注意fileDrugId 的配置需要放置在配置的最前面
            if (StringUtils.equalsIgnoreCase(r3MappingDTO.getTarget(), E2BImportConfigConstant.TARGET_FILE_DRUG_ID_KEY)) {
                if (StringUtils.isBlank(innerFileDrugId)) {
                    innerFileDrugId = previewItemDTO.getValue();
                }
                handler.addDrugId(innerFileDrugId);
            }
            //通过配置初始化fileEventId，注意通过配置初始化fileEventId的配置需要放置在配置最前面
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

        //TODO 这里有可能会出现覆盖的情况，不过不影响，这里设置这两个数值fileDrugId,fileEventId，主要是为了后面做对应的时候使用
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

        //meddra信息查找，只设置最基础的meddra信息，具体翻译逻辑需要统一处理
        meddraProcess(previewItemDTO, flag, doc, commonDataParameter, r3MappingDTO, reportIter, moduleIter, subCompnoentIter, drugId, reactionId);
        //如果elementNumber为空的话并且meddraValue 也没数据，直接跳过
        if (StringUtils.isNotEmpty(previewItemDTO.getMeddraValue())) {
            //如果有meddra的情况，elment number不会是多个
            HL7ConfigDto e2bxpath = xpathMap.get(r3MappingDTO.getFirstElementNumber());
            //这里是为了让前台读取的时候兼容，否则第一列就展示为空串了，这个方法主要是针对只有meddra但是没有freetext描述的情况
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
                 * 这里记录下找到数值的elementNumber,理论上有且只有一个elementNumber 能够找到值，这个步骤只针对一些对应位置敏感的情况进行处理例如F.r.3.2
                 * 后续根据这个字段进行逻辑处理
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
                 * 进入OID逻辑
                 * 1.判断是否是国家
                 * 2.判断是否是为语言
                 * 3.是否为booleanPV
                 * 4.获取字典
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


        //检查整个DTO是否为有效数据
        return getValidItemDTO(previewItemDTO);
    }

    /**
     * 拼接Preview页面mapping的key
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
        //TODO 进行 fax, mailto, tel 替换
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
     * 为什么需要构建这个方法？？
     * 原因我们需要在孙子类也调度，父亲类不允许覆盖整个方法,用final修饰
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
     * 公共方法，获取mapping关系，preview 和 export 都会用到
     * 父亲类可以覆盖
     */
    protected Map<String, List<R3MappingDTO>> rebuildConfig(String mappingMapString) {
        return baseRebuildConfig(mappingMapString);
    }

    protected abstract String getMappingDTO(ReportConfigDto reportImportconfigWithBLOBs);

    /**
     * ###生成reportEntityDto方法####
     * build 设置对象到reportEntityDto
     * getResultInstance: 获得instance
     * buildSingleObject. 构建个体对象
     * fill: 填充reportEntityDto
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
                throw new BusinessException(new ErrorInfo("111", itemDTO.fetchOneProcessKey() + "应该是数字类型"));
            }
            if (obj instanceof IcsrDtoInterface) {
                IcsrDtoInterface mi = (IcsrDtoInterface) obj;
                MeddraFieldInfoDto finalMeddraInfo = itemDTO.getFinalMeddrafidldinfoDto();
                if (finalMeddraInfo != null) {
                    //理论上不会走到这段代码，主要是为了查找问题设置的判断
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
     * 导出基于subcategory级别的导出对象，提供给freemarker使用
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
                //检查导出的类型是不是和mapping一致，比如相关病史需要和相关病史对应，既往药物和既往药物对应
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
     * 统一设置数据
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
        //由于可能出现使用逗号隔离开的情况，所以取得第一个，TODO 可能有特殊逻辑需要使用多个，这个后面遇到问题再处理
        String elementNumber = mapping.getFirstElementNumber();
        Map<String, HL7ConfigDto> configMap = parameter.getE2bR3HL7ConfigMap();
        HL7ConfigDto e2bxpath = configMap.get(elementNumber);
        if (StringUtils.isEmpty(elementNumber)) {
            return;
        }
        String target = mapping.getTarget();
        String objectValue = ReflectionUtil.getPropertyValue2String(object, target);
        //这里需要考虑字符
        if (StringUtils.isEmpty(objectValue)) {
            return;
        }
        String e2bValue = objectValue;

        //针对引用的字符进行处理，这些字符是不需要具备配置的
        if (e2bxpath == null) {
            setTemplateValue(result, elementNumber, e2bValue);
            return;
        }

        String OID = e2bxpath.getOid();
        if (StringUtils.containsIgnoreCase(e2bxpath.getIchDataType(), "Date")) {
            e2bValue = DateUtils.e2bR3DateExportConvert(objectValue);
        }
        /**
         * 进入OID逻辑
         * 1.判断是否是国家
         * 2.判断是否是为语言
         * 3.获取字典
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
                    throw new BusinessException(new ErrorInfo("111", itemDTO.fetchOneProcessKey() + "应该是数字类型"));
                }
            }
        }
        return obj;
    }
}
