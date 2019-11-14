package org.mickey.framework.common.exception;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 不经过i18n服务进行国际化转换
 *
 * @author mickey
 * 23/07/2019
 */
@Data
@Slf4j
public class NoI18nException extends RuntimeException {

    public NoI18nException(String message) {
        super(message);
    }
}
