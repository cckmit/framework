package org.mickey.framework.common.exception;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.mickey.framework.common.dto.ErrorInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Data
public class BusinessException extends RuntimeException implements Serializable {
    private List<ErrorInfo> errors;

    public BusinessException(){
        super();
    }

    public BusinessException(List<ErrorInfo> errorInfoList) {
        super();
        this.errors = errorInfoList;
    }

    public BusinessException(List<ErrorInfo> errorInfoList, Throwable throwable) {
        super(throwable);
        this.errors = errorInfoList;
    }

    public BusinessException(ErrorInfo... errorInfo) {
        super();
        this.errors = new ArrayList<>();
        this.errors.addAll(Arrays.asList(errorInfo));
    }
    protected void addErrors(ErrorInfo... errorInfo) {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        this.errors.addAll(Arrays.asList(errorInfo));
    }

    @Override
    public String getMessage() {
        if (CollectionUtils.isNotEmpty(errors)) {
            StringBuilder builder = new StringBuilder();
            for (ErrorInfo errorInfo :
                    errors) {
                builder.append(errorInfo.getCode())
                        .append(":")
                        .append(errorInfo.getMessage())
                        .append("\n");
            }
            if (builder.length() > 0) {
                builder.deleteCharAt(builder.length() - 1);
            }
            return builder.toString();
        }
        return null;
    }
}
