package org.mickey.framework.example.service.file;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.mickey.framework.common.util.FileUtil;
import org.mickey.framework.common.util.UUIDUtils;
import org.mickey.framework.core.test.BaseSpringTest;
import org.mickey.framework.filemanager.client.FileClient;

import java.io.File;
import java.io.IOException;

/**
 * description
 *
 * @author mickey
 * 2020-02-14
 */
@Slf4j
public class FileServiceTest extends BaseSpringTest {

    String fileName = UUIDUtils.getUuid();

    @Test
    public void upload4Java() throws IOException {
        File file = new File("C:\\Users\\micke\\Pictures\\e2b_export_1.png");

        FileClient.updateByteFileToTemp(fileName + ".png", FileUtil.getByteArray(file));
    }

    @Test
    public void downloadBasee64String() throws Exception {
        print(FileClient.downloadBase64String("https://asvinos001-1252065669.cos.ap-chengdu.myqcloud.com/dev/haolun/temp/" + fileName + ".png"));
    }
}
