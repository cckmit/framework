package org.mickey.framework.i18n.service.i18ndictionary;

import org.mickey.framework.core.service.IBaseService;
import org.mickey.framework.i18n.po.I18nDictionary;

import java.util.Map;

/**
 * description
 *
 * @author codeGeneric
 * 2019/11/11
 */
public interface II18nDictionaryService extends IBaseService<I18nDictionary> {

    Map<String, String> queryByAppId(String appId);

    Map<String, String> queryByKey(String appId, String key, String locale);
}