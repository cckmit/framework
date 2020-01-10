package org.mickey.framework.common;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mickey.framework.common.util.MapUtils;

import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Data
public class SystemContext {

    private transient static ThreadLocal<Map<String, String>> contextMap = new ThreadLocal();

    /**
     * context map max capacity
     */
    private static Integer Max_Capacity = 100;
    /**
     * context map key | value max size
     */
    private static Integer Max_Size = 1024;

    public SystemContext() {
        super();
    }

    public static Map<String, String> getContextMap() {
        return contextMap.get();
    }

    public static String get(String key) {
        Map<String, String> contextMap = getContextMap();
        if (contextMap == null) {
            return null;
        }
        return contextMap.get(convertKey(key));
    }

    public static String put(String key, String value) {
        if (key == null) {
            log.error("key is null, can't put it into the context map");
            return value;
        }
        if (key.length() > Max_Size) {
            throw new RuntimeException("key is more than " + Max_Size + ", can't put it into the context map");
        }
        if (value != null && value.length() > Max_Size) {
            throw new RuntimeException("value is more than " + Max_Size + ", can't put it into the context map");
        }
        Map<String, String> contextMap = getContextMap();
        if (contextMap == null) {
            contextMap = new HashMap<>(MapUtils.getInitialCapacity(16));
            SystemContext.contextMap.set(contextMap);
        }
        if (contextMap.size() > Max_Capacity) {
            throw new RuntimeException("the context map is full, can't put anything");
        }
        if (value == null) {
            contextMap.remove(convertKey(key));
            return null;
        }
        return contextMap.put(convertKey(key), value);
    }

    public static void remove(String key) {
        if (key == null) {
            log.error("key is null, can't remove");
            return;
        }
        Map<String, String> contextMap = getContextMap();
        if (contextMap != null) {
            contextMap.remove(convertKey(key));
        }
    }

    private static String convertKey(String key) {
        return key.toLowerCase();
    }

    public static String getUserId() {
        return get(SystemConstant.HEADER_USER_ID);
    }

    public static void setUserId(String userId) {
        put(SystemConstant.HEADER_USER_ID, userId);
    }

    public static String getAccountId() {
        return get(SystemConstant.HEADER_ACCOUNT_ID);
    }

    public static void setAccountId(String accountId) {
        put(SystemConstant.HEADER_ACCOUNT_ID, accountId);
    }

    public static String getAccountName() {
        return get(SystemConstant.HEADER_ACCOUNT_NAME);
    }

    public static void setAccountName(String accountName) {
        put(SystemConstant.HEADER_ACCOUNT_NAME, accountName);
    }

    public static String getProjectId() {
        return get(SystemConstant.HEADER_PROJECT_ID);
    }

    public static void setProjectId(String projectId) {
        put(SystemConstant.HEADER_PROJECT_ID, projectId);
    }

    public static String getAppId() {
        return get(SystemConstant.HEADER_APP_ID);
    }

    public static void setAppId(String appId) {
        put(SystemConstant.HEADER_APP_ID, appId);
    }

    public static String getTraceId() {
        return get(SystemConstant.HEADER_TRACE_ID);
    }

    public static void setTraceId(String traceId) {
        put(SystemConstant.HEADER_TRACE_ID, traceId);
    }

    public static String getNodeId() {
        return get(SystemConstant.HEADER_NODE_ID);
    }

    public static void setNodeId(String nodeId) {
        put(SystemConstant.HEADER_NODE_ID, nodeId);
    }

    public static String getUserName() {
        return get(SystemConstant.HEADER_USER_NAME);
    }

    public static void setUserName(String userName) {
        put(SystemConstant.HEADER_USER_NAME, userName);
    }

    public static String getTenantId() {
        return get(SystemConstant.HEADER_TENANT_ID);
    }

    public static void setTenantId(String tenantId) {
        put(SystemConstant.HEADER_TENANT_ID, tenantId);
    }

    public static String getLocale() {
        String locale = get(SystemConstant.HEADER_LOCALE);
        if (locale == null || locale.isEmpty()) {
            return SystemConstant.DEFAULT_LOCALE;
        }
        return locale;
    }

    public static void setLocale(String locale) {
        put(SystemConstant.HEADER_LOCALE, locale);
    }

    public static TimeZone getTimeZone() {
        String zoneOffset = get(SystemConstant.HEADER_TIME_ZONE);
        if (StringUtils.isBlank(zoneOffset)) {
            return TimeZone.getDefault();
        }
        ZoneOffset offset = ZoneOffset.ofTotalSeconds(Integer.parseInt(zoneOffset) * 3600);
        return TimeZone.getTimeZone(offset);
    }

    public static void setTimeZone(String timeZone) {
        put(SystemConstant.HEADER_TIME_ZONE, timeZone);
    }

    public static String getClientIp() {
        return get(SystemConstant.HEADER_USER_IP);
    }

    public static void setClientIp(String clientIp) {
        put(SystemConstant.HEADER_USER_IP, clientIp);
    }

    public static void setUserAgent(String userAgent) {
        put(SystemConstant.HEADER_USER_AGENT, userAgent);
    }

    public static String getServerHost() {
        return get(SystemConstant.HEADER_SERVER_HOST);
    }

    public static void setServerHost(String serverHost) {
        put(SystemConstant.HEADER_SERVER_HOST, serverHost);
    }

    public static void removeServerHost() {
        getContextMap().remove(SystemConstant.HEADER_SERVER_HOST);
    }

    public static void clean() {
        contextMap.remove();
    }

    public static void clean(String key) {
        if (key == null) {
            log.error("key is null,can't remove");
            return;
        }
        Map<String, String> map = contextMap.get();
        if (map != null) {
            map.remove(convertKey(key));
        }
    }
}
