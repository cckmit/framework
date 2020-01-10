package org.mickey.framework.common.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.config.OssConfig;
import org.mickey.framework.common.config.OssFileTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * aliyun OSS util
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Component
public class OssUtil {

    @Autowired
    private OssConfig ossConfig;

    public static OssUtil ossUtil;

    @PostConstruct
    public void init() {
        ossUtil = this;
    }

    /**
     * 获取OSSClient实例
     *
     * @return
     */
    private static OSSClient getOssClient() {
        OSSClient ossClient = new OSSClient(ossUtil.ossConfig.getEndpoint(), ossUtil.ossConfig.getAccessKeyId(), ossUtil.ossConfig.getAccessKeySecret());
        return ossClient;
    }

    /**
     * 获取key
     *
     * @param key
     * @param containRoot
     * @return
     */
    private static String getKey(String key, boolean containRoot) {
        String returnKey = key;
        if (containRoot) {
            returnKey = ossUtil.ossConfig.getRootFolder() + key;
        }
        return returnKey;
    }

    /**
     * 获取Key访问路径
     *
     * @param key
     * @return
     */
    public static String getFullKey(String key, boolean containDomain) {
        if (containDomain) {
            return ossUtil.ossConfig.getBucketDomain() + "/" + key;
        } else {
            return key;
        }
    }

    /**
     * 上传本地文件
     *
     * @param filePath
     * @param saveKey
     * @throws Exception
     */
    public static String uploadFile(String filePath, String saveKey) throws Exception {
        return uploadFile(filePath, saveKey, true);
    }

    /**
     * 上传本地文件
     *
     * @param filePath
     * @param saveKey
     * @param containRoot
     * @return
     * @throws Exception
     */
    public static String uploadFile(String filePath, String saveKey, boolean containRoot) throws Exception {
        return uploadFile(filePath, saveKey, containRoot, false);
    }

    /**
     * 上传本地文件
     *
     * @param filePath
     * @param saveKey
     * @param containRoot
     * @param containDomain
     * @return
     * @throws Exception
     */
    public static String uploadFile(String filePath, String saveKey, boolean containRoot, boolean containDomain) throws Exception {
        // 创建OSSClient实例。
        OSSClient ossClient = getOssClient();
        //上传文件流。
        InputStream inputStream = new FileInputStream(filePath);
        String key = getKey(saveKey, containRoot);
        ossClient.putObject(ossUtil.ossConfig.getBucketName(), key, inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();
        return getFullKey(key, containDomain);
    }

    /**
     * 上传文件流
     *
     * @param saveKey
     * @throws Exception
     */
    public static String uploadFile(String saveKey, InputStream inputStream) {
        return uploadFile(saveKey, inputStream, true);
    }

    /**
     * 上传文件流
     *
     * @param saveKey
     * @param inputStream
     * @param containRoot
     * @return
     * @throws Exception
     */
    public static String uploadFile(String saveKey, InputStream inputStream, boolean containRoot) {
        return uploadFile(saveKey, inputStream, containRoot, false);
    }

    /**
     * 上传文件流
     *
     * @param saveKey
     * @param inputStream
     * @param containRoot
     * @param containDomain
     * @return
     * @throws Exception
     */
    public static String uploadFile(String saveKey, InputStream inputStream, boolean containRoot, boolean containDomain) {
        // 创建OSSClient实例。
        OSSClient ossClient = getOssClient();
        //上传文件流。
        String key = getKey(saveKey, containRoot);
        ossClient.putObject(ossUtil.ossConfig.getBucketName(), key, inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();

        return getFullKey(key, containDomain);
    }

    /**
     * 根据条件列举key
     *
     * @param searchKey
     * @param containSearch
     * @return
     */
    public static List<String> listObjects(String searchKey, boolean containSearch) {
        return listObjects(searchKey, containSearch, true);
    }

    public static List<String> listObjects(String searchKey, boolean containSearch, boolean containRoot) {
        List<String> keyList = new ArrayList<>();
        // 创建OSSClient实例
        OSSClient ossClient = getOssClient();
        // 设置最大个数。
        final int maxKeys = 200;
        // 列举指定marker之后的文件
        ListObjectsRequest objReq = new ListObjectsRequest(ossUtil.ossConfig.getBucketName());
        objReq.withMaxKeys(maxKeys);

        String key = getKey(searchKey, containRoot);
        if (containSearch) {
            objReq.withPrefix(key);
        } else {
            objReq.withMarker(key);
        }
        ObjectListing objectListing = ossClient.listObjects(objReq);
        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();

        for (OSSObjectSummary s : sums) {
            keyList.add(s.getKey());
        }
        // 关闭OSSClient。
        ossClient.shutdown();

        return keyList;
    }

    /**
     * 列举key
     *
     * @return
     */
    public static List<String> listObjects() {
        return listObjects("", false);
    }

    /**
     * key是否存在
     *
     * @param key
     * @return
     */
    public static boolean hasKey(String key) {
        return hasKey(key, true);
    }

    /**
     * key是否存在
     *
     * @param key
     * @param containRoot
     * @return
     */
    public static boolean hasKey(String key, boolean containRoot) {
        // 创建OSSClient实例
        OSSClient ossClient = getOssClient();

        // 判断文件是否存在。doesObjectExist还有一个参数isOnlyInOSS，如果为true则忽略302重定向或镜像；如果为false，则考虑302重定向或镜像。
        boolean found = ossClient.doesObjectExist(ossUtil.ossConfig.getBucketName(), getKey(key, containRoot));

        // 关闭OSSClient。
        ossClient.shutdown();

        return found;
    }

    /**
     * 删除key
     *
     * @param key
     */
    public static void deleteKey(String key) {
        deleteKey(key, true);
    }

    /**
     * 删除key
     *
     * @param key
     * @param containRoot
     */
    public static void deleteKey(String key, boolean containRoot) {
        // 创建OSSClient实例
        OSSClient ossClient = getOssClient();

        // 删除文件
        ossClient.deleteObject(ossUtil.ossConfig.getBucketName(), getKey(key, containRoot));

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * 下载文件到本地
     *
     * @param key
     * @param localFilePath
     */
    public static void downloadFile(String key, String localFilePath) {
        downloadFile(key, localFilePath, true);
    }

    /**
     * 下载文件到本地
     *
     * @param key
     * @param localFilePath
     * @param containRoot
     */
    public static void downloadFile(String key, String localFilePath, boolean containRoot) {
        // 创建OSSClient实例。
        OSSClient ossClient = getOssClient();

        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(ossUtil.ossConfig.getBucketName(), getKey(key, containRoot)), new File(localFilePath));

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * 下载文件流
     *
     * @param key
     * @return
     */
    public static void downloadFile(String key, OutputStream outStream) {
        downloadFile(key, outStream, true);
    }

    /**
     * 下载文件流
     *
     * @param key
     * @param outStream
     */
    public static void downloadFile(String key, OutputStream outStream, boolean containRoot) {
        // 创建OSSClient实例。
        OSSClient ossClient = getOssClient();

        // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
        OSSObject ossObject = ossClient.getObject(ossUtil.ossConfig.getBucketName(), getKey(key, containRoot));

        InputStream input = ossObject.getObjectContent();
        try {
            //缓冲文件输出流
            BufferedOutputStream outputStream = new BufferedOutputStream(outStream);
            byte[] car = new byte[1024];
            int l;
            while ((l = input.read(car)) != -1) {
                if (car.length != 0) {
                    outputStream.write(car, 0, l);
                }
            }
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }
    }

    /**
     * 下载文件流
     *
     * @param key
     */
    public static byte[] downloadBytesFile(String key) throws Exception {
        // 创建OSSClient实例。
        OSSClient ossClient = getOssClient();

        // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
        OSSObject ossObject = ossClient.getObject(ossUtil.ossConfig.getBucketName(), key);
        InputStream input = ossObject.getObjectContent();
        ByteArrayOutputStream dstStream = new ByteArrayOutputStream();
        //缓冲文件输出流
        BufferedOutputStream outputStream = new BufferedOutputStream(dstStream);
        byte[] car = new byte[1024];
        int l;
        while ((l = input.read(car)) != -1) {
            if (car.length != 0) {
                outputStream.write(car, 0, l);
            }
        }
        if (outputStream != null) {
            outputStream.flush();
            outputStream.close();
        }
        // 关闭OSSClient。
        ossClient.shutdown();

        return dstStream.toByteArray();
    }

    /**
     * 下载base64文件流
     *
     * @param key
     */
    public static byte[] downloadBase64BytesFile(String key) throws Exception {
        byte[] bytes = downloadBytesFile(key);
        return Base64.getEncoder().encode(bytes);
    }


    /**
     * 上传byte文件
     *
     * @param saveKey
     * @param fileBytes
     * @return
     * @throws Exception
     */
    public static String uploadByteFile(String saveKey, byte[] fileBytes) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(fileBytes);
        // 创建OSSClient实例。
        OSSClient ossClient = getOssClient();
        //上传文件流。
        String key = getKey(saveKey, true);
        ossClient.putObject(ossUtil.ossConfig.getBucketName(), key, inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();
        return key;
    }

    /**
     * 上传byte文件
     *
     * @param saveKey
     * @param fileBytes
     * @return
     * @throws Exception
     */
    public static String uploadByteFileToKey(String saveKey, byte[] fileBytes) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(fileBytes);
        // 创建OSSClient实例。
        OSSClient ossClient = getOssClient();
        //上传文件流。
        String key = getKey(saveKey, false);
        ossClient.putObject(ossUtil.ossConfig.getBucketName(), key, inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();
        return key;
    }

    /**
     * 上传bytes文件到temp目录
     *
     * @param fileName
     * @param fileBytes
     * @return
     */
    public static String updateByteFileToTemp(String fileName, byte[] fileBytes) {
        String saveKey = "/temp/" + fileName;
        String key = uploadByteFile(saveKey, fileBytes);
        return getFullKey(key, true);
    }

    public static String generatorFilePath(String companyId, OssFileTypeEnum typeEnum) {
        String root = ossUtil.ossConfig.getRootFolder();
        String curDate = DateUtils.format(DateUtils.getCurrentDate(), "yyyyMMdd");
        StringBuilder builder = new StringBuilder();
        builder.append(root)
                .append("/").append(companyId)
                .append("/").append(typeEnum.getPath())
                .append("/").append(curDate)
                .append("/");
        return builder.toString();
    }
}

