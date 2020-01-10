package org.mickey.framework.i18n.service.i18ndictionary.impl;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.SystemContext;
import org.mickey.framework.common.exception.BusinessException;
import org.mickey.framework.common.exception.NoI18nException;
import org.mickey.framework.common.query.v2.Criteria;
import org.mickey.framework.common.util.CollectionConverter;
import org.mickey.framework.common.util.CollectionUtil;
import org.mickey.framework.common.util.StringUtil;
import org.mickey.framework.core.service.GenericService;
import org.mickey.framework.i18n.mapper.I18nDictionaryMapper;
import org.mickey.framework.i18n.po.I18nDictionary;
import org.mickey.framework.i18n.service.i18ndictionary.II18nDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author codeGeneric
 * 2019/11/11
 */
@Slf4j
@Service
public class I18nDictionaryServiceImpl extends GenericService<I18nDictionaryMapper, I18nDictionary> implements II18nDictionaryService {

    @Resource
    private I18nDictionaryMapper mapper;
    @Autowired
    private Environment env;

    @Override
    public Map<String, String> queryByAppId(String appId) {
        if (StringUtil.isBlank(SystemContext.getLocale())) {
            throw new BusinessException();
        }

        List<I18nDictionary> byConditionBase = mapper.findByConditionBase(new Criteria()
                .and("appId", appId)
                .and("locale", SystemContext.getLocale())
                .and("tenantId", new String[] {"system", SystemContext.getTenantId()})
        );

        return genericConvert(byConditionBase);
    }

    @Override
    public Map<String, String> queryByKey(String appId, String key, String locale) {
        if (StringUtil.isBlank(SystemContext.getLocale())) {
            throw new BusinessException();
        }

        List<I18nDictionary> byConditionBase = mapper.findByConditionBase(new Criteria()
                .and("appId", appId)
                .and("locale", locale)
                .and("key", key)
                .and("tenantId", new String[] {"system", SystemContext.getTenantId()})
        );

        return genericConvert(byConditionBase);
    }

    @Override
    public int insert(I18nDictionary dto) {
        Map<String, String> queryByKey = queryByKey(dto.getAppId(), dto.getKey(), dto.getLocale());
        if (!queryByKey.isEmpty()) {
            SystemContext.setAppId(env.getProperty("spring.application.name"));
            throw new NoI18nException(String.format("key : %s ,已经存在", dto.getKey()));
        }
        return mapper.insert(dto);
    }

    private Map<String, String> genericConvert(List<I18nDictionary> byConditionBase) {

        List<I18nDictionary> systemList = CollectionUtil.selectNonNullToList(byConditionBase, i -> StringUtil.equals(i.getTenantId(), "system"));
        // 公司自定义的国际化字典项
        List<I18nDictionary> i18nDictionaries = CollectionUtil.selectNonNullToList(byConditionBase, i -> StringUtil.equals(i.getTenantId(), SystemContext.getTenantId()));

        Map<String, String> i18nMap = CollectionConverter.listToMap(systemList, k -> k.getKey(), v -> v.getValue());
        i18nMap.putAll(CollectionConverter.listToMap(i18nDictionaries, k -> k.getKey(), v -> v.getValue()));
        return i18nMap;
    }
}