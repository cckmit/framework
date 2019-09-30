package org.mickey.framework.core.web;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.mickey.framework.common.dto.ActionResult;
import org.mickey.framework.common.dto.ErrorInfo;
import org.mickey.framework.common.exception.BusinessException;
import org.springframework.stereotype.Component;

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

            actionResult = ActionResult.Errors(businessException.getErrors());
        } else {
            log.error("Exception Type is : {}, Exception Message is {};", ex.getClass().getTypeName(), ex.getMessage(), ex);
            actionResult = ActionResult.Errors(new ErrorInfo(10000, ex.getMessage()));
        }
    }
}
