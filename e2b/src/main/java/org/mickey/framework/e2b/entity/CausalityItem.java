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
public class CausalityItem {
    private String source;

    private String method;

    private String result;

    @ApiModelProperty(value = "主键")
    private String id;

    private String causalityId;

    @ApiModelProperty(value = "租户Id")
    private String tenantId;

    @ApiModelProperty(value = "创建者Id")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "最后修改人Id")
    private String updateBy;

    @ApiModelProperty(value = "最后修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "删除标记 0 未删除 1 删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "版本号")
    private Long version;

    @ApiModelProperty(value = "来源字典值")
    private String sourceItem;

    @ApiModelProperty(value = "结果字典值")
    private String resultItem;

    @ApiModelProperty(value = "结果字典值")
    private String methodItem;
}
