package org.mickey.framework.common.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

/**
 * 错误消息Bean
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Data
public class ErrorInfo implements Serializable {
    private boolean internationalized = false;
    private String code;
    private String message;
    private List<Object> arguments;
    private Exception exception;

    public ErrorInfo(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorInfo(Integer code, String message) {
        this.code = null != code ? code.toString() : null;
        this.message = message;
    }

    public ErrorInfo(String code, String message, List<Object> arguments) {
        this.code = code;
        this.message = message;
        this.arguments = arguments;
    }

    public ErrorInfo(Integer code, String message, List<Object> arguments) {
        this.code = null != code ? code.toString() : null;
        this.message = message;
        this.arguments = arguments;
    }

    public ErrorInfo(Integer code, String message, Exception ex) {
        this.code = null != code ? code.toString() : null;
        this.message = message;
        this.exception = ex;
    }
}
