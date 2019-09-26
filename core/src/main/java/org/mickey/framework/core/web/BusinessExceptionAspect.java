package org.mickey.framework.core.web;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.mickey.framework.common.dto.ActionResult;
import org.mickey.framework.common.exception.BusinessException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Aspect
@Component
public class BusinessExceptionAspect {

    @AfterThrowing(pointcut = "execution(* org.mickey.framework.example.api..*.*(..))", throwing = "ex")
    public void afterThrowing(Exception ex) {
        ex.printStackTrace();

        ActionResult actionResult;

        if (ex instanceof BusinessException) {
            BusinessException businessException = (BusinessException) ex;

            actionResult = new ActionResult<>(Boolean.FALSE, businessException.getErrors());
        } else {
            actionResult = new ActionResult(Boolean.FALSE, ex.getMessage());
        }

        ServletOutputStream outputStream = null;

        try {
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(417);
            outputStream = response.getOutputStream();
            outputStream.write(JSON.toJSONString(actionResult).getBytes("UTF-8"));
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
