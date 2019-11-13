package org.mickey.framework.core.web;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.SystemContext;
import org.mickey.framework.common.dto.ActionResult;
import org.mickey.framework.common.dto.ErrorInfo;
import org.mickey.framework.common.exception.BusinessException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Data
@Slf4j
public class RestTemplateUtils {

    public static <T> T convertActionResult(ResponseEntity<ActionResult<T>> responseEntity) {
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new BusinessException(new ErrorInfo[]{new ErrorInfo(responseEntity.getStatusCode().getReasonPhrase(), responseEntity.getStatusCode().toString())});
        }
        ActionResult<T> result = (ActionResult) responseEntity.getBody();
        if (!result.isSuccess()) {
            throw new BusinessException(result.getErrors());
        }
        return (T) result.getData();
    }

    public static void addSystemContextToHeader(HttpHeaders headers) {
        log.debug("preparing request header...");
        if (headers == null) {
            return;
        }
        Map<String, String> contextMap = SystemContext.getContextMap();
        if (contextMap == null) {
            return;
        }
        List<Pair<String, String>> pairs = convertSystemContext();
        if (CollectionUtils.isEmpty(pairs)) {
            return;
        }
        pairs.forEach(pair ->
                headers.add((String) pair.getKey(), (String) pair.getValue()));
    }


    private static Pair<String, String> convertKey(String name, String value, boolean encoding) {
        if (encoding) {
            try {
                return Pair.of(name, URLEncoder.encode(value, "utf-8"));
            } catch (UnsupportedEncodingException e) {
                log.error("can't convert value:" + value + " to utf-8,will set the raw value");
                return Pair.of(name, value);
            }
        }
        return Pair.of(name, value);
    }


    public static List<Pair<String, String>> convertSystemContext() {
        Map<String, String> contextMap = SystemContext.getContextMap();
        if (contextMap == null) {
            return Collections.emptyList();
        }
        List<Pair<String, String>> pairs = new ArrayList<Pair<String, String>>();
        for (Map.Entry<String, String> entry : contextMap.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();

            if (StringUtils.isBlank(value)) {
                log.warn("header:" + key + "'s value:" + value + " is empty,will not add to headers");
                continue;
            }
//            if (!StringUtils.startsWithIgnoreCase(key, "")) {
//                continue;
//            }
            log.debug("adding header{" + key + ":" + value + "}");
            if (StringUtils.equalsIgnoreCase(key, "AccountName") ||
                    StringUtils.equalsIgnoreCase(key, "UserName")) {
                pairs.add(convertKey(key, value, true));
                continue;
            }
            pairs.add(convertKey(key, value, false));
        }

        return pairs;
    }
}
