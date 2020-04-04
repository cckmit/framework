package org.mickey.framework.filemanager.api;

import org.junit.Before;
import org.junit.Test;
import org.mickey.framework.common.dto.ActionResult;
import org.mickey.framework.common.util.FileUtil;
import org.mickey.framework.common.util.UUIDUtils;
import org.mickey.framework.core.test.BaseSpringTest;
import org.mickey.framework.filemanager.client.FileClient;
import org.mickey.framework.filemanager.dto.PolicyRequestDto;
import org.mickey.framework.filemanager.dto.PolicyResultDto;
import org.mickey.framework.filemanager.dto.UploadCallbackDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * description
 *
 * @author mickey
 * 2020-02-12
 */
public class FileControllerTest extends BaseSpringTest {

    @Autowired
    private FileCredentialController credentialController;
    @Autowired
    private FileUploadCallbackController callbackController;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void function(int max, String priceStr) {
        List<Integer> priceArr = parseToList(priceStr);
        // 从小到大排序
        priceArr.sort(Integer::compareTo);
        int money = 0;
        for (int i = 1; i < priceArr.size(); i++) {
            int next = priceArr.subList(0, i).stream().mapToInt(x -> x).sum();
            print(next);
            if (next > max)  {
                print(money);
                return;
            } else {
                money = next;
            }
        }
    }

    private List<Integer> parseToList(String priceStr) {
        String[] strings = priceStr.split(" ");
        List<Integer> priceArr = new ArrayList<>();
        for (String s: strings) {
            try {
                int parseInt = Integer.parseInt(s);
                priceArr.add(parseInt);
            } catch (NumberFormatException e) {
                System.out.println("输入的单价格式错误，请检查输入");
            }
        }
        return priceArr;
    }

    @Test
    public void getPolicy() {
        PolicyRequestDto requestDto = new PolicyRequestDto();
        requestDto.setAction("PutObject");
        ActionResult<PolicyResultDto> actionResult = credentialController.getPolicy(requestDto);

        print(actionResult);
    }

    @Test
    public void callback() {
        String fileId = UUIDUtils.getUuid();

        UploadCallbackDto uploadCallbackDto = new UploadCallbackDto();
        uploadCallbackDto.setBucket("asvinos001-1252065669");
        uploadCallbackDto.setEtag("FF808081702CFDCD01703483D0910013");
        uploadCallbackDto.setFileId(fileId);
        uploadCallbackDto.setFileName("WeChat Image_20200204154945.png");
        uploadCallbackDto.setFilePath("asvinos001-1252065669.cos.ap-chengdu.myqcloud.com/dev/haolun/null/95f05fe5621f4f299771759758ab7fb0/reportsource/20200211/FF808081702CFDCD01703483D0910013_WeChat%20Image_20200204154945.png");
        uploadCallbackDto.setMimeType("image/png");
        uploadCallbackDto.setSize(52353);
        uploadCallbackDto.setType(".png");

        ActionResult actionResult = callbackController.callback(uploadCallbackDto);

        print(actionResult);

        callbackController.delete(fileId);
    }

    @Test
    public void upload4Java() throws IOException {
        File file = new File("C:\\Users\\micke\\Pictures\\e2b_export_1.png");

        FileClient.updateByteFileToTemp("test_upload_java_file.png", FileUtil.getByteArray(file));
    }

    @Test
    public void downloadBasee64String() throws Exception {
        print(FileClient.downloadBase64String("https://asvinos001-1252065669.cos.ap-chengdu.myqcloud.com/dev/haolun/temp/test_upload_java_file.png"));
    }
}