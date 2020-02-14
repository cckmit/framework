package org.mickey.framework.filemanager.service.impl;

import com.alibaba.fastjson.JSON;
import com.tencent.cloud.CosStsClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.mickey.framework.common.SpringUtils;
import org.mickey.framework.common.SystemContext;
import org.mickey.framework.common.util.DateUtils;
import org.mickey.framework.common.util.FileUtil;
import org.mickey.framework.common.util.UUIDUtils;
import org.mickey.framework.filemanager.config.CosProperties;
import org.mickey.framework.filemanager.dto.PolicyRequestDto;
import org.mickey.framework.filemanager.dto.PolicyResultDto;
import org.mickey.framework.filemanager.service.IFileCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.TreeMap;

/**
 * 腾讯文件存储 身份验证 实现
 *
 * @author mickey
 * 2020-02-12
 */
@Service
@Slf4j
public class TencentFileCredentialServiceImpl implements IFileCredentialService {

    @Autowired
    private CosProperties cosProperties;

    @PostConstruct
    public void init() {
         this.cosProperties = getCosProperties();
    }

    private CosProperties getCosProperties() {
        if (cosProperties == null) {
            cosProperties = SpringUtils.getBean(CosProperties.class);
        }
        return cosProperties;
    }

    @Override
    public PolicyResultDto getPolicy(PolicyRequestDto request) {
        cosProperties = getCosProperties();
        JSONObject credential = getCredential(request);

        PolicyResultDto policyResult = JSON.parseObject(credential.toString(), PolicyResultDto.class);
        policyResult.setDir(generatorFilePath());
        policyResult.setFileId(UUIDUtils.getUuid());
        log.info("getPolicy parseObject : " + policyResult);
        return policyResult;
    }

    private JSONObject getCredential(PolicyRequestDto req) {
        String allowPrefix = getAllowPrefix(cosProperties.getRootFolder());

        TreeMap<String, Object> config = new TreeMap<String, Object>();
        try {
            config.put("SecretId", cosProperties.getSecretId());
            config.put("SecretKey", cosProperties.getSecretKey());
            config.put("durationSeconds", Integer.parseInt(cosProperties.getDurationSeconds()));
            config.put("bucket", cosProperties.getBucket());
            config.put("region", cosProperties.getRegion());
            config.put("allowPrefix", allowPrefix);
            //密钥的权限列表，其他权限列表请看
            //https://cloud.tencent.com/document/product/436/31923
            if (StringUtils.isBlank(req.getAction())) {
                config.put("allowActions", getAllowActions());
            } else {
                config.put("allowActions", new String[] { "name/cos:" + req.getAction()});
            }

            JSONObject credential = CosStsClient.getCredential(config);
            //成功返回临时密钥信息，如下打印密钥信息
            log.info(JSON.toJSONString(credential));
            return credential;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            //失败抛出异常
            throw new IllegalArgumentException("no valid secret for tencent !");
        }
    }

    private String getAllowPrefix(String rootFolder) {
        if (!equalsLastCharAndFileSeparator()) {
            rootFolder += FileUtil.fileSeparator;
        }
        return rootFolder + "*";
    }

    @Override
    public String getAllowPath(String path) {
        cosProperties = getCosProperties();
        String rootFolder = cosProperties.getRootFolder();
        if (!equalsLastCharAndFileSeparator()) {
            rootFolder += FileUtil.fileSeparator;
        }
        return rootFolder + path;
    }

    /**
     * 判断root folder配置的结尾是否包含文件夹分隔符("/")
     * @return
     */
    private boolean equalsLastCharAndFileSeparator () {
        String rootFolder = cosProperties.getRootFolder();
        String lastChar = StringUtils.substring(rootFolder, rootFolder.length() - 1, rootFolder.length());
        return StringUtils.equals(FileUtil.fileSeparator, lastChar);
    }

    private String[] getAllowActions() {
        String[] allowActions = new String[]{
                //简单上传操作
                "name/cos:PutObject",
                //表单上传对象
                "name/cos:PostObject",
                //分块上传：初始化分块操作
                "name/cos:InitiateMultipartUpload",
                //分块上传：List 已上传分块操作
                "name/cos:ListParts",
                //分块上传：上传分块块操作
                "name/cos:UploadPart",
                //分块上传：完成所有分块上传操作
                "name/cos:CompleteMultipartUpload",
                //取消分块上传操作
                "name/cos:AbortMultipartUpload"
        };
        return allowActions;
    }

    /**
     * OSS保存路径 ROOT/COMPANY_ID/APP_ID/USERID/
     *
     * @return
     */
    private String generatorFilePath() {
        StringBuilder builder = new StringBuilder();
        builder.append(SystemContext.getTenantId())
                .append(FileUtil.fileSeparator).append(SystemContext.getAppId())
                .append(FileUtil.fileSeparator).append(DateUtils.getYear(DateUtils.getCurrentDate()))
                .append(FileUtil.fileSeparator).append(DateUtils.getMonth(DateUtils.getCurrentDate()))
                .append(FileUtil.fileSeparator).append(DateUtils.getDay(DateUtils.getCurrentDate()))
                .append(FileUtil.fileSeparator);
        return getAllowPath(builder.toString());
    }
}
