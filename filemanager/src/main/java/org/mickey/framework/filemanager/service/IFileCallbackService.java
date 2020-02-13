package org.mickey.framework.filemanager.service;

import org.mickey.framework.filemanager.dto.UploadCallbackDto;

/**
 * 文件上传 回调接口
 *
 * @author mickey
 * 2020-02-12
 */
public interface IFileCallbackService {

    /**
     * 文件上传 回调接口
     */
    void callback(UploadCallbackDto callbackDto);

    void delete(String id);
}
