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
public class AdverseEvent {
    private String id;

    private String reportId;

    @ApiModelProperty(value = "死亡时间")
    private String dateofDeath;

    @ApiModelProperty(value = "直接死因")
    private String causeofDeath;

    @ApiModelProperty(value = "是否尸检")
    private String autopsy;

    @ApiModelProperty(value = "尸检结果")
    private String autopsyResults;

    @ApiModelProperty(value = "不良事件名称")
    private String eventTerm;

    @ApiModelProperty(value = "不良事件发生时间")
    private String dateofOnset;

    @ApiModelProperty(value = "不良事件结束时间")
    private String dateofResolution;

    @ApiModelProperty(value = "不良事件的结果")
    private String eventOutcome;

    @ApiModelProperty(value = "sea国外报道情况")
    private String saeReportedAbroad;

    @ApiModelProperty(value = "首要不良事件")
    private String initialReport;

    private String manifestationofSequelae;

    @ApiModelProperty(value = "对原患疾病的影响")
    private String impactonPrimaryDisease;

    @ApiModelProperty(value = "是否为IME")
    private String isIME;

    @ApiModelProperty(value = "是否严重")
    private String seriousness;

    @ApiModelProperty(value = "严重性标准")
    private String standardofSeriousness;

    @ApiModelProperty(value = "严重程度")
    private String severity;

    @ApiModelProperty(value = "CTC AE分级")
    private String cTCAEclassification;

    @ApiModelProperty(value = "住院开始时间")
    private String admittedDate;

    @ApiModelProperty(value = "住院结束时间")
    private String dateforDischarge;

    @ApiModelProperty(value = "住院时长(天)")
    private String hospitalStay;

    @ApiModelProperty(value = "处理措施")
    private String process;

    private Boolean isDelete;

    private Date lastUpdateTime;

    private Date createTime;

    @ApiModelProperty(value = "是否加重")
    private String whetherIncreasing;

    @ApiModelProperty(value = "因该不良事件退出临床试验")
    private String quitClinicalTrials;

    @ApiModelProperty(value = "是否接受医学治疗和医学处置")
    private String whetherAcceptDisposal;

    @ApiModelProperty(value = "与研究过程有关")
    private String relatedToTheResearchProcess;

    @ApiModelProperty(value = "是否修改（true时因果评价需要作出响应）")
    private Boolean isEditedForCausality;

    @ApiModelProperty(value = "SAE国内报道")
    private String saeReportedDocestic;

    private String responseclassification;

    private Integer sortIndex;

    private String diseaseprogression;

    private String clinicalpurposeevent;

    private String clinicalendevent;

    @ApiModelProperty(value = "住院/住院时间延长，二选一")
    private String hospitalDetail;

    private String aeMedicalConfirmed;

    @ApiModelProperty(value = "发热")
    private String fever;

    @ApiModelProperty(value = "红肿")
    private String redAndSwollen;

    @ApiModelProperty(value = "硬结")
    private String indurate;

    @ApiModelProperty(value = "初步分类")
    private String preliminaryClassification;

    @ApiModelProperty(value = "反应分类")
    private String reactionClassification;

    @ApiModelProperty(value = "是否群体性AEFI")
    private String aEFI;

    private String disablingDetail;

    private Date saehappentime;

    private String eventDuration;

    private String eventDurationUnit;

    private String eventNameNativeLanguage;

    private String nativeLanguageCode;

    private String reporterTermHighLighted;

    private String eventCountry;

}
