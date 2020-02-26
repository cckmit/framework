package org.mickey.framework.e2b.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * description
 *
 * @author mickey
 * 2020-02-21
 */
@Slf4j
@Data
public class Related2Code {
    private String id;

    private Boolean isDelete;

    @ApiModelProperty(value = "编号类型")
    private String typeItemId;

    @ApiModelProperty(value = "编号")
    private String code;

    @ApiModelProperty(value = "备注")
    private String remark;

    private Date createTime;

    private String reportId;

    private Date lastUpdateTime;
}
