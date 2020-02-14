package org.mickey.framework.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.io.*;

/**
 * description
 *
 * @author mickey
 * 2020-02-12
 */
@Slf4j
public class FileUtil {
    public static final String fileSeparator = "/";
    public static final String fileDot = ".";

    public static String encryptBASE64(byte[] data) {
        return Base64.encodeBase64String(data).replaceAll("[\\s*\t\n\r]", "");
    }

    public static byte[] decryptBASE64(String data) throws Exception {
        return Base64.decodeBase64(data);
    }

    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            return file.delete();
        } else {
            log.warn("The file is not exists");
            return false;
        }
    }

    public static byte[] input2byte(InputStream inStream) throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }

    public static int getContentLength(InputStream inputStream) throws IOException {
        int count = 0;
        while (count == 0) {
            count = inputStream.available();
        }
        return count;
    }

    public static byte[] getByteArray(File file) throws IOException {
        InputStream input = new FileInputStream(file);

        byte[] byt = new byte[input.available()];

        input.read(byt);

        return byt;
    }

    public static String getSuffix(String fileName) {
        if (StringUtil.contains(fileName, fileDot)) {
            String[] split = StringUtil.split(fileName, fileDot);
            return fileDot + split[split.length - 1];
        }
        return "";
    }
}
