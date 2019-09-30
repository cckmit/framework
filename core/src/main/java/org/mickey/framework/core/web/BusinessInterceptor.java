package org.mickey.framework.core.web;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.exception.BusinessException;
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

        log.error("BusinessInterceptor filter; response class is " + handler.getClass() + ", exception is :" + ex + ";", ex);
        if (ex != null) {
            if (ex instanceof BusinessException) {

            } else {
                ServletOutputStream outputStream = null;
                try {
                    response.setContentType(request.getContentType());
                    response.setStatus(HttpStatus.EXPECTATION_FAILED.value());
                    outputStream = response.getOutputStream();
                    outputStream.write(ex.getMessage().getBytes(request.getCharacterEncoding()));
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

    }
}
