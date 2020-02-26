package org.mickey.framework.e2b.dto.report;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * description
 *
 * @author mickey
 * 2020-02-21
 */
@Slf4j
@Data
public class IcsrEntityDto {

    /**
     * 报告id
     */
    private String id;
    /**
     * 报告详情
     */
    private ReportBaseDto reportBaseDto;
    /**
     * 患者信息
     */
    private PatientInfoDto patientInfoDto;
    /**
     * 药物信息
     */
    private List<DrugDto> drugDtoList;
    /**
     * 不良事件
     */
    private List<AdverseEventDto> adverseEventDtoList;
    /**
     * 报告评价
     */
    private List<CausalityDto> causalityDtoList;
    /**
     * 实验室检查
     */
    private List<LabDataDto> labDataDtoList;
    /**
     * 报告者信息
     */
    private List<ReporterDto> reporterDtoList;
    /**
     * 相关病史
     */
    private List<OtherMedicalHistoryDto> otherMedicalHistoryDtoList;

    /**
     * 相关编号
     */
    private List<RelatedToCodeDto> relatedToCodeDtoList;
    /**
     * 死亡原因
     */
    private List<CauseOfDeathDto> causeOfDeathDtoList;
    /**
     * meddra编码
     */
    private List<MeddraFieldInfoDto> meddraFieldInfoDtoList;
    /**
     * 附件
     */
    private List<AttachmentR3Dto> attachmentR3DtoList;
    /**
     * 文献信息
     */
    private List<DocumentRetrievalDto> documentRetrievalDtoList;
    /**
     * 新生儿信息
     */
    private List<BabyInfoDto> babyInfoDtoList;
    /**
     * ResearchR3 和 AttachmentR3 专门给R3导入使用，作为载体
     */
    private List<ResearchR3Dto> researchR3DtoList;
    /**
     * 报告总结
     */
    private List<CaseSummaryDto> caseSummaryDtoList;
    /**
     * 原始资料分发任务ID
     */
    private String taskId;

    /*
    private List<WhodrugfieldinfoDto> whodrugfieldinfoDtoList;//whodrug编码
    private TmMedWatchDto tmMedWatchDto;//medwatch
    private List<E2bR2ImportSender> e2bR2ImportSenderList;//e2b导入的发送者信息
    private List<TmUnblindingDrug> tmUnblindingDrugList;
    private List<TmReportUnblindingLogDto> tmReportUnblindLogDtoList;
     */

    public void addAttachmentR3DTO(AttachmentR3Dto dto) {
        if (attachmentR3DtoList == null) {
            attachmentR3DtoList = new ArrayList<>();
        }
        attachmentR3DtoList.add(dto);
    }

    public void addAdverseEventDto(AdverseEventDto dto) {
        if (adverseEventDtoList == null) {
            adverseEventDtoList = new ArrayList<>();
        }
        adverseEventDtoList.add(dto);
    }

    public void addCauseOfDeathDto(CauseOfDeathDto dto) {
        if (causeOfDeathDtoList == null) {
            causeOfDeathDtoList = new ArrayList<>();
        }
        causeOfDeathDtoList.add(dto);
    }

    public void addLabDataDto(LabDataDto dto) {
        if (labDataDtoList == null) {
            labDataDtoList = new ArrayList<>();
        }
        labDataDtoList.add(dto);
    }

    public void addOtherMedicalHistoryDto(OtherMedicalHistoryDto dto) {
        if (otherMedicalHistoryDtoList == null) {
            otherMedicalHistoryDtoList = new ArrayList<>();
        }
        otherMedicalHistoryDtoList.add(dto);
    }

    public void addRelatedToCodeDto(RelatedToCodeDto dto) {
        if (relatedToCodeDtoList == null) {
            relatedToCodeDtoList = new ArrayList<>();
        }
        relatedToCodeDtoList.add(dto);
    }

    public void addReporterDto(ReporterDto dto) {
        if (reporterDtoList == null) {
            reporterDtoList = new ArrayList<>();
        }
        reporterDtoList.add(dto);
    }

    public void addResearchR3DTO(ResearchR3Dto dto) {
        if (researchR3DtoList == null) {
            researchR3DtoList = new ArrayList<>();
        }
        researchR3DtoList.add(dto);
    }

    public void addCaseSummary(CaseSummaryDto dto) {
        if (caseSummaryDtoList == null) {
            caseSummaryDtoList = new ArrayList<>();
        }
        caseSummaryDtoList.add(dto);
    }

    public void addDrugDto(DrugDto dto) {
        if (drugDtoList == null) {
            drugDtoList = new ArrayList<>();
        }
        drugDtoList.add(dto);
    }

}
