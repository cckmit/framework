package org.mickey.framework.common.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.http.HttpStatus;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Data
public class ActionResult<T> implements Serializable {

    public static ActionResult ok() {
        return new ActionResult(Boolean.TRUE, HttpStatus.SC_OK);
    }

    public static <T> ActionResult<T> ok(T obj) {
        return new ActionResult<>(Boolean.TRUE, HttpStatus.SC_OK, obj);
    }

    public static ActionResult created() {
        return new ActionResult(Boolean.TRUE, HttpStatus.SC_CREATED);
    }

    public static ActionResult updated() {
        return new ActionResult(Boolean.TRUE, HttpStatus.SC_NO_CONTENT);
    }

    public static ActionResult errors(ErrorInfo errorInfo) {
        return new ActionResult<>(errorInfo);
    }

    public static ActionResult errors(List<ErrorInfo> errors) {
        return new ActionResult<>(errors);
    }
    
    
    private int httpStatus;
    private int code = 0;
    private boolean success;
    private String message;
    /**
     * 错误消息
     */
    private List<ErrorInfo> errors;
    /**
     * ll
     * 返回数据
     */
    private T data;

    public ActionResult() {
        super();
    }

    private ActionResult(Boolean success, int httpStatus) {
        fillSuccessAndCode(success);
        this.httpStatus = httpStatus;
    }

    private ActionResult(Boolean success, int httpStatus, T obj) {
        fillSuccessAndCode(success);
        this.httpStatus = httpStatus;
        this.data = obj;
    }

    private ActionResult(ErrorInfo errorInfo) {
        fillSuccessAndCode(Boolean.FALSE);
        this.httpStatus = HttpStatus.SC_EXPECTATION_FAILED;
        this.errors = Collections.singletonList(errorInfo);
    }

    private ActionResult(List<ErrorInfo> errors) {
        fillSuccessAndCode(Boolean.FALSE);
        this.httpStatus = HttpStatus.SC_EXPECTATION_FAILED;
        this.errors = errors;
    }

    /**
     * 为了兼容旧的前端代码，将success与code同步下值
     *
     * @param success boolean success
     */
    private void fillSuccessAndCode(Boolean success) {
        this.success = success;
        if (BooleanUtils.isTrue(success)) {
            this.code = 0;
        } else {
            this.code = 1;
        }
    }
}
