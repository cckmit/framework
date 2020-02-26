package org.mickey.framework.e2b.constant;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * description
 *
 * @author mickey
 * 2020-02-21
 */
@Slf4j
@Data
public class E2BImportConfigConstant {
    public static String debugTarget="reportReceiveDate";
    public final static String OID_BOOLEAN_PV="BOOLEAN_PV";

    public final static String SC_PREVIEW_REPORT_ID="PREVIEW_REPORT_ID";
    public static final String BASE_COMPANY_ID = "baseCompanyId";
    public static final String E2B_CONFIG_ID = "0ff10c49-309d-11e8-aa2e-000c29ef8c08";
    public static final String E2B_CONFIG_PREVIEW_UNIQUECODE ="62ca2af7-46c5-11e8-919b-000c29399d7c";
    public static final String E2B_CONFIG_EXPORT_UNIQUECODE ="305d6e37-de7f-11e8-a962-000c29399d7c";
    public static final String E2B_CONFIG_R3_UNIQUECODE="2afea745-68a7-11e8-a602-000c29399d7c";

    /**
     * E2B R3 Related
     */
    final public static String XPATH_BASE_FLAG="PORR_IN049016UV";
    public final static Map<String,Integer> reportRootNodeName;
    static{
        reportRootNodeName = new HashMap<>();
        reportRootNodeName.put("PORR_IN049016UV",1);
    }

    public final static Map<String,String> drugSubElementNumber2DrugSubCategory;
    public static final String PSUR_DRUG_ITEM = "PsurDrugItem";
    public static final String MEDICATION_REASON = "MedicationReason";
    public static final String DRUG_DOSE = "DrugDose";
    public static final String CAUSALITY = "Causality";
    public static final String CAUSALITY_ITEM = "CausalityItem";
    public static final String DRUG_MORE_INFO="DrugMoreInfo";

    public static final String PSUR_DRUG_ITEM_NUM="G.k.2.3.r";
    public static final String MEDICATION_REASON_NUM = "G.k.7.r";
    public static final String DRUG_DOSE_NUM = "G.k.4.r";
    public static final String CAUSALITY_NUM = "G.k.9.i";
    public static final String CAUSALITY_ITEM_NUM = "G.k.9.i.2.r";

    public static final String BATCH_SENDER_NUM="N.1.3";
    public static final String BATCH_RECEIVE_NUM="N.1.4";

    public static final String MESSAGE_SENDER_NUM="N.2.r.2";
    public static final String MESSAGE_RECEIVE_NUM="N.2.r.3";
    public static final String E2B_NO_NUM="C.1.1";
    public static final String FIRST_RECIEVE_DATE_NUM="C.1.4";
    public static final String RECIEVE_DATE_NUM="C.1.5";
    public static final String STUDY_NAME_NUM="C.5.2";
    public static final String STUDY_NAME_NO_NUM="C.5.3";

    public static final String DEATH_CAUSE_NUM="D.9.2.r";
    public static final String AUTOPSY_NUM="D.9.4.r";
    public static final String REPORT_DIAGNOSE_NUM="H.3.r";
    public static final String DRUG_MORE_INFO_NUM="G.k.10.r";
    public static final String DRUG_MORE_INFO_REPEAT_NUM ="G.k.10.r.1";
    public static final String R3_START_PATH = "/MCCI_IN200100UV01[@ITSVersion='XML_1.0'][@xsi:schemaLocation='urn:hl7-org:v3MCCI_IN200100UV01.xsd']/PORR_IN049016UV/id/@extension";



    static {
        drugSubElementNumber2DrugSubCategory = new HashMap<>();
        drugSubElementNumber2DrugSubCategory.put(PSUR_DRUG_ITEM_NUM, PSUR_DRUG_ITEM);
        drugSubElementNumber2DrugSubCategory.put(MEDICATION_REASON_NUM, MEDICATION_REASON);
        drugSubElementNumber2DrugSubCategory.put(DRUG_DOSE_NUM, DRUG_DOSE);
        drugSubElementNumber2DrugSubCategory.put(CAUSALITY_NUM, CAUSALITY);
        drugSubElementNumber2DrugSubCategory.put(CAUSALITY_ITEM_NUM, CAUSALITY_ITEM);
        drugSubElementNumber2DrugSubCategory.put(DRUG_MORE_INFO_REPEAT_NUM,DRUG_MORE_INFO);
    }

    public static final String TARGET_FILE_DRUG_ID_KEY ="fileDrugId";
    public static final String TARGET_FILE_EVENT_ID_KEY ="fileEventId";

    public static final String MEDICATION_NUM ="D.7.1.r";
    public static final String MEDICATION_DRUG_NUM ="D.8.r";

    public static final String RELATETOCODE_PRESYSTEM_NUM ="C.1.9.1.r";
    public static final String RELATETOCODE_OTHER_NUM ="C.1.10.r.1";
    public static final String RELATETOCODE_IMPORTE2B_NUM ="C.1.1";

    public static final Map<String,String> labdataResultOperatorMap;
    public static final Map<String,String> operatorLabdataResultMap;
    static {
        labdataResultOperatorMap= new HashMap<>();
        labdataResultOperatorMap.put("F.r.3.2","=");
        labdataResultOperatorMap.put("F.r.3.2_low_gt",">");
        labdataResultOperatorMap.put("F.r.3.2_low_ge","≥");
        labdataResultOperatorMap.put("F.r.3.2_high_lt","<");
        labdataResultOperatorMap.put("F.r.3.2_high_le","≤");

        operatorLabdataResultMap=new HashMap<>();
        operatorLabdataResultMap.put("#","eq");
        operatorLabdataResultMap.put("=","eq");
        operatorLabdataResultMap.put(">","gt");
        operatorLabdataResultMap.put("≥","ge");
        operatorLabdataResultMap.put("<","lt");
        operatorLabdataResultMap.put("≤","le");

    }
}

