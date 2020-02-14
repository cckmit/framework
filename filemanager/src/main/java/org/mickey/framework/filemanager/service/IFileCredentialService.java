package org.mickey.framework.filemanager.service;

import org.mickey.framework.filemanager.dto.PolicyRequestDto;
import org.mickey.framework.filemanager.dto.PolicyResultDto;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 身份识别服务
 *
 * @author mickey
 * 2020-02-12
 */
public interface IFileCredentialService {

    /**
     * 获取上传操作私钥（提供具体的action操作，如: putObject）
     * @param request
     * @return
     */
    PolicyResultDto getPolicy(@RequestBody PolicyRequestDto request);

    /**
     * 根据参数路径返回服务器允许操作的绝对路径
     * @param path
     * @return
     */
    String getAllowPath(String path);
}
