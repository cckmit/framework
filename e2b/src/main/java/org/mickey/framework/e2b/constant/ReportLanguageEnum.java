package org.mickey.framework.e2b.constant;

import lombok.extern.slf4j.Slf4j;

/**
 * description
 *
 * @author mickey
 * 2020-02-21
 */
@Slf4j
public enum ReportLanguageEnum {
    /**
     * 中文
     */
    CN("cn"),
    /**
     * 英文
     */
    EN("en");

    private String language;

    ReportLanguageEnum(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }
}
