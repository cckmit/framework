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
public class Reporter {
    private String id;

    @ApiModelProperty(value = " 报告ID")
    private String reportId;

    @ApiModelProperty(value = "联系电话")
    private String telephoneNumber;

    @ApiModelProperty(value = "职业")
    private String reporterOccupation;

    @ApiModelProperty(value = "首要报告者")
    private String primaryReporter;

    @ApiModelProperty(value = "报告者姓名")
    private String reporterName;

    @ApiModelProperty(value = "邮寄地址")
    private String postAddress;

    @ApiModelProperty(value = "职业说明")
    private String occupationalDescription;

    @ApiModelProperty(value = "联系方式")
    private String contactMethod;

    @ApiModelProperty(value = "报告者评价")
    private String reporterEvaluation;

    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @ApiModelProperty(value = "传真")
    private String fax;

    @ApiModelProperty(value = "可否被随访")
    private String canReporterBeFollowedUp;

    @ApiModelProperty(value = "备注")
    private String notes;

    private String companyId;

    private Boolean isDelete;

    private Date lastUpdateTime;

    private Date createTime;

    private String researchRole;

    @ApiModelProperty(value = "研究者获知SAE时间")
    private String learnedSAETime;

    @ApiModelProperty(value = "是否严重")
    private String isSerious;

    @ApiModelProperty(value = "报告者信息--报告者称谓")
    private String reporterNickname;

    @ApiModelProperty(value = "报告者信息--报告者所在单位")
    private String reporterOrganization;

    @ApiModelProperty(value = "报告者信息--报告者所在部门")
    private String reporterDepartment;

    @ApiModelProperty(value = "报告者信息--报告者详细地址")
    private String reporterAddress;

    @ApiModelProperty(value = "报告者信息--报告者所在国")
    private String reporterCountry;

    @ApiModelProperty(value = "报告者信息--报告者所在省份")
    private String reporterProvince;

    @ApiModelProperty(value = "报告者信息--报告者所在城市")
    private String reporterCity;

    @ApiModelProperty(value = "报告者信息--报告者所在地邮编")
    private String reporterZipCode;

    @ApiModelProperty(value = "医院名称")
    private String rHospitalName;

    @ApiModelProperty(value = "医疗机构电话")
    private String rHospitalTelNumber;

    @ApiModelProperty(value = "经医学确认")
    private String rMedicalConfirmed;

    private String reporterStandardofSeriousness;

    private String reporterFamilyName;

    private String reporterMiddleName;
}
