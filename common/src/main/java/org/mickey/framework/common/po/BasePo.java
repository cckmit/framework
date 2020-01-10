package org.mickey.framework.common.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * base po基于common po增加tenant id
 *
 * @author mickey
 * @date 2020-01-10
 */
@SuppressWarnings("serial")
@Data
public class BasePo extends AbstractCommonPo implements Serializable, Cloneable {

    @ApiModelProperty(value = "租户ID，系统自动读取当前登录用户的租户ID", position = 95)
    @Column(nullable = false, updatable = false, length = 50)
    protected String tenantId;
}
