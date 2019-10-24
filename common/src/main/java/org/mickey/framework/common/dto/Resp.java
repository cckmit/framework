package org.mickey.framework.common.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Data
public class Resp<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int code;
    private T data;
    private String message;
}
