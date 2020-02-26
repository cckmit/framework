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
public class ReportBase {
    private String id;

    @ApiModelProperty(value = "企业报告类型")
    private String companyReportType;

    @ApiModelProperty(value = "AE发生国")
    private String aeCountry;

    @ApiModelProperty(value = "报告收到日期")
    private Date companyReceivedDate;

    @ApiModelProperty(value = "首次或随访")
    private String initialorFU;

    @ApiModelProperty(value = "报告编号")
    private String safetyReportId;

    @ApiModelProperty(value = "药警部门收到时间")
    private Date pvReceivedDate;

    @ApiModelProperty(value = "版本号")
    private String versionNumber;

    @ApiModelProperty(value = "企业信息来源")
    private String infoSource;

    @ApiModelProperty(value = "报告状态")
    private Integer reportStatus;

    @ApiModelProperty(value = "报告所有者")
    private String owner;

    @ApiModelProperty(value = "严重性")
    private String seriousness;

    @ApiModelProperty(value = "民族")
    private String patientNationality;

    @ApiModelProperty(value = "严重性标准")
    private String severityOfTheStandard;

    @ApiModelProperty(value = "首要不良事件")
    private String adverseEvent;

    @ApiModelProperty(value = "药品名称")
    private String drugName;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "0未删除1已删除")
    private Boolean isDelete;

    @ApiModelProperty(value = "公司ID")
    private String companyId;

    @ApiModelProperty(value = "报告录入途径")
    private Integer reportSource;

    private String createBy;

    private String updateBy;

    private String psurDrugIds;

    private String autopsy;

    private String mark;

    private String caseNarrative;

    private String companyReview;

    private String aelocality;

    private String worldwideUniqueNumber;

    private String firstReceivedReportDate;

    private String fulFillExpediteCriteria;

    private String outSideReport;

    private String lastReportTime;

    private String managerReportDate;

    private String reportSubmitDate;

    private String countryCenterReceiveTime;

    @ApiModelProperty(value = "修正/作废的原因")
    private String versionDescription;

    private String reportNullificationAmendment;

    @ApiModelProperty(value = "作废/修正的原因")
    private String reason4Nullification;

    private String deleteMark;

    private String invalidReason;

    private String reportTypeId;

    private String ponderanceId;

    private String causalityId;

    private String reporterComments;

    private String countryNum;

    private String sourceInfoId;

    private String sourceInfoName;

    private String isInvalid;

    private String dateOfDeath;

    private String autopsyReport;

    @ApiModelProperty(value = "发现该反应/事件时的研究类型")
    private String studyType;

    @ApiModelProperty(value = "研究方案名称")
    private String researchTopic;

    @ApiModelProperty(value = "报告首位发送者")
    private String firstSender;

    @ApiModelProperty(value = "研究方案编号")
    private String researchId;
}
