package org.mickey.framework.core.i18nClient;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.SystemContext;
import org.mickey.framework.common.dto.ActionResult;
import org.mickey.framework.common.exception.BusinessException;
import org.mickey.framework.common.util.StringUtil;

import javax.annotation.Resource;
import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Data
@Slf4j
public class I18nProvider {
    @Resource
    private I18nProxy i18nProxy;

    public Map<String, String> get() {
        if (StringUtil.isBlank(SystemContext.getAppId())) {
            throw new BusinessException("缺少header参数：[appId]");
        }
        if (StringUtil.isBlank(SystemContext.getLocale())) {
            throw new BusinessException("缺少header参数：[lang]");
        }
        ActionResult<Map<String, String>> actionResult = i18nProxy.queryByAppId();
        if (actionResult.isSuccess()) {
            return actionResult.getData();
        } throw new BusinessException("调用i18n-service失败");
    }

    public String get(String key) {
        Map<String, String> i18nMap = get();
        return i18nMap.getOrDefault(key, key);
    }
}
