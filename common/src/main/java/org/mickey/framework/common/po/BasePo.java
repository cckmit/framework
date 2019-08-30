package org.mickey.framework.common.po;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@SuppressWarnings("serial")
@Data
public class BasePo extends CommonPo implements Serializable, Cloneable {

    @Column(nullable = false, updatable = false, length = 50)
    protected String tenantId;
}
