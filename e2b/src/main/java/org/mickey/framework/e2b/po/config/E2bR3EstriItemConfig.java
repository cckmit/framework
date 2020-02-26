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
@Data
@Table(name = "e2b_r3_estri_item_config")
public class E2bR3EstriItemConfig extends AbstractCommonPo {
    @Column(columnDefinition = "varchar(100)")
    private String elementNumber;
    @Column(columnDefinition = "varchar(50) comment 'e2b_r3_m2estri_config表id'")
    private String estriId;
    @Column(columnDefinition = "varchar(50) comment 'items表unique_code'")
    private String itemUniqueCode;
}
