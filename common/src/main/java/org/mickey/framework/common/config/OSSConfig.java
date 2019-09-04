package org.mickey.framework.common.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Component
@ConfigurationProperties(prefix = "oss.config")
@Data
public class OSSConfig {

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String rootFolder;
    private String bucketDomain;
    private String callbackUrl;

}
