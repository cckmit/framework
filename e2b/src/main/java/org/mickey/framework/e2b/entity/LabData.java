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
public class LabData {
    private String id;

    @ApiModelProperty(value = " 报告ID")
    private String reportId;

    @ApiModelProperty(value = "检查日期")
    private String startDate;

    @ApiModelProperty(value = "检查项")
    private String labTest;

    @ApiModelProperty(value = "结果判定")
    private String labTestAssessment;

    @ApiModelProperty(value = "检查结果")
    private String labDataResults;

    @ApiModelProperty(value = "单位")
    private String units;

    @ApiModelProperty(value = "正常值范围")
    private String labNormalRange;

    @ApiModelProperty(value = "备注")
    private String notes;

    private String companyId;

    private Boolean isDelete;

    private Date lastUpdateTime;

    private Date createTime;

    private String normalLowValue;

    private String normalHighValue;

    private String resultunstructureddata;

    private String normalHighValueUnit;

    private String normalLowValueUnit;

    private String moreInformation;
}
