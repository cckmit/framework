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
public class NullFlavor {
    private String id;

    @ApiModelProperty(value = "子项ID")
    private String itemId;

    private String value;

    @ApiModelProperty(value = "report id")
    private String reportId;

    @ApiModelProperty(value = "column name")
    private String fieldName;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建人员")
    private String createBy;

    @ApiModelProperty(value = "更新人员")
    private String updateBy;

    @ApiModelProperty(value = "0：未删除   1：已删除")
    private Boolean isDelete;

    @ApiModelProperty(value = "租户ID")
    private String companyId;
}
