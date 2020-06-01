package org.mickey.framework.omp.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.mickey.framework.common.po.AbstractCommonPo;

/**
 * @author wangmeng
 * @date 2020-05-22
 */
@Data
public class UserPo extends AbstractCommonPo {
    private String name;

    private String password;
}
