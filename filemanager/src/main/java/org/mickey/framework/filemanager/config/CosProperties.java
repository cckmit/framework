package org.mickey.framework.filemanager.config;

import lombok.Data;
import org.mickey.framework.common.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 腾讯文件存储配置文件
 *
 * @author mickey
 * 2020-02-12
 */
@Component
//@ConfigurationProperties(prefix = "tencent")
@Data
public class CosProperties {
    /**
     * 腾讯云的SecretId
     */
    @Value("${tencent.SecretId}")
    private String secretId;
    /**
     * 腾讯云的SecretKey
     */
    @Value("${tencent.SecretKey}")
    private String secretKey;
    /**
     * 腾讯云的bucket (存储桶)
     */
    @Value("${tencent.bucket}")
    private String bucket;
    /**
     * 腾讯云的region(bucket所在地区)
     */
    @Value("${tencent.region}")
    private String region;
    /**
     * 腾讯云的allowPrefix(允许上传的路径)
     */
    @Value("${tencent.rootFolder}")
    private String rootFolder;
    /**
     * 腾讯云的临时密钥时长(单位秒)
     */
    @Value("${tencent.durationSeconds}")
    private String durationSeconds;
    /**
     * 腾讯云的访问基础链接:
     */
    @Value("${tencent.host}")
    private String host;
    /**
     * http or https
     */
    @Value("${tencent.http}")
    private String http;
}
