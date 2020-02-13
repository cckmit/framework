package org.mickey.framework.filemanager.service.impl;

import org.mickey.framework.common.SystemContext;
import org.mickey.framework.common.util.BeanUtil;
import org.mickey.framework.filemanager.config.CosProperties;
import org.mickey.framework.filemanager.dto.OsFileDto;
import org.mickey.framework.filemanager.dto.UploadCallbackDto;
import org.mickey.framework.filemanager.po.OsFilePo;
import org.mickey.framework.filemanager.service.IFileCallbackService;
import org.mickey.framework.filemanager.service.IOsFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description
 *
 * @author mickey
 * 2020-02-12
 */
@Service
public class FileCallbackServiceImpl implements IFileCallbackService {
    @Autowired
    private CosProperties cosProperties;
    @Autowired
    private IOsFileService osFileService;

    private String bucketFlag = "COS";

    @Override
    public void callback(UploadCallbackDto callbackDto) {
        OsFileDto osFileDto = new OsFileDto();
        // id 为获取私钥时，随机生成的文件的唯一标识
        osFileDto.setId(callbackDto.getFileId());
        osFileDto.setBucket(callbackDto.getBucket());
        osFileDto.setBucketFlag(bucketFlag);
        osFileDto.setEtag(callbackDto.getEtag());
        osFileDto.setFilePath(callbackDto.getFilePath());
        osFileDto.setFileName(callbackDto.getFileName());
        osFileDto.setMediaType(callbackDto.getMimeType());
        osFileDto.setFileSize(callbackDto.getSize());
        osFileDto.setSuffix(callbackDto.getType());
        osFileDto.setUrl(cosProperties.getHttp() + callbackDto.getFilePath());
        osFileDto.setRegion(cosProperties.getRegion());

//        osFileDto.setTenantId(SystemContext.getTenantId());

        osFileService.insert(BeanUtil.convert(osFileDto, OsFilePo.class));
    }

    @Override
    public void delete(String id) {
        osFileService.delete(id);
    }
}
