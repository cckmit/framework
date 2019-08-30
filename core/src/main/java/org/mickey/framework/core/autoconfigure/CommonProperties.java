package org.mickey.framework.core.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.SystemConstant;
import org.mickey.framework.core.ThreadPoolProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@ConfigurationProperties(prefix = SystemConstant.FRAMEWORK_NS)
public class CommonProperties {

    /**
     * 是否打开唯一索引
     */
    private boolean enableDbUniqueCheck = false;
    /**
     * 是否加上context过滤器
     */
    private boolean contextFilter = true;
    /**
     * contextFilter过滤pattern
     */
    private String contextFilterPattern = "*";
    /**
     * contextFilter过滤pattern
     */
    private String contextFilterAppId = SystemConstant.I18N_GLOBAL_ID;
    /**
     * 是否加上返回结果自动包装
     */
    private boolean autoWrapResponse = false;
    /**
     * zk地址
     */
    private String zkAddress;
    /**
     * 映射器配置文件路径
     */
    private String mapperConfigLocations = "";
    /**
     * 线程池配置
     */
    private ThreadPoolProperties threadPool = new ThreadPoolProperties();

    public boolean isAutoWrapResponse() {
        return autoWrapResponse;
    }

    public void setAutoWrapResponse(boolean autoWrapResponse) {
        this.autoWrapResponse = autoWrapResponse;
    }

    public boolean isEnableDbUniqueCheck() {
        return enableDbUniqueCheck;
    }

    public void setEnableDbUniqueCheck(boolean enableDbUniqueCheck) {
        this.enableDbUniqueCheck = enableDbUniqueCheck;
    }

    public boolean isContextFilter() {
        return contextFilter;
    }

    public void setContextFilter(boolean contextFilter) {
        this.contextFilter = contextFilter;
    }

    public String getContextFilterPattern() {
        return contextFilterPattern;
    }

    public void setContextFilterPattern(String contextFilterPattern) {
        this.contextFilterPattern = contextFilterPattern;
    }

    public String getZkAddress() {
        return zkAddress;
    }

    public void setZkAddress(String zkAddress) {
        this.zkAddress = zkAddress;
    }

    public ThreadPoolProperties getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(ThreadPoolProperties threadPool) {
        this.threadPool = threadPool;
    }

    public String getMapperConfigLocations() {
        return mapperConfigLocations;
    }

    public void setMapperConfigLocations(String mapperConfigLocations) {
        this.mapperConfigLocations = mapperConfigLocations;
    }

    public String getContextFilterAppId() {
        return contextFilterAppId;
    }

    public void setContextFilterAppId(String contextFilterAppId) {
        this.contextFilterAppId = contextFilterAppId;
    }
}
