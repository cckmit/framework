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
public class OtherMedicalHistory {
    private String id;

    @ApiModelProperty(value = " 报告ID")
    private String reportId;

    @ApiModelProperty(value = "开始时间")
    private String medicalHistoryStartDate;

    @ApiModelProperty(value = "仍持续")
    private String medicalHistoryStillContinue;

    @ApiModelProperty(value = "结束时间")
    private String medicalHistoryStopDate;

    @ApiModelProperty(value = "治疗药物")
    private String treatmentDrug;

    @ApiModelProperty(value = "用法用量")
    private String routeAndDosage;

    @ApiModelProperty(value = "病史")
    private String medicalHistory;

    private Boolean isDelete;

    private Date lastUpdateTime;

    private Date createTime;

    @ApiModelProperty(value = "病史药物")
    private String historyOfDrug;

    @ApiModelProperty(value = "是否原患疾病")
    private String whetherPatientPrimaryDisease;

    private String medicalHistoryType;

    private String relationshipWithPatient;

    private String medHistoryDrugBrandName;

    private Integer historyLong;

    private String historyUnit;

    private String familyDrugReactionEventHistory;

    private String familyHistory;

    private String importantMedicalStatus;

    private String previousDrugAdverseReactionEvent;

    private String omhAdverseEvent;

    private String omhIndications;

    private String omhMark;
}
