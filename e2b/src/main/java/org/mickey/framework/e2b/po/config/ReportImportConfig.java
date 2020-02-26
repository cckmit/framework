package org.mickey.framework.e2b.po.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.po.BasePo;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * description
 *
 * @author mickey
 * 2020-02-20
 */
@Slf4j
@Data
@Table(name = "report_import_config")
public class ReportImportConfig extends BasePo {
    @Column(columnDefinition = "varchar(36)")
    private String uniqueCode;
    @Column(columnDefinition = "longtext")
    private String reportBase;
    @Column(columnDefinition = "longtext")
    private String adverseEvent;
    @Column(columnDefinition = "longtext")
    private String reporter;
    @Column(columnDefinition = "longtext")
    private String drug;
    @Column(columnDefinition = "longtext")
    private String mark;
    @Column(columnDefinition = "longtext")
    private String meddraFieldInfo;
    @Column(columnDefinition = "longtext")
    private String otherMedicalHistory;
    @Column(columnDefinition = "longtext")
    private String relatedToCode;
    @Column(columnDefinition = "longtext")
    private String causeOfDeath;
    @Column(columnDefinition = "longtext")
    private String causality;
    @Column(columnDefinition = "longtext")
    private String labData;
    @Column(columnDefinition = "longtext")
    private String caseSummary;
    @Column(columnDefinition = "longtext")
    private String research;
    @Column(columnDefinition = "longtext")
    private String attachment;
    @Column(columnDefinition = "longtext")
    private String drugPsur;
    @Column(columnDefinition = "longtext")
    private String drugMedicationReason;
    @Column(columnDefinition = "longtext")
    private String drugDose;
    @Column(columnDefinition = "longtext")
    @ApiModelProperty(value = "特别注意 实质为 TM_Causality_Item 表")
    private String drugCausalityItem;
    @Column(columnDefinition = "longtext")
    @ApiModelProperty(value = "患者信息")
    private String reportPatientInformation;
    @Column(columnDefinition = "longtext")
    @ApiModelProperty(value = "e2b导入的发送者信息")
    private String e2bImportSender;
}
