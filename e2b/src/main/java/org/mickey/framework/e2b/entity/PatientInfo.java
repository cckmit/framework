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
public class PatientInfo {
    private String id;

    @ApiModelProperty(value = "报告ID")
    private String reportId;

    @ApiModelProperty(value = "性别")
    private String patientGender;

    @ApiModelProperty(value = "体重")
    private String patientweight;

    @ApiModelProperty(value = "身高")
    private String patientheight;

    @ApiModelProperty(value = "生日")
    private String patientDOB;

    @ApiModelProperty(value = "姓名缩写")
    private String patientInitial;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "年龄层")
    private String ageGroup;

    @ApiModelProperty(value = "年龄单位")
    private String ageatTimeofOnset;

    @ApiModelProperty(value = "首次药物暴露所处孕期")
    private String gestationPeriodatTimeofExposure;

    @ApiModelProperty(value = "盲态")
    private String blindedorNot;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "0未删除1已删除")
    private Boolean isDelete;

    @ApiModelProperty(value = "公司ID")
    private String companyId;

    private String createBy;

    private String updateBy;

    private String patientName;

    private String sex;

    private String dateOfBirth;

    private String ageAtTimeOfOnSetUnit;

    private String racial;

    private String patientCitizenship;

    private String patientNationality;

    private String patientContactInformation;

    private String weight;

    private String height;

    private String gpMedicalRecordNumber;

    private String hospitalName;

    @ApiModelProperty(value = "事件发生时孕周")
    private String weeksAtOnset;

    @ApiModelProperty(value = "事件发生时孕周单位")
    private String gestationPeriodUnit;

    @ApiModelProperty(value = "末次月经时间")
    private String lmp;

    @ApiModelProperty(value = "相关病史及并发疾病说明")
    private String relevantMedicalHistory;

    @ApiModelProperty(value = "父/母姓名缩写")
    private String parentIdentification;

    @ApiModelProperty(value = "父母出生日期")
    private String dateOfBirthOfParent;

    @ApiModelProperty(value = "父/母年龄(数字)")
    private String ageOfParent;

    @ApiModelProperty(value = "父/母的年龄（单位）")
    private String parentAgeUnit;

    @ApiModelProperty(value = "母亲末次月经")
    private String lastMenstrualPeriodDate;

    @ApiModelProperty(value = "父/母的体重(kg)")
    private String bodyWeightOfParent;

    @ApiModelProperty(value = "父/母的身高(cm)")
    private String parentHeight;

    @ApiModelProperty(value = "父/母的性别")
    private String parentSex;

    @ApiModelProperty(value = "相关病史及并发疾病的文本说明")
    private String parentRelevantMedicalHistory;

    @ApiModelProperty(value = "合并治疗")
    private String concomitantTherapiesNew;

    @ApiModelProperty(value = "患者编号")
    private String subjectNumber;

    @ApiModelProperty(value = "中心编号")
    private String centerNumber;

    @ApiModelProperty(value = "GP 编号")
    private String gpNumber;

    @ApiModelProperty(value = "专家记录编号")
    private String specialistRecordNumber;

    @ApiModelProperty(value = "父母信息")
    private String parentsInformation;

    @ApiModelProperty(value = "是否妊娠")
    private String pregnancyReport;

    @ApiModelProperty(value = "妊娠次数")
    private String gravida;

    @ApiModelProperty(value = "受试者先天性异常/缺陷")
    private String theSubjectsofCongenitalAnomalies;

    @ApiModelProperty(value = "避孕措施")
    private String contraceptives;

    @ApiModelProperty(value = "妊娠预产期")
    private String dueDate;

    @ApiModelProperty(value = "妊娠胎儿数")
    private String numberOfFetus;

    @ApiModelProperty(value = "药物暴露孕周")
    private String weeksAtExposure;

    @ApiModelProperty(value = "开始用药时孕期")
    private String trimesterOfExposure;

    @ApiModelProperty(value = "产前检查")
    private String prenatalCare;

    @ApiModelProperty(value = "妊娠状态")
    private String birthType;

    @ApiModelProperty(value = "人工流产原因")
    private String abortionCause;

    @ApiModelProperty(value = "妊娠详情及转归")
    private String detailsOfPregnancy;

    @ApiModelProperty(value = "妊娠状态持续中")
    private String outcomePending;
}
