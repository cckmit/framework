package org.mickey.framework.filemanager.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 腾讯文件存储配置文件
 *
 * @author mickey
 * 2020-02-12
 */
@Component
@ConfigurationProperties(prefix = "tencent")
@Data
public class CosProperties {
    /**
     * 腾讯云的SecretId
     */
    private String secretId;
    /**
     * 腾讯云的SecretKey
     */
    private String secretKey;
    /**
     * 腾讯云的bucket (存储桶)
     */
    private String bucket;
    /**
     * 腾讯云的region(bucket所在地区)
     */
    private String region;
    /**
     * 腾讯云的allowPrefix(允许上传的路径)
     */
    private String rootFolder;
    /**
     * 腾讯云的临时密钥时长(单位秒)
     */
    private String durationSeconds;
    /**
     * 腾讯云的访问基础链接:
     */
    private String baseUrl;
    /**
     * http or https
     */
    private String http;
}
