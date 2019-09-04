package org.mickey.framework.common.util;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class FixedValue {
    /**
     * 整数及小数位全为0的浮点数
     */
    static Pattern ZERO_FLOAT_INTEGER = Pattern.compile("^(\\-)?\\d+(\\.(0)+)?$");
}
