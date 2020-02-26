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
public class DrugMedicationReason {
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "用药原因排序字段")
    private Byte sequence;

    @ApiModelProperty(value = "药物记录关联字段")
    private String drugId;

    @ApiModelProperty(value = "用药原因")
    private String reason;

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
    private Boolean isDelete;

    @ApiModelProperty(value = "版本号")
    private Long version;
}
