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
public class Drug {
    private String id;

    @ApiModelProperty(value = " 报告ID")
    private String reportId;

    @ApiModelProperty(value = "药品类型")
    private String drugType;

    @ApiModelProperty(value = "商品名称")
    private String brandName;

    @ApiModelProperty(value = "Active Ingredient (s)")
    private String activeIngredient;

    @ApiModelProperty(value = "盲态")
    private String blindedorNot;

    @ApiModelProperty(value = "批准文号")
    private String licenceNumber;

    @ApiModelProperty(value = "Country")
    private String country;

    @ApiModelProperty(value = "生产厂家")
    private String manufacture;

    @ApiModelProperty(value = "用法")
    private String routeofAdministration;

    @ApiModelProperty(value = "剂量")
    private String dosage;

    @ApiModelProperty(value = "剂量单位")
    private String doseunit;

    @ApiModelProperty(value = "用药开始时间")
    private String drugStartDate;

    @ApiModelProperty(value = "用药结束时间（如适用）")
    private String drugStopDate;

    @ApiModelProperty(value = "生产批号")
    private String batchNumber;

    @ApiModelProperty(value = "适应症")
    private String indication;

    @ApiModelProperty(value = "对可疑药物采取的措施")
    private String actionTakenforSuspectDrug;

    @ApiModelProperty(value = "频次")
    private String frequency;

    @ApiModelProperty(value = "研究药物")
    private String studyDrug;

    @ApiModelProperty(value = "通用名称")
    private String genericName;

    @ApiModelProperty(value = "药品分类")
    private String drugCategorization;

    @ApiModelProperty(value = "英文名称")
    private String englishName;

    @ApiModelProperty(value = "规格")
    private String concentration;

    @ApiModelProperty(value = "有效期至")
    private String expirationDate;

    @ApiModelProperty(value = "疗程药物暴露量")
    private String regimenDrugexposure;

    @ApiModelProperty(value = "采取措施的时间")
    private String dateofActionTaken;

    @ApiModelProperty(value = "破盲时间")
    private String dateforUnblinding;

    @ApiModelProperty(value = "破盲原因")
    private String unblindReason;

    private String companyId;

    @ApiModelProperty(value = "用药原因")
    private String medicalReasons;

    @ApiModelProperty(value = "剂型")
    private String dosageFormId;

    private Boolean isDelete;

    private Date lastUpdateTime;

    private Date createTime;

    @ApiModelProperty(value = "再激发")
    private String rechallenge;

    @ApiModelProperty(value = "去激发")
    private String dechallenge;

    @ApiModelProperty(value = "剂量详情")
    private String doseTheDetails;

    @ApiModelProperty(value = "末剂量至事件发生时间")
    private String lastDoseToEventOnset;

    @ApiModelProperty(value = "首剂量至事件发生时间")
    private String firstDoseToEventOnset;

    @ApiModelProperty(value = "对怀疑药物采取措施的原因")
    private String reasonOfActionTakenforSuspectDrug;

    @ApiModelProperty(value = "末剂量至事件发生时间单位")
    private String lastDoseToEventOnsetUnit;

    @ApiModelProperty(value = "首剂量至事件发生时间单位")
    private String firstDoseToEventOnsetUnit;

    @ApiModelProperty(value = "药物信息--是否持续")
    private String drugContinued;

    @ApiModelProperty(value = "日剂量")
    private String dailyDose;

    @ApiModelProperty(value = "日剂量单位")
    private String dailyDoseUnit;

    @ApiModelProperty(value = "用法-日")
    private String usageDay;

    @ApiModelProperty(value = "用法-次")
    private String usageTimes;

    private String psurDrugId;

    private String productCategory;

    @ApiModelProperty(value = "药物的额外信息")
    private String drugAddItionalInfo;

    @ApiModelProperty(value = "持有者/申请者姓名")
    private String holderName;

    @ApiModelProperty(value = "药物相互作用")
    private String medicineInteractions;

    @ApiModelProperty(value = "未用药")
    private String untreated;

    @ApiModelProperty(value = "药品获得国")
    private String obtainedCountry;

    @ApiModelProperty(value = "试验药物是否设盲")
    private String investigationalBlinded;

    @ApiModelProperty(value = "上市许可/申请编号")
    private String authorisationNumber;

    @ApiModelProperty(value = "上市许可/申请国家")
    private String authorisationCountry;

    @ApiModelProperty(value = "首次发生反应的累积剂量")
    private String cumulativeDose;

    @ApiModelProperty(value = "首次发生反应的累积剂量单位")
    private String cumulativeDoseUnit;

    @ApiModelProperty(value = "药物暴露孕周")
    private String exposureGestationPeriod;

    @ApiModelProperty(value = "药物暴露孕周单位")
    private String exposureGestationPeriodUnit;

    @ApiModelProperty(value = "药物暴露孕周单位")
    private String drugOtherInfo;
}
