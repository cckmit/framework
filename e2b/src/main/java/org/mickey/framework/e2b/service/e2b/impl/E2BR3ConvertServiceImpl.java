package org.mickey.framework.e2b.service.e2b.impl;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.exception.BusinessException;
import org.mickey.framework.common.util.CollectionConverter;
import org.mickey.framework.common.util.MapUtils;
import org.mickey.framework.e2b.constant.E2BImportConfigConstant;
import org.mickey.framework.e2b.constant.ErrorCodeProvider;
import org.mickey.framework.e2b.dto.config.EstriConfigDto;
import org.mickey.framework.e2b.dto.config.HL7ConfigDto;
import org.mickey.framework.e2b.dto.config.ReportConfigDto;
import org.mickey.framework.e2b.dto.e2b.E2BR3SingleExportDto;
import org.mickey.framework.e2b.dto.report.IcsrEntityDto;
import org.mickey.framework.e2b.service.config.IE2bR3EstriConfigService;
import org.mickey.framework.e2b.service.config.IE2bR3hl7ConfigService;
import org.mickey.framework.e2b.service.config.IImportConfigService;
import org.mickey.framework.e2b.service.e2b.E2BR3ConvertService;
import org.mickey.framework.e2b.service.e2b.helper.E2BR3EstriConfigHelper;
import org.mickey.framework.e2b.service.e2b.helper.E2BTaskDocumentHelper;
import org.mickey.framework.e2b.service.e2b.parameter.R3CommonDataParameter;
import org.mickey.framework.e2b.service.e2b.r3convertor.ConvertorR3Factory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * description
 *
 * @author mickey
 * 2020-02-24
 */
@Slf4j
@Service
public class E2BR3ConvertServiceImpl implements E2BR3ConvertService {
    @Resource
    IImportConfigService importConfigService;
    @Resource
    IE2bR3hl7ConfigService hl7ConfigService;
    @Resource
    IE2bR3EstriConfigService ie2BR3EstriConfigService;
    @Resource
    private ConvertorR3Factory convertorR3Factory;

    @Override
    public R3CommonDataParameter generateR3CommonDataParameter() {
        R3CommonDataParameter r3parameter = new R3CommonDataParameter();
        r3parameter.setReportImportconfigWithBLOBs(getE2BImportConfig());
        r3parameter.setE2bR3HL7ConfigMap(initE2bR3HL7ConfigMap());
        List<EstriConfigDto> configList = ie2BR3EstriConfigService.query();
        Map<String, EstriConfigDto> estriConfigMap = E2BR3EstriConfigHelper.getElementInfoKeyMap(configList);
        r3parameter.setR3ValueMapping(estriConfigMap);
        Map<String, EstriConfigDto> estriR3ConfigMap = E2BR3EstriConfigHelper.getUniqueCodeKeyMap(configList);
        r3parameter.setPvUniqueCodeMapping(estriR3ConfigMap);
        Map<String, EstriConfigDto> oidMapping = E2BR3EstriConfigHelper.getOIDMapping(configList);
        r3parameter.setOidMapping(oidMapping);
        return r3parameter;
    }

    @Override
    public Map<String, List<E2BR3SingleExportDto>> exportAllTemplateObject(R3CommonDataParameter r3Parameter, IcsrEntityDto entityDto) {
        Map<String, List<E2BR3SingleExportDto>> exportMap = new HashMap<>();
        for (String category : ConvertorR3Factory.getServiceMap().keySet()) {
            Map<String, List<E2BR3SingleExportDto>> singleExportResult = convertorR3Factory.export(category, entityDto, r3Parameter);
            if (MapUtils.isEmpty(singleExportResult)) {
                continue;
            }
            exportMap.putAll(singleExportResult);
        }
        return exportMap;
    }

    public ReportConfigDto getE2BImportConfig() {
        Optional<ReportConfigDto> dtoOptional = importConfigService.queryByUniqueCode(E2BImportConfigConstant.E2B_CONFIG_R3_UNIQUECODE);
        if (dtoOptional.isPresent()) {
            return dtoOptional.get();
        } else {
            throw new BusinessException(ErrorCodeProvider.R3_EXPORT_CONFIG_MISS);
        }
    }

    public Map<String, HL7ConfigDto> initE2bR3HL7ConfigMap() {

        if (MapUtils.isEmpty(E2BTaskDocumentHelper.e2bR3HL7ConfigMap)) {
            synchronized (E2BTaskDocumentHelper.e2bR3HL7ConfigMap) {
                if (MapUtils.isEmpty(E2BTaskDocumentHelper.e2bR3HL7ConfigMap)) {
                    long s = System.currentTimeMillis();
                    forceRefreshHL7Config();
                    long e = System.currentTimeMillis();
                    System.out.println("load e2bR3hl7config cost:" + (e - s));
                }
            }
        }
        return E2BTaskDocumentHelper.e2bR3HL7ConfigMap;
    }

    public void forceRefreshHL7Config() {
        List<HL7ConfigDto> hl7ConfigDtos = hl7ConfigService.query();
        E2BTaskDocumentHelper.e2bR3HL7ConfigMap = CollectionConverter.list2Map(hl7ConfigDtos, dto -> dto.getElementNumber());
    }
}
