package org.mickey.framework.core.config.feigin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.mickey.framework.common.dto.ActionResult;
import org.mickey.framework.common.dto.ErrorInfo;
import org.mickey.framework.common.dto.Resp;
import org.mickey.framework.common.exception.BusinessException;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Configuration
public class FeiginErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            String message = Util.toString(response.body().asReader());
                if (response.status() == HttpStatus.SC_EXPECTATION_FAILED) {
                    try {
                        ActionResult actionResult = JSON.parseObject(message, ActionResult.class);
                        return new BusinessException(new ErrorInfo(String.valueOf(-1), actionResult.getMessage()));
                    } catch (JSONException actionResultParseEx) {
                        try {
                            Resp actionResult = JSON.parseObject(message, Resp.class);
                            return new BusinessException(new ErrorInfo(String.valueOf(-1), actionResult.getMessage()));
                        }
                        catch(JSONException parseEx) {
                            parseEx.printStackTrace();
                        }
                    }
                }
                return new BusinessException(message);
        } catch (IOException ignored) {

        }
        return decode(methodKey, response);
    }
}
