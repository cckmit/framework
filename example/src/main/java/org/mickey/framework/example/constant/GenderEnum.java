package org.mickey.framework.example.constant;

/**
 * @author wangmeng
 * @date 2020-05-18
 */
public enum GenderEnum {
    保密(3),
    女(2),
    男(1);

    private Integer code;

    GenderEnum(Integer code) {
        this.code = code;
    }
}
