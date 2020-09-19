package org.mickey.framework.job.service;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.mickey.framework.common.util.CollectionUtil;
import org.mickey.framework.common.util.FileUtil;
import org.mickey.framework.common.util.HttpStatusEnum;
import org.mickey.framework.common.util.HttpUtil;
import org.mickey.framework.job.dto.bingimage.JsonsRootBean;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;

/**
 * #Description
 *
 * @author wangmeng
 * @date 2020-06-11
 */
@Slf4j
public class BingImageJob implements Job {
    @Value("${file.folder}")
    private String folder;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        log.info("----------------------------------------------request bing image------------------------------------------------");

        try {
            requestBing();
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void requestBing() throws IOException {
        String baseUrl = "http://www.bing.com";
        String jsonUrl = baseUrl + "/HPImageArchive.aspx?format=js&idx=0&n=10";

        HttpUtil.Response<Object> objectResponse = HttpUtil.get(jsonUrl, null, null);
        if (objectResponse.getStatusCode() == HttpStatusEnum.OK) {
            JsonsRootBean jsonsRootBean = JSON.parseObject(objectResponse.getResult(), JsonsRootBean.class);
            if (jsonsRootBean != null) {
                CollectionUtil.forEach(jsonsRootBean.getImages(), image -> {
                    File file = FileUtils.getFile(folder + image.getEnddate() + ".jpg");
                    if (!file.exists()) {
                        log.info(String.format("file %s is not exists, downloading...", file.getName()));
                        HttpUtil.Response inputStream = null;
                        try {
                            inputStream = HttpUtil.get4InputStream(baseUrl + image.getUrl(), null, null);
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                        try {
                            FileUtils.writeByteArrayToFile(file, FileUtil.input2byte(inputStream.getInputStream()));
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                    }
                });
            }
        }
    }
}
