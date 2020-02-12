package org.mickey.framework.filemanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 私钥返回体
 *
 * @author mickey
 * 2020-02-12
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PolicyResultDto {
    private credential credentials;
    private String requestId;
    private String expiration;
    private Long startTime;
    private Long expiredTime;

    private String dir;
    private String fileId;

    @Data
    public static class credential {
        private String tmpSecretId;
        private String tmpSecretKey;
        private String sessionToken;
    }
}
