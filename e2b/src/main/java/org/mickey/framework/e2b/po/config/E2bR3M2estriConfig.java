package org.mickey.framework.e2b.po.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.po.AbstractCommonPo;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * description
 *
 * @author mickey
 * 2020-02-20
 */
@Slf4j
@Table(name = "e2b_r3_m2estri_config")
@Data
public class E2bR3M2estriConfig extends AbstractCommonPo {
    @Column(columnDefinition = "varchar(50)")
    private String shortName;
    @Column(columnDefinition = "varchar(100)")
    private String longName;
    @Column(columnDefinition = "varchar(20)")
    private String rversion;
    @Column(columnDefinition = "varchar(100)")
    private String canonicalUri;
    @Column(columnDefinition = "varchar(100)")
    private String canonicalVersionUri;
    @Column(columnDefinition = "varchar(50)")
    private String agencyShortName;
    @Column(columnDefinition = "varchar(100)")
    private String agencyLongName;
    @Column(columnDefinition = "varchar(50) not null")
    private String code;
    @Column(columnDefinition = "varchar(200)")
    private String englishLabel;
    @Column(columnDefinition = "varchar(200)")
    private String japaneseLabel;
    @Column(columnDefinition = "varchar(50)")
    private String currentFlag;
}
