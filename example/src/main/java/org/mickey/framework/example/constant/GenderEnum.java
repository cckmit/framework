package org.mickey.framework.example.constant;

/**
 * @author wangmeng
 * @date 2020-05-18
 */
public enum GenderEnum {
    保密(3),
    女(2),
    男(1);

    private int code;

    int getValue() {
        return code;
    }

    GenderEnum(int code) {
        this.code = code;
    }

    public static GenderEnum parse(int value) {
        for (GenderEnum code : values()) {
            if (code.getValue() == value) {
                return code;
            }
        }
        return null;
    }
}
