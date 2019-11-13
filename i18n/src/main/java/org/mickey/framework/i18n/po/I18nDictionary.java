package org.mickey.framework.i18n.po;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.po.BasePo;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Data
@Slf4j
@Table
public class I18nDictionary extends BasePo {
    @Column
    private String appId;
    @Column
    private String locale;
    @Column
    private String key;
    @Column
    private String value;
    @Column
    private String mark;
}
