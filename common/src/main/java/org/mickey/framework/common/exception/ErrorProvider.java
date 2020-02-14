package org.mickey.framework.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.dto.ErrorInfo;

/**
 * description
 *
 * @author mickey
 * 2020-02-14
 */
@Slf4j
public class ErrorProvider {
    public static final ErrorInfo HTTP_EXCEPTION = new ErrorInfo("HTTP_EXCEPTION", "调用第三方服务出错");
    public static final ErrorInfo DOWNLOAD_ERROR = new ErrorInfo("DOWNLOAD_ERROR", "下载失败");

}
