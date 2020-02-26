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
public class DrugDose {
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "剂量（数）")
    private String doseNumber;

    @ApiModelProperty(value = "剂量（单位）")
    private String doseUnit;

    @ApiModelProperty(value = "间隔单位编号")
    private String unitInterval;

    @ApiModelProperty(value = "时间间隔单位的定义")
    private String unitIntervalDefinition;

    @ApiModelProperty(value = "开始用药的日期和时间")
    private String startDate;

    @ApiModelProperty(value = "末次给药的日期和时间")
    private String endDate;

    @ApiModelProperty(value = "给药的持续时间（数）")
    private String durationNumber;

    @ApiModelProperty(value = "给药的持续时间（单位）")
    private String durationUnit;

    @ApiModelProperty(value = "批次/批号")
    private String batch;

    @ApiModelProperty(value = "剂量文本")
    private String dosageText;

    @ApiModelProperty(value = "药物剂型表（自定义文本）")
    private String doseForm;

    @ApiModelProperty(value = "给药途径（自定义文本）")
    private String routeOfAdministration;

    @ApiModelProperty(value = "父/母的给药途径（自定义文本）")
    private String parentRouteOfAdministration;

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

    @ApiModelProperty(value = "药物信息记录ID")
    private String drugId;

    @ApiModelProperty(value = "日剂量")
    private String dailyDose;

    @ApiModelProperty(value = "日剂量单位")
    private String dailyDoseUnit;

    @ApiModelProperty(value = "频次")
    private String frequency;

    @ApiModelProperty(value = "用法-日")
    private String usageDay;

    @ApiModelProperty(value = "用法-次")
    private String usageTimes;

    @ApiModelProperty(value = "是否持续")
    private String drugContinued;

    @ApiModelProperty(value = "有效期")
    private String expirationDate;

    @ApiModelProperty(value = "疗程暴露量")
    private String regimenDrugExposure;

    @ApiModelProperty(value = "版本号")
    private Long version;

    private String frequencyunit;
}
