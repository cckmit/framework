package org.mickey.framework.filemanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 私钥请求体
 *
 * @author mickey
 * 2020-02-12
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PolicyRequestDto {
    /**
     * action 操作（用于精确控制到接口）
     */
    private String action;
}
