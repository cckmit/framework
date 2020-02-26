package org.mickey.framework.e2b.po.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.po.AbstractCommonPo;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * hl7 config
 *
 * @author mickey
 * 2020-02-20
 */
@Slf4j
@Data
@Table(name = "e2b_r3_hl7_config")
public class E2bR3HL7Config extends AbstractCommonPo {
    @Column(columnDefinition = "varchar(50)")
    private String headerEntity;
    @Column(columnDefinition = "varchar(50)")
    private String elementNumber;
    @Column(columnDefinition = "varchar(500)")
    private String elementName;
    @Column(columnDefinition = "varchar(100)")
    private String ichDataType;
    @Column(columnDefinition = "varchar(500)")
    private String hl7DataType;
    @Column(columnDefinition = "varchar(500)")
    private String hl7DataTypeSubComponent;
    @Column(columnDefinition = "varchar(50)")
    private String category;
    @Column(columnDefinition = "varchar(2000)")
    private String xpath;
    @Column(columnDefinition = "varchar(200)")
    private String oid;
    @Column(
            columnDefinition = "tinyint not null default 0 comment '1 需要翻译 0 不需要'"
    )
    private Integer needTranslate = 0;
}
