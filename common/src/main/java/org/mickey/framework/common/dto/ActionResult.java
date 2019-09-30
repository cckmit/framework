package org.mickey.framework.common.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.mickey.framework.common.util.StringUtil;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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

    public static ActionResult Ok() {
        return new ActionResult(Boolean.TRUE, HttpStatus.OK);
    }

    public static <T> ActionResult<T> Ok(T obj) {
        return new ActionResult<>(Boolean.TRUE, HttpStatus.OK, obj);
    }

    public static ActionResult Created() {
        return new ActionResult(Boolean.TRUE, HttpStatus.CREATED);
    }

    public static ActionResult Updated() {
        return new ActionResult(Boolean.TRUE, HttpStatus.NO_CONTENT);
    }

    public static ActionResult Errors(ErrorInfo errorInfo) {
        return new ActionResult<>(errorInfo);
    }

    public static ActionResult Errors(List<ErrorInfo> errors) {
        return new ActionResult<>(errors);
    }

    private HttpStatus httpStatus;

    private int code = 0;
    private boolean success;
    private String message;
    /**
     * 错误消息
     */
    private List<ErrorInfo> errors;
    /**ll
     * 返回数据
     */
    private T data;


    private ActionResult(Boolean success, HttpStatus httpStatus) {
        fillSuccessAndCode(success);
        this.httpStatus = httpStatus;
    }

    private ActionResult(Boolean success, HttpStatus httpStatus, T obj) {
        fillSuccessAndCode(success);
        this.httpStatus = httpStatus;
        this.data = obj;
    }

    private ActionResult(ErrorInfo errorInfo) {
        fillSuccessAndCode(Boolean.FALSE);
        this.httpStatus = HttpStatus.EXPECTATION_FAILED;
        this.errors = Arrays.asList(errorInfo);
    }

    private ActionResult(List<ErrorInfo> errors) {
        fillSuccessAndCode(Boolean.FALSE);
        this.httpStatus = HttpStatus.EXPECTATION_FAILED;
        this.errors = errors;
    }

    /**
     * 为了兼容旧的前端代码，将success与code同步下值
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

//    @Data
//    public class Result<T> {
//        private HttpStatus httpStatus;
//
//        private int code = 0;
//        private boolean success;
//        private String message;
//        /**
//         * 错误消息
//         */
//        private List<ErrorInfo> errors;
//        /**ll
//         * 返回数据
//         */
//        private T data;
//
//
//        private Result(Boolean success, HttpStatus httpStatus) {
//            fillSuccessAndCode(success);
//            this.httpStatus = httpStatus;
//        }
//
//        private Result(Boolean success, HttpStatus httpStatus, T obj) {
//            fillSuccessAndCode(success);
//            this.httpStatus = httpStatus;
//            this.data = obj;
//        }
//
//        private Result(ErrorInfo errorInfo) {
//            fillSuccessAndCode(Boolean.FALSE);
//            this.httpStatus = HttpStatus.EXPECTATION_FAILED;
//            this.errors = Arrays.asList(errorInfo);
//        }
//
//        private Result(List<ErrorInfo> errors) {
//            fillSuccessAndCode(Boolean.FALSE);
//            this.httpStatus = HttpStatus.EXPECTATION_FAILED;
//            this.errors = errors;
//        }
//
//        /**
//         * 为了兼容旧的前端代码，将success与code同步下值
//         * @param success boolean success
//         */
//        private void fillSuccessAndCode(Boolean success) {
//            this.success = success;
//            if (BooleanUtils.isTrue(success)) {
//                this.code = 0;
//            } else {
//                this.code = 1;
//            }
//        }
//    }

//
//    public ActionResult(boolean success, String errorMessage, String errorCode) {
//        this.success = success;
//        ErrorInfo error = new ErrorInfo(errorCode, errorMessage);
//        this.errors = Collections.singletonList(error);
//        message = error.getMessage();
//        if (!this.success) {
//            try {
//                this.code = StringUtil.stringToInt(error.getCode());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public ActionResult(boolean success, String errorMessage) {
//        this.success = success;
//        ErrorInfo error = new ErrorInfo("10000", errorMessage);
//        this.errors = Collections.singletonList(error);
//        message = error.getMessage();
//        if (!this.success) {
//            try {
//                this.code = StringUtil.stringToInt(error.getCode());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public ActionResult(boolean success, List<ErrorInfo> errors, T data) {
//        this.success = success;
//        this.errors = errors;
//        this.data = data;
//
//        if (!this.success) {
//            if (CollectionUtils.isNotEmpty(errors)) {
//                ErrorInfo error = errors.get(0);
//                message = error.getMessage();
//                try {
//                    this.code = StringUtil.stringToInt(error.getCode());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    public ActionResult(boolean success, List<ErrorInfo> errors) {
//        this.success = success;
//        this.errors = errors;
//        if (!this.success) {
//            if (CollectionUtils.isNotEmpty(errors)) {
//                ErrorInfo error = errors.get(0);
//                message = error.getMessage();
//                try {
//                    this.code = StringUtil.stringToInt(error.getCode());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    public ActionResult(boolean success, T data) {
//        this.success = success;
//        this.data = data;
//        if (!this.success) {
//            if (CollectionUtils.isNotEmpty(errors)) {
//                ErrorInfo error = errors.get(0);
//                message = error.getMessage();
//                try {
//                    this.code = StringUtil.stringToInt(error.getCode());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    public ActionResult(T data) {
//        this.success = true;
//        this.data = data;
//    }
//
//    public void addError(ErrorInfo errorInfo) {
//        this.success = false;
//        if (errors == null) {
//            errors = new ArrayList<>();
//        }
//        errors.add(errorInfo);
//        if (!this.success) {
//            if (CollectionUtils.isNotEmpty(errors)) {
//                ErrorInfo error = errors.get(0);
//                message = error.getMessage();
//                try {
//                    this.code = StringUtil.stringToInt(error.getCode());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    public void addErrors(List<ErrorInfo> errorInfoList) {
//        if (errorInfoList != null && errorInfoList.size() > 0) {
//            for (ErrorInfo errorInfo : errorInfoList) {
//                this.addError(errorInfo);
//            }
//        }
//    }
//
//    public void addError(String errorCode, String errorMsg) {
//        ErrorInfo errorInfo = new ErrorInfo(errorCode, errorMsg);
//        this.addError(errorInfo);
//    }
}
