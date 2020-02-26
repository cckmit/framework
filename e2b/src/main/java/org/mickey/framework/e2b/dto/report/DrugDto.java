package org.mickey.framework.e2b.dto.report;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.e2b.entity.Drug;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 2020-02-21
 */
@Slf4j
@Data
public class DrugDto extends Drug implements IcsrDtoInterface {
    private Map<String, MeddraFieldInfoDto> meddraMap;
    private Map<String, NullFlavorInfoDto> nullFlavorMap;

    /**
     * 药物下子表
     */
    private List<ProductDrugItemDto> productDrugItemDtoList;
    /**
     * 计量信息
     */
    private List<DrugDoseDto> drugDoseDtoList;
    /**
     * 用药原因
     */
    private List<DrugMedicationReasonDto> medicationReasonDtoList;

    private String fileDrugId;
    private List<CausalityDto> rCausalityDtoList;


    public void addMedicationReasonDto(DrugMedicationReasonDto reason) {
        if (medicationReasonDtoList == null) {
            medicationReasonDtoList = new ArrayList<>();
        }
        medicationReasonDtoList.add(reason);
    }

    public void addPsurDrugItemDto(ProductDrugItemDto psur) {
        if (productDrugItemDtoList == null) {
            productDrugItemDtoList = new ArrayList<>();
        }
        productDrugItemDtoList.add(psur);
    }

    public void addDrugDoseDto(DrugDoseDto dose) {
        if (drugDoseDtoList == null) {
            drugDoseDtoList = new ArrayList<>();
        }
        drugDoseDtoList.add(dose);
    }
    public void addRCausalityDto(CausalityDto rCausalityDTO) {
        if(rCausalityDtoList == null) {
            rCausalityDtoList = new ArrayList<>();
        }
        rCausalityDtoList.add(rCausalityDTO);
    }

    @Override
    public String getCreateBy() {
        return null;
    }

    @Override
    public void setCreateBy(String createBy) {

    }

    @Override
    public String getUpdateBy() {
        return null;
    }

    @Override
    public void setUpdateBy(String updateBy) {

    }

    @Override
    public Date getUpdateTime() {
        return null;
    }

    @Override
    public void setUpdateTime(Date updateTime) {

    }
}
