package org.mickey.framework.common.po;

import javax.persistence.Column;
import java.io.Serializable;

@SuppressWarnings("serial")
public class BasePo extends CommonPo implements Serializable, Cloneable {

    @Column(nullable = false, columnDefinition = "varchar(50")
    protected String companyId;
}
