package org.mickey.framework.filemanager.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.SpringUtils;
import org.mickey.framework.common.exception.BusinessException;
import org.mickey.framework.common.exception.ErrorProvider;
import org.mickey.framework.common.util.*;
import org.mickey.framework.filemanager.config.CosProperties;
import org.mickey.framework.filemanager.dto.PolicyRequestDto;
import org.mickey.framework.filemanager.dto.PolicyResultDto;
import org.mickey.framework.filemanager.po.OsFilePo;
import org.mickey.framework.filemanager.service.IFileCredentialService;
import org.mickey.framework.filemanager.service.IOsFileService;
import org.mickey.framework.filemanager.service.impl.OsFileServiceImpl;
import org.mickey.framework.filemanager.service.impl.TencentFileCredentialServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * JAVA后台操作文件上传及下载类
 *
 * @author mickey
 * 2020-02-14
 */
@Slf4j
public class FileClient {
    @Autowired
    public static CosProperties cosProperties;
//    @Resource
    private IFileCredentialService fileCredentialService;
//    @Resource
    private IOsFileService osFileService;

    public static FileClient fileClient;

    @PostConstruct
    public void init() {
        fileClient = SpringUtils.getBean(FileClient.class);

        cosProperties = SpringUtils.getBean(CosProperties.class);

        fileCredentialService = new TencentFileCredentialServiceImpl();
        osFileService = new OsFileServiceImpl();
    }

    public static String updateByteFileToTemp(String fileName, byte[] fileBytes) {
        String saveKey = "temp/" + fileName;
        String path = uploadByteFile(saveKey, fileName, fileBytes);

        return path;
    }

    public static String uploadByteFile(String path, String fileName, byte[] fileBytes) {
        return uploadFile(path, fileName, new ByteArrayInputStream(fileBytes));
    }

    public static String uploadFile(String path, String fileName, InputStream inputStream) {
        // region 获取临时密钥
        PolicyRequestDto policyRequestDto = new PolicyRequestDto();
        policyRequestDto.setAction("PutObject");
        PolicyResultDto policyResultDto = fileClient.fileCredentialService.getPolicy(policyRequestDto);
        // endregion

        // 用户基本信息:解析临时密钥中的相关信息
        String tmpSecretId = policyResultDto.getCredentials().getTmpSecretId();
        String tmpSecretKey = policyResultDto.getCredentials().getTmpSecretKey();
        String sessionToken = policyResultDto.getCredentials().getSessionToken();

        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(tmpSecretId, tmpSecretKey);
        // 2 设置 bucket 区域
        ClientConfig clientConfig = new ClientConfig(new Region(fileClient.cosProperties.getRegion()));
        // 3 生成 cos 客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        // bucket名需包含appid
        String bucketName = fileClient.cosProperties.getBucket();
        // 上传 object, 建议 20M 以下的文件使用该接口
        // 设置 x-cos-security-token header 字段
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setSecurityToken(sessionToken);
        String rtValue = null;
        try {
            int fileSize = FileUtil.getContentLength(inputStream);
            objectMetadata.setContentLength(fileSize);
            path = fileClient.fileCredentialService.getAllowPath(path);
            log.info("CosUtil.uploadFile.path is : " + path);
            PutObjectResult putObjectResult = cosclient.putObject(bucketName, path, inputStream, objectMetadata);
            log.info("FileClient.uploadFile.putObjectResult is : " + JSON.toJSONString(putObjectResult, SerializerFeature.PrettyFormat));
            // 成功：putobjectResult 会返回文件的 etag
            String etag = putObjectResult.getETag();
            rtValue = fileClient.cosProperties.getHttp() + fileClient.cosProperties.getHost() + path;

            OsFilePo osFilePo = new OsFilePo();
            osFilePo.setBucket(bucketName);
            osFilePo.setBucketFlag("COS");
            osFilePo.setEtag(etag);
            osFilePo.setFileName(fileName);
            osFilePo.setFileSize(Integer.toUnsignedLong(fileSize));
            osFilePo.setFilePath(fileClient.cosProperties.getHost() + path);
            osFilePo.setRegion(fileClient.cosProperties.getRegion());
            osFilePo.setUrl(rtValue);
            osFilePo.setServerName(fileName);
            osFilePo.setSuffix(FileUtil.getSuffix(fileName));
            osFilePo.setMediaType(MIMEUtil.getMediaType(osFilePo.getSuffix()));

            fileClient.osFileService.insert(osFilePo);
        } catch (CosServiceException e) {
            //失败，抛出 CosServiceException
            e.printStackTrace();
        } catch (CosClientException e) {
            //失败，抛出 CosClientException
            e.printStackTrace();
        } finally {
            // 关闭客户端
            cosclient.shutdown();
            //返回文件的网络访问url
            return rtValue;
        }
    }

    /**
     * 下载文件流
     *
     * @param url
     */
    public static byte[] downloadBytesFile(String url) throws Exception {
        log.info("CosUtil.downloadBytesFile.url is : " + url);
        HttpUtil.Response response = HttpUtil.get4InputStream(url, null, null);
        if (HttpStatusEnum.OK.equals(response.getStatusCode())) {
            return FileUtil.input2byte(response.getInputStream());
        } else {
            throw new BusinessException(ErrorProvider.DOWNLOAD_ERROR);
        }
    }

    public static String downloadBase64String(String url) throws Exception {
        return EncryptUtils.encryptBASE64(downloadBytesFile(url));
    }
}
