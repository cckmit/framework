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
public class Causality {
    private String id;

    @ApiModelProperty(value = " 报告ID")
    private String reportId;

    @ApiModelProperty(value = "首剂量至事件发生时间")
    private String firstDosetoEventonsetdate;

    @ApiModelProperty(value = "末剂量至事件发生时间")
    private String lastDoseToEventOnset;

    @ApiModelProperty(value = "再次使用可疑药品后是否再次出现同样反应/事件？")
    private String rechallenge;

    @ApiModelProperty(value = "因果评价（报告者）")
    private String reporterCausality;

    @ApiModelProperty(value = "因果关系（Naranjo 评分）")
    private String causalityAssessmentNaranjoquestionnaire;

    @ApiModelProperty(value = "因果评价(修正)")
    private String causalityAssessmentAmendment;

    @ApiModelProperty(value = "产品")
    private String product;

    @ApiModelProperty(value = "事件")
    private String event;

    @ApiModelProperty(value = "是否已知")
    private String whetherExpectedorUnexpected;

    @ApiModelProperty(value = "停药或减量后，反应/事件是否消失或减轻?")
    private String dechallenge;

    private String companyId;

    private Boolean isDelete;

    private String adverseEventId;

    private String drugId;

    private Date lastUpdateTime;

    private Date createTime;

    private String manufacturer;

    private String calDrugType;

    private String calActionTakenforSuspectDrug;

    private Integer canReset;

    private String reporterEvaluation;

    private String eventPTName;

    @ApiModelProperty(value = "开始用药至事件发生时间的间隔 ")
    private String beginDrugStartEventInterval;

    @ApiModelProperty(value = "开始用药至事件发生时间的间隔单位 ")
    private String beginDrugStartEventIntervalUnit;

    @ApiModelProperty(value = "结束用药至事件发生时间的间隔 ")
    private String lastDrugStartEventInterval;

    @ApiModelProperty(value = "结束用药至事件发生时间的间隔单位 ")
    private String lastDrugStartEventIntervalUnit;
}
