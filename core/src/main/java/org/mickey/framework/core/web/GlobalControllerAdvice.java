package org.mickey.framework.core.web;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.dto.ActionResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@ControllerAdvice(annotations = {RestController.class, Controller.class})
public class GlobalControllerAdvice implements ResponseBodyAdvice<Object> {


    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return methodParameter.getMethod().getReturnType().equals(ActionResult.class);
    }

    @Override
    public Object beforeBodyWrite(Object object, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        log.error("GlobalControllerAdvice methodParameter is : {} ;", methodParameter.getGenericParameterType());

        if (methodParameter.getMethod().getReturnType().equals(ActionResult.class)) {

            ActionResult actionResult = (ActionResult)object;

            serverHttpResponse.setStatusCode(actionResult.getHttpStatus());
            serverHttpResponse.getHeaders().set("Content-Type", "application/json;charset=utf-8");

            if (actionResult.getHttpStatus().value() >200 && actionResult.getHttpStatus().value() <300) {
                return null;
            }
            return actionResult;
        } else {
            return object;
        }
    }
}
