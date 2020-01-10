package org.mickey.framework.common.config;

import lombok.extern.slf4j.Slf4j;

/**
 * 原始资料类型
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public enum OssFileTypeEnum {
    /**
     * 原始资料
     */
    REPORT_SOURCE("1", "reportsource", "原始资料"),
    /**
     * 产品附件
     */
    PRODUCT_ATTACHMENT("2", "productattach", "产品附件"),
    /**
     * 租户LOGO
     */
    COMPANY_LOGO("3", "companylogo", "租户LOGO"),
    /**
     * E2B转换文件
     */
    E2B_CONVERT("4", "e2bconvert", "E2B转换文件"),
    /**
     * PSUR信息收集
     */
    PSUR_ATTACHMENT("5", "psurattachment", "PSUR信息收集"),
    /**
     * 质疑附件
     */
    EXTERNALQUERY_FILE("6", "externalquery", "质疑附件"),
    /**
     * 数据导出
     */
    DATA_EXPORT("7", "dataexport", "数据导出"),
    ;

    private String value;
    private String path;
    private String desc;

    OssFileTypeEnum(String value, String path, String desc) {
        this.value = value;
        this.path = path;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public String getPath() {
        return path;
    }

    public String getDesc() {
        return desc;
    }
}
