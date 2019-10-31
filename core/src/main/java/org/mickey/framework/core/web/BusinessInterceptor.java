package org.mickey.framework.core.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mickey.framework.common.dto.ErrorInfo;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Component
public class BusinessInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        log.info("postHandle ModelAndView is : {};", modelAndView);

    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

        log.error("BusinessInterceptor filter; exception is :" + ex + ";", ex);
        if (ex != null) {
            if (ex instanceof DataIntegrityViolationException) {
                //字段 值过长
                String message = ex.getMessage();
                if(StringUtils.contains(message,"Data too long for column")){
                    String tooLongMsg = ex.getCause().getMessage();
                    String[] split = tooLongMsg.split("'");
                    if(split.length>2){
                        String fieldName = split[1];

                        log.info("fieldName is {};", fieldName);

                        String showMsg = "字段["+fieldName+"]内容超出最大长度限制";
                        ErrorInfo errorInfo = new ErrorInfo(-1, showMsg, ex);
                        writeResponse(request, response, errorInfo);
                    }
                } else {
                    writeResponse(request, response, new ErrorInfo(-1, ex.getMessage(), ex));
                }
            } else {
                ErrorInfo errorInfo = new ErrorInfo(-1, ex.getMessage(), ex);
                writeResponse(request, response, errorInfo);
            }
        }
    }

    private void writeResponse(HttpServletRequest request, HttpServletResponse response, ErrorInfo errorInfo) {
        ServletOutputStream outputStream = null;
        try {
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpStatus.EXPECTATION_FAILED.value());

            outputStream = response.getOutputStream();
            log.error("BusinessInterceptor writeResponse errorInfo is : " + JSON.toJSONString(errorInfo, SerializerFeature.WriteMapNullValue));
            outputStream.write(JSON.toJSONString(errorInfo, SerializerFeature.WriteMapNullValue).getBytes("UTF-8"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
