package org.mickey.framework.common;

import java.util.Locale;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
public interface SystemConstant {
    String GLOBAL_NS = "org.mickey";
    String DOT = ".";
    String FRAMEWORK_NS = GLOBAL_NS + DOT + "framework";

    String INTERNATIONALIZED_MSG_CODE = "__internationalized__";

    String DEFAULT_TENANT_ID = "default";

    String DEFAULT_PROJECT_ID = "default";

    String I18N_GLOBAL_ID = "global";

    String I18N_GLOBAL_NAME = "全局";

    String SERVER_ERROR = "500";
    String SERVER_ERROR_TEXT = "server error";

    Integer TRUE = 1;
    Integer FALSE = 0;

    String DEFAULT_LOCALE = Locale.SIMPLIFIED_CHINESE.toString();
    //********************filter order******************************//
    int FilterOrder = Integer.MIN_VALUE;

    /*************** common header ********************/
    String CONTEXT_HEADER_PREFIX = "MIC-Header-";
    String HEADER_TOKEN = CONTEXT_HEADER_PREFIX + "Token";
    String ENVIRONMENT_TOKEN = CONTEXT_HEADER_PREFIX + "Environment-Token";

    String HEADER_USER_ID = CONTEXT_HEADER_PREFIX + "UserId";

    String HEADER_ACCOUNT_ID = CONTEXT_HEADER_PREFIX + "AccountId";

    String HEADER_ACCOUNT_NAME = CONTEXT_HEADER_PREFIX + "AccountName";

    String HEADER_USER_NAME = CONTEXT_HEADER_PREFIX + "UserName";

    String HEADER_USER_IP = CONTEXT_HEADER_PREFIX + "UserIp";

    String HEADER_USER_AGENT = CONTEXT_HEADER_PREFIX + "UserAgent";

    String HEADER_TENANT_ID = CONTEXT_HEADER_PREFIX + "TenantId";

    String HEADER_PROJECT_ID = CONTEXT_HEADER_PREFIX + "ProjectId";

    String HEADER_APP_ID = CONTEXT_HEADER_PREFIX + "AppId";

    String HEADER_LOCALE = CONTEXT_HEADER_PREFIX + "Locale";

    String HEADER_TIME_ZONE = CONTEXT_HEADER_PREFIX + "TimeZone";

    String HEADER_SERVER_HOST = CONTEXT_HEADER_PREFIX + "ServerHost";

    String HEADER_PLATFORM = CONTEXT_HEADER_PREFIX + "Platform";

    String HEADER_DEVICE = CONTEXT_HEADER_PREFIX + "Device";

    String HEADER_TRACE_ID = CONTEXT_HEADER_PREFIX + "TraceId";

    String HEADER_NODE_ID = CONTEXT_HEADER_PREFIX + "NodeId";


    String APOLLO_BOOTSTRAP_PROPERTY_SOURCE_NAME = "ApolloBootstrapPropertySources";


    String CONFIG_CENTER_ENABLED = "com.mickey.configCenter.enabled";

    /**
     * local properties file path for using ConfigService(see com.ctrip.framework.apollo.internals.DefaultConfig)
     * */
    String CONFIG_CENTER_LOCAL_PROPERTIES = "com.mickey.configCenter.localProperties";

    String TRACE_LOGGER_NAME = "traceLogger";

    static String tokenName() {
        return "token";
    }
}
