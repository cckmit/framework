package org.mickey.framework.example.constant;

/**
 * description
 *
 * @author wangmeng
 * @version 1.0.0
 * 2020年07月14日 11:34:00
 */
public enum OCRecognitionResultEnum {
    /**
     * OCR返回结果，识别中
     */
    识别中(-1),

    /**
     * OCR识别完成
     */
    已完成(1);

    private int value;

    OCRecognitionResultEnum(int value) {
        this.value = value;
    }
}
