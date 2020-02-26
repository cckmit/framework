package org.mickey.framework.e2b.constant;

import java.util.Arrays;
import java.util.List;

/**
 * description
 *
 * @author mickey
 * 2020-02-22
 */
public class ItemUniqueCodeProvider {

    /**
     * RelateTOcode
     */
    public static final String E2BReportCode = "C28E45CC-02CB-4BE6-ACB2-E041AAE8CE5A";
    public static final String E2BDuplicateCode = "246220B5-B0AF-4309-BA6B-3CEFD5B90078";
    public static final String E2BAuthorityCode = "6A69D3D3-26F4-455E-BDA4-0517D596B10F";
    public static final String E2BOtherCode = "D87F2B9A-1A18-458A-977F-340E3EF6190F";
    public static final String E2BCompanyCode = "80564D01-5276-494C-9588-E831196479EE";
    public static final String E2BPRESYTEMCODE="7e34ea67-63a0-4f4a-9a5b-74e1bbc64340";
    //同一患者信息编号
    public static final String SAME_PATIENT_CODE="0BE24A49-40FA-49D5-B169-E128B92897E2";
    //同一报告者
    public static final String SAME_REPORTER_CODE="12004FDE-346C-47AB-A9AE-D765690EABC8";
    //相关报告编号
    public static final String RELATED_CODE="904c9db1-624e-11e8-9882-000c29399d7c";
    //兄弟姐妹报告编号
    public static final String CONSIN_CODE="C44C4F45-848A-4ECB-8FB3-3A7ACB1DB8A6";
    //亲子关联编号
    public static final String CHILD_CODE="CB5D979F-D548-4617-8E56-3FF1BA34356D";
    //状态开发
    public static final String OPEN_LABEL="b0b315fe-c9a6-4244-aa89-82fafc37878f";



    /**
     * 相关编号 国家系统编号
     */
    public static final String CFDA_CASE_NUMBER = "a44894d1-45df-4fe3-9458-70173c41e238";
    public static final String FOREIGN_CASE_NUMBER = "224DE2E0-6255-4932-B20A-6634C4896E00";
    public static final String MAH_NUMBER = "f7ab60b7-4603-11e9-a99b-000c29399d7c";//MAH编码
    /**
     * CreateReportType
     */
    public static final String CREATE_NEW_REPORT = "a163a9b9-6cc8-467b-a6bf-c51502a795e8";
    public static final String CREATE_NEW_VERSION = "2afcea68-43ca-4e21-b727-70fd30615b97";
    public static final String OVERRIDE_REPORT = "65b3e937-4ba2-11e9-8df3-000c29ee981b";

    // region 相关病史
    /**
     * 既往病史
     */
    public static final String PATIENT_MEDICINE_HISTORY = "e18b2b3f-ee84-4af4-a903-1f72d60a49b6";
    /**
     * 既往药物史
     */
    public static final String PATIENT_PAST_DRUG_THERAPY = "be434fc2-2bd0-4cb2-a174-458ee19764aa";
    /**
     * 家族史
     */
    public static final String PATIENT_FAMILY_THERAPY = "bba6aa95-fafd-4487-a973-f5f7c89f5dad";
    /**
     * 现病史(原患疾病)
     */
    public static final String PRESENT_ILLNESS_HISTORY = "269de961-07c1-45eb-9095-3984d4d3d881";
    /**
     * 相关重要信息
     */
    public static final String RELATED_IMPORTANT_INFORMATION = "52A4D153-0D1B-47FE-BC05-CD584492ACA2";
    // endregion

    /**
     * DRUG CONTINUED
     */
    public static final String DRUG_CONTINUED_YES = "3464e6b0-358a-4ef5-911c-c2b7cd438ae0";
    public static final String DRUG_CONTINUED_NO = "a9992944-8d9d-4081-aaa8-ac4d9f4a6d08";

    /**
     * PARENTINFORATION
     */
    public static final String PARENT_INFORMATION = "28d57ad6-936e-4d06-8952-8d42ca92f775";

    /**
     * 是
     */
    public static final String YES = "3464e6b0-358a-4ef5-911c-c2b7cd438ae0";
    /**
     * 否
     */
    public static final String NO = "a9992944-8d9d-4081-aaa8-ac4d9f4a6d08";

    /**
     * ADR上报状态 上报成功
     */
    public static final String ADR_RECORD_STATUS_SUCCESS = "b51af9ee-083b-456b-ac7c-1595d3ab2a9e";
    public static final String ADR_RECORD_STATUS_FAIL = "3dbf7e2c-62a5-485b-ae74-383e4b479aff";
    public static final String ADR_RECORD_STATUS_SUBMIT = "57bf0bee-8bb3-400a-8f55-411517944603";
    public static final String ADR_RECORD_STATUS_SUBMISSION = "39f7e233-2a4a-4840-b339-7b5a655bfdde";//提交中
    public static final String ADR_RECORD_STATUS_NO_NEED_SUBMIT = "58f3f7ae-72cf-4967-a1e4-67d2bf29ae76";//无需提交
    /**
     * 相关病史 仍持续
     */
    public static final String MEDICAL_HISTORY_STILL_CONTINUE_YES = "9053d34d-ae77-4f36-a732-e9bff7892f8f";
    public static final String MEDICAL_HISTORY_STILL_CONTINUE_NO = "9053d34d-ae77-4f36-a732-e9bff7892f8f";
    public static final String MEDICAL_HISTORY_STILL_CONTINUE_UNKNOW = "9053d34d-ae77-4f36-a732-e9bff7892f8f";

    /**
     * 年龄单位
     */
    public static final String AGE_UNIT_TENYEAR = "a8858990-4679-4b6c-aba6-bfaf28f2248f";
    public static final String AGE_UNIT_WEEK = "d4dc1791-f3fd-47c1-b5b6-aab38bc2af83";
    public static final String AGE_UNIT_HOUR = "3433bd7c-934b-40af-8315-cede87f0577d";
    public static final String AGE_UNIT_AGE = "986638fb-7def-4df2-946b-ef539181be45";
    public static final String AGE_UNIT_MONTH = "37e87de9-f018-434e-899b-a15fc75ce5a6";
    public static final String AGE_UNIT_TENYEARS = "a8858990-4679-4b6c-aba6-bfaf28f2248f";
    public static final String AGE_UNIT_DAY = "a55af24b-d7d3-4754-92b1-55ad57abe35a";

    /**
     * 不良事件结果 死亡、后遗症、痊愈
     */
    public static final String EVENT_OUT_COME_DEATH = "32cb46e1-f9be-4eea-a12e-6a864f45c442";
    public static final String EVENT_OUT_COME_SEQUELA = "7c9da47a-da04-47d7-a716-55fedae62381";
    public static final String EVENT_OUT_COME_Recovery = "1d0d03b6-a55f-473f-9d10-d507dcd0501f";
    public static final String EVENT_OUT_COME_Unknow = "69270192-656e-4135-a32f-4aee5496462a";
    public static final String EVENT_OUT_COME_NotBetter = "94e06ee1-e0e9-489e-a5ff-e831bbe19d5a";
    public static final String EVENT_OUT_COME_Better = "e642625f-d30e-4ea9-b9e5-d11c57e1dd45";
    /**
     * 既往药品不良反应/事件 是、否、不详
     */
    public static final String PREVIOUS_DRUG_ADVERSE_REACTION_EVENT_YES = "5b7e8448-4d7f-4da5-9950-9e5795b5aaf4";
    public static final String PREVIOUS_DRUG_ADVERSE_REACTION_EVENT_NO = "87bc238b-a306-4b3e-8f44-3704bfaf40bb";
    public static final String PREVIOUS_DRUG_ADVERSE_REACTION_EVENT_UNKNOW = "e05c62bc-9312-45c2-8b93-fa0d72932444";

    /**
     * 家族药品不良反应/事件 是、否、不详
     */
    public static final String FAMILY_DRUG_REACTION_EVENT_HISTORY_YES = "0873372c-f953-417f-b743-3d695aee499c";
    public static final String FAMILY_DRUG_REACTION_EVENT_HISTORY_NO = "3ea8dbd2-81f4-4a67-adc3-c1087d23726c";
    public static final String FAMILY_DRUG_REACTION_EVENT_HISTORY_UNKNOW = "2a4db6e7-c3b4-4d4e-b665-77a154a5cf22";

    /**
     * 药品类型 怀疑药品、并用药品、治疗用药
     */
    public static final String DRUG_TYPE_DOUBT = "aaa14e3d-053a-4bee-9c91-9da75b20b9e4";
    public static final String DRUG_TYPE_MERGE = "e073d9c1-c4a9-4af1-a5c3-0675b40ddea6";
    public static final String DRUG_TYPE_Treatment = "f68a6421-9dab-4e7e-aa75-1d0948acc025";
    public static final String DRUG_TYPE_INTERACTING = "b2fbfd96-4a77-4fe4-b2ed-f869835e13a3";
    public static final String DRUG_TYPE_UNTREATED = "972152c5-5f49-4ef7-aacb-93ffbd28ef3c";

    /**
     * 收到报告类型 临床研究
     */
    public static final String RECEIVED_FROM_RESEARCH = "f6a10042-94d0-4f85-9dd7-d4452f676e3b";
    public static final String RECEIVED_FROM_FEEDBACK = "5f4e1f7b-1169-4894-8ec6-90439d99e013";
    public static final String RECEIVED_FROM_STUDY = "b8069829-7e30-41f2-9a5e-fb3e06033cf1";
    public static final String RECEIVED_FROM_PSP= "18d85d0d-8fb4-4545-9da9-193ecd3235e4";
    public static final String RECEIVED_FROM_DOCUMENT= "fdbafe04-925d-4c21-a6c5-cfead39bde54";
    public static final String RECEIVED_FROM_SPONTANEOUS= "289c1dc9-0ef7-4183-bf04-1dada7168228";
    /**
     * 重点监测项目
     */
    public static final String RECEIVED_FROM_INTENSIVE_MONITORING = "5a273355-e027-4b41-a26e-6cc653dee234";
    /**
     * 不详
     */
    public static final String RECEIVED_FROM_UNKNOWN = "d576f3c4-31a3-11e8-b467-0ed5f89f718b";
    /**
     * 很可能有关
     */
    public static final String CAUSALITY_PROBABLE = "215f1919-c154-11e8-a962-000c29399d7c";
    /**
     * 可能有关
     */
    public static final String CAUSALITY_POSSIBLE = "215fd28c-c154-11e8-a962-000c29399d7c";

    /**
     * 临床试验
     */
    public static final String CLINICAL_TRIALS = "3c63bc57-68b2-11e8-a602-000c29399d7c";
    /**
     * 个例患者使用
     */
    public static final String INDIVIDUAL_PATIENT_USE = "416f8f34-68b2-11e8-a602-000c29399d7c";
    /**
     * 其他研究
     */
    public static final String OTHER_STUDIES = "45273c20-68b2-11e8-a602-000c29399d7c";

    /**
     * 包含的联系方式
     */
    public static final String CONTACT_TELPHONE = "d8af1a44-add5-4e00-b389-0db5db7351e5";
    public static final String CONTACT_MAIL = "da8074e9-04d5-4d3e-979c-ed8d82440cff";
    public static final String CONTACT_FAX = "0b53de78-dcba-4cb8-bfc9-cc1153f768d8";

    /**
     * 是否尸检报告
     */
    public static final String AUTOPSY_REPORT_YES = "0c4917f2-ef9f-4999-84b8-f2cc13cfacaa";
    /**
     * 是否妊娠报告
     */
    public static final String PREGNANCY_REPORT_YES = "4019f36c-8e94-4b85-83ef-d29b6888d815";

    /**
     * 是否已知：已知，新的
     */
    public static final String REPORT_TYPE_ID_NEW = "9ada081f-c296-42a3-8db3-af69db03310e";
    public static final String REPORT_TYPE_ID_KNOW = "26df65fd-81b9-46e9-bab4-72f9c24b0070";

    /**
     * 首次报告
     */
    public static final String INIT_FIRST_ID = "ec63fd9c-b715-4571-96b8-ea43e562cb23";
    /**
     * 随访报告
     */
    public static final String INIT_FOLLOW_ID = "71e4a13f-36e1-426d-9379-019bb43ad1ff";

    /**
     * 过敏史
     */
    public static final String IMPORTANT_INFORMATION_ALLERGIC_HISTORY = "1959911e-faf1-463a-b8a7-8681f69b223e"; // 过敏史
    public static final String OTHER_HISTORY = "6ba88b09-f16d-487f-b3f3-645961f35bce";
    public static final String RESEARCH_DRUG_YES = "ebf73feb-3b56-495d-a40f-d74e540b3665";
    public static final String RESEARCH_DRUG_NO = "18a29dc8-370b-4870-948b-4c1e89ff6e02";

    /**
     * 产品信息上市前、上市后
     */
    public static final String SALE_MARKET = "31b4edf3-ea43-41eb-9b13-b61416752ee8";
    public static final String PREV_MARKET = "a5d3b874-165d-499c-9239-59dd1bcc5ed1";

    /**
     * 临床研究分类 4期、上市后研究
     */
    public static final String Phase_One = "cd0bc739-9707-457e-9f5f-355cbb7eb164";
    public static final String Phase_Two = "264f628e-41b9-4c1a-9aae-670196901c84";
    public static final String Phase_Three = "34786ac2-8614-4ea9-996c-cc6699c7bbd6";
    public static final String Phase_Four = "3f475b8a-6370-48b9-921e-f7ac031ea344";
    public static final String Phase_Bioequivalence = "585d4828-a44d-40ca-a8bd-fb3e5ae3dfd6";
    public static final String Phase_Research = "084da2ba-e2e3-4fcd-a51f-8882148f3305";
    public static final String Postmarketing_Research = "eac36752-1c48-4b36-ae0a-5236a4260673";

    /**
     * 是否IME
     */
    public static final String IME_YES = "60f8a1d7-4198-4d93-ae9f-a65c442acc13";
    public static final String IME_NO = "9c43cf59-7823-4c1e-838e-2283d6dfa9a0";

    /**
     * 严重性标准 - 其他重要医学事件
     */
    public static final String OTHER_MEDICALLY_IMPORTANT_CONDITION = "9eaec5c4-b294-41bc-89e3-5ed11a37b573";
    public static final String DISABLING_INCAPACITATING = "946d48cc-8e7f-4bc3-87c8-30381baa16cf";
    public static final String DISABLING = "6D4C0D2A-C870-48BC-804D-58D2194BB0DD";
    public static final String Disfunctioning = "C434A50D-6C7F-4998-BA82-57AB9A2410EA";
    public static final String Caused_ProlongedHospitalisation = "bd401bd2-ee7e-4212-b935-38c76e305c61";
    public static final String Prolonged_Hospitalisation = "4ff18274-4b74-4221-8383-fec4908ce3b4";
    public static final String Hospitalization = "42b843fb-7f0d-4697-857a-bd2752e0ef12";

    public static final String E2B_DETAIL_TASK_INIT = "78b06732-2162-43b0-bbbc-2377fce87670";
    public static final String E2B_DETAIL_TASK_REJECT = "c2e725db-7e47-482a-abdd-37d8497c5539";

    public static final String OTHERHISTORY_CONTINUE_NO="eaf4249d-3edf-4e18-979a-c6f459cec512";
    public static final String OTHERHISTORY_CONTINUE_YES="9053d34d-ae77-4f36-a732-e9bff7892f8f";
    public static final String OTHERHISTORY_CONTINUE_UK="077e4230-31b0-11e8-b467-0ed5f89f718b";
    /**
     * 原始资料 文件分类 原始资料
     */
    public static final String RAWDATA_FILECLASSIFY_SourceData = "7CBF308D-1E15-472C-9FF0-130D215811A4";
    /**
     * 原始资料 文件分类 文献报道
     */
    public static final String RAWDATA_FILECLASSIFY_Document = "FFF43B77-B0BD-4A93-BFEB-D67A1CF93934";

    /**
     * 原始资料 文件来源 其他系统
     */
    public static final String RAWDATA_SOURCE_OtherSystem = "573E2DD9-7F1A-4CE7-B429-2E7EBB494067";

    public static final String SOURCE_INFO_OTHER = "6b7b2dc4-657c-45cf-8936-904809b33105";

    // region 去激发
    // 是
    public static final String QJF_Yes = "42cc439a-ca11-45cf-b5ad-d20477c0ae95";
    // 否
    public static final String QJF_No = "62cac41a-0f1a-4665-863f-6413f08b7b89";
    // 不明
    public static final String QJF_Unknow = "bc0db3f2-78a4-4cbf-9bb4-23f63c959b61";
    // 去激发 不适用
    public static final String QJF_Not = "57fecb0f-8c91-4f05-81cd-a18bf76f2927";
    // endregion

    // region 在激发
    //是
    public static final String ZJF_Yes = "b97f4e09-d982-4cd8-a3a8-0cf01c0c8a72";
    // 否
    public static final String ZJF_No = "8ce98713-777f-4509-8293-530f4b644ad6";
    // 不明
    public static final String ZJF_Unknow = "15dfc078-3f34-4ad8-8070-3eeabde336b2";
    // 不适用
    public static final String ZJF_Not = "9d6d4271-c70b-45b6-86c3-b6d7f4fa92df";
    // endregion

    // region 严重性标准
    // 致死 1
    public static final String Severity_Death = "da176568-14b3-415f-8472-290c6811109e";
    // 危及生命 2
    public static final String Severity_LifeThreatening = "8cf65505-9385-4d08-9ea2-f0c1e4124f90";
    // 住院/住院时间延长 5
    public static final String Severity_Hospitalisation = "bd401bd2-ee7e-4212-b935-38c76e305c61";
    // 致残/致功能丧失 4
    public static final String Severity_Disabling = "946d48cc-8e7f-4bc3-87c8-30381baa16cf";
    // 先天异常/出生缺陷 3
    public static final String Severity_CongenitalAnomaly = "d826e01b-5d7b-4728-9f34-fe479666636a";
    // 其他重要医学事件 6
    public static final String Severity_OtherMedically = "9eaec5c4-b294-41bc-89e3-5ed11a37b573";
    // endregion

    // region 企业信息来源
    // 医疗机构
    public static final String SourceInfo_Medical_institution = "dd217a8c-f228-4c1c-8e1b-bd1c2ea8669b";
    // 文献报道
    public static final String SourceInfo_Literature_report = "868d9ffc-427f-4818-b898-1776486128e4";
    // 上市后研究
    public static final String SourceInfo_PostMarket_study = "ea3ea96b-cfca-4110-a2f3-f0912febd72f";
    // 其他
    public static final String SourceInfo_other = "6b7b2dc4-657c-45cf-8936-904809b33105";
    // 经营企业
    public static final String SourceInfo_OperatingEnterprises = "1ecf2111-332e-4351-a93a-d51965aeba8d";
    // 个人
    public static final String SourceInfo_Personal = "41de195d-47f9-405f-80b0-3248af6f559c";
    // endregion

    // region report import config
    public static final String Report_Import_Foreign = "78CD18F8-7184-4847-81C2-674AB13056AD";
    // endregion

    // region sae reported docestic
    public static final String SAE_Docestic_No = "caa42aa3-5742-4bad-aab4-6091c26f23eb";
    public static final String SAE_Docestic_Yes = "e89e17cc-d846-44e3-b461-57d0efde9131";
    public static final String SAE_Docestic_Unknow = "5f7da630-8841-4443-b5b1-f8efb6652142";
    // endregion

    // region product type
    public static final String Product_Chinese_Medicine = "254507dc-8c8c-49b2-a237-b0898543c5d4";
    public static final String Product_Biologics = "6bd0d2d0-4f7b-4bee-b8e1-f694bce4494a";
    public static final String Product_Chemicals = "6fe2dd80-74bf-47fd-a08f-cf07b5832b5c";
    public static final String Product_Prophylaxis = "b569cea3-a635-4c7d-9b1d-ec98453b8841";
    public static final String Product_Other = "4754d863-ae70-41e4-b514-08d24df892eb";
    // endregion

    // region 对可疑药物采取的措施
    public static final String Drug_Continued = "8b6be177-608d-4cf5-9e01-f28a320d9644";
    public static final String Drug_Reduced = "759685e0-ee77-417c-b5f5-04cc8b652477";
    public static final String Drug_Interrupted = "3026be53-3510-4e68-9e74-546ec1f81d21";
    public static final String Drug_Discontinued = "c58a67dc-b157-4647-b927-44fba7d29c6a";
    public static final String Drug_Increase = "06b1f665-736e-4913-8f04-b4cad5a9b6a9";
    public static final String Drug_Suspend_Medication = "f8017f38-e89b-455e-8f66-66ea9abd8ce6";
    public static final String Drug_Unkown = "0242546D-FC56-464E-A7BC-F5A38ADFACAD";
    public static final String Drug_Dose_Not_Support = "A6C5A653-2999-4C02-A713-96DFD0FCF62F";
    // endregion

    // region blinded 盲态
    public static final String Drug_Blinded = "d0bcdaff-3c39-4339-ba28-a1d49c38fb85";
    public static final String Drug_Blinded_Not = "d0bcdaff-3c39-4339-ba28-a1d49c38fb85";
    // endregion

    // region 企业报告类型
    public static final String Company_Report_Type_Medication = "c306312e-858e-46e3-8b8b-53e684c5681b";
    public static final String Company_Report_Type_Operating = "a8263798-1a5c-4f5c-9c5f-1b367adffbc1";
    public static final String Company_Report_Type_Production = "0a8a70bd-ea0d-4066-9dba-5bea2919233e";
    public static final String Company_Report_Type_Person = "6944e1c7-3f13-4ca6-828b-3c53f8411dd2";
    public static final String Company_Report_Type_Other = "bbdc4a82-4d02-4e44-b790-0fedd8e25fb6";
    // endregion

    // region gender
    public static final String Gender_Female = "fa28e508-29ba-4b89-9b44-5330eb343878";
    public static final String Gender_Male = "c51dbff1-dbee-4209-ac41-703c4526317e";
    //

    // region Degree of severity
    public static final String Severity_Mild = "e9c84b2a-379a-4a71-80f8-7e79af18abca";
    public static final String Severity_Moderate  = "8a478707-851e-40c3-92ab-d737a5f746cc";
    public static final String Severity_Severe = "97a176ec-428d-4d80-9d3d-cf5ec8d454bb";
    // endregion

    // region Pregnancy result
    public static final String Pregnancy_Fulltime = "7544066f-42fd-453f-a5ab-c7468821e0f8";
    public static final String Pregnancy_Termination = "76b27d55-207a-453a-9fff-f590e59e934e";
    public static final String Pregnancy_PreMature = "877acbdd-2fec-4399-ba99-4111333bc5c7";
    public static final String Pregnancy_StillBirth = "95cf367f-b806-4843-9f3e-64727b856f54";
    public static final String Pregnancy_OverdueBirth = "cec02e46-0e3f-4917-abfe-02f562bf7900";
    public static final String Pregnancy_Inutero = "fc52fc0d-e46c-4a84-badd-842eb6e43cec";
    public static final String Pregnancy_Spontaneous_abortion = "fd99b671-d787-450b-b108-6b060f5eae08";
    // endregion

    // region 生产方式
    public static final String Vaginal = "7a06c12e-ac0f-45db-8d89-af043244e49e";
    public static final String Cesarean = "be3cd833-372f-4aef-8baf-1315401efa6f";
    // endregion

    // region is continue
    public static final String IsContinued_NO = "6b19bb1b-4029-4fc9-8dce-019483ce088b";
    public static final String IsContinued_YES = "bec77f80-fbee-46eb-8127-564b088fb282";
    // endregioin

    public static final String Report_Severity_Ser = "9bc127ed-822d-4527-b80f-f7e69fe7f0da";

    // region 相关重要信息
    public static final String Importantmedicalstatus_Pregnancy_History = "02507353-32ff-46a8-a84e-17d87b1b80d5";
    public static final String Importantmedicalstatus_Allergic_History = "1959911e-faf1-463a-b8a7-8681f69b223e";
    public static final String Importantmedicalstatus_Alcohol_History = "2fca39b3-4a24-49ee-a3ff-d7a975d3cef6";
    public static final String Importantmedicalstatus_Smoking_History = "2fff63f8-cd1f-43cf-be07-d86a60f68148";
    public static final String Importantmedicalstatus_Kidney_Disease = "62b0c60f-46bf-403a-b68c-5b70b38bcf9b";
    public static final String Importantmedicalstatus_other = "6ba88b09-f16d-487f-b3f3-645961f35bce";
    public static final String Importantmedicalstatus_Hepatitis_History = "df4ef22d-5113-4eac-99f6-29f6824bbbd2";
    // endregion

    public static final String SMOKE_IMPORT = "2fff63f8-cd1f-43cf-be07-d86a60f68148";
    public static final String ALCOLH_IMPORT = "2fca39b3-4a24-49ee-a3ff-d7a975d3cef6";
    public static final String ALLYN_IMPORT = "1959911e-faf1-463a-b8a7-8681f69b223e";
    public static final String SUYN_IMPORT = "6ba88b09-f16d-487f-b3f3-645961f35bce";
    public static final String OTHER_IMPORT = "6ba88b09-f16d-487f-b3f3-645961f35bce";


    // region medwatch reportsource itemclassid: 42fd9933-629f-4a49-8d00-908bb091a1d9
    /**
     * 国外
     */
    public static final String MEDWATCH_REPORTSOURCE_OVERSEAS = "aa5a3dc8-e6cd-4712-a09e-a689bb86c60a";

    /**
     * 研究
     */
    public static final String MEDWATCH_REPORTSOURCE_RESEARCH = "94610f56-3219-4a12-b052-40470d923add";

    /**
     * 文献
     */
    public static final String MEDWATCH_REPORTSOURCE_DOCUMENT = "b2e9d9f1-a263-40ae-8ca4-a658877238e2";

    /**
     * 消费者 consumer
     */
    public static final String MEDWATCH_REPORTSOURCE_CONSUMER = "6072530a-00e8-448d-80e6-a8090029704a";

    /**
     * 卫生专业人员 Health professional
     */
    public static final String MEDWATCH_REPORTSOURCE_HEALTHPROFESSIONAL = "391eb7b9-cf2e-4884-ab32-f20450ecbb2a";

    /**
     * 用户设施 User facility
     */
    public static final String MEDWATCH_REPORTSOURCE_USERFACILITY = "9c1c6217-0d6d-48f4-a822-3f6467366590";

    /**
     * 公司代表 Company representatives
     */
    public static final String MEDWATCH_REPORTSOURCE_COMPANYREPRESENTATIVES = "bb12901c-fde3-4845-a8de-81e791e5b0c8";

    /**
     * 分销商 Distributor
     */
    public static final String MEDWATCH_REPORTSOURCE_DISTRIBUTOR = "9857bdf8-5af9-483c-806b-3249679eb20f";

    /**
     * 其他 other
     */
    public static final String MEDWATCH_REPORTSOURCE_OTHER = "1b46105c-1b4c-4414-87fb-d3607c2348e4";
    // endregion

    // region 报告者同样将报告发送给FDA
    /**
     * 是
     */
    public static final String ISSENTFDA_YES = "16425102-8045-4a75-a180-0114e2d8c95d";

    /**
     * 否
     */
    public static final String ISSENTFDA_NO = "7dc508bc-1959-4067-8643-f0be17c60f1a";

    /**
     * 不详
     */
    public static final String ISSENTFDA_UK = "09016cf6-f75e-4a13-b19c-70896e5a47d0";
    // endregion

    // region tm_report_unblinding_log unblinding_status itemclassid: d65793aa-4adf-44fb-823b-2302a2ba9e3b
    /**
     * 破盲申请中
     */
    public static final String TM_REPORT_UNBLINDING_LOG_UNBLINDING_STATUS_APPROVING = "ec11e04e-cd43-4799-87fa-cb2050bd1778";
    /**
     * 破盲处理中
     */
    public static final String TM_REPORT_UNBLINDING_LOG_UNBLINDING_STATUS_UNBLINDING = "68139157-35c9-477e-9d94-9a7b8b682a53";
    /**
     * 已破盲
     */
    public static final String TM_REPORT_UNBLINDING_LOG_UNBLINDING_STATUS_FINISH = "1063d9d9-907a-43e6-82c9-466c88e45284";
    /**
     * 驳回
     */
    public static final String TM_REPORT_UNBLINDING_LOG_UNBLINDING_STATUS_REJECTED = "d4cb728a-65e6-4975-9aa3-898cc4dc5e96";
    // endregion

    // region tm_report_unblinding_log unblinding_type itemclassid: f4ad2314-4f55-41eb-a055-14b5f05b1e6f
    /**
     * 在线
     */
    public static final String TM_REPORT_UNBLINDING_LOG_UNBLINDING_TYPE_ONLINE = "176959f7-5991-4ad0-954a-d9e6601a63d1";
    /**
     * 邮件
     */
    public static final String TM_REPORT_UNBLINDING_LOG_UNBLINDING_TYPE_OFFLINE = "251b4da2-54b3-4041-bc71-9133f8d7e994";
    // endregion



    // region ethnicity
    /**
     * 西班牙裔/拉丁裔
     */
    public static final String ETHNICITY_YES = "d5bc568e-ad07-11e8-b5b6-000c29399d7c";

    /**
     * 不是西班牙裔/拉丁裔
     */
    public static final String ETHNICITY_NO = "8320fb86-ad0d-11e8-b5b6-000c29399d7c";
    // endregion

    // region racial
    /**
     * 美洲印第安人或阿拉斯加原住民
     */
    public static final String RACE_AMERICAN = "e50d1b82-ad0c-11e8-b5b6-000c29399d7c";

    /**
     * 夏威夷原住民或其他太平洋岛民
     */
    public static final String RACE_PACIFIC = "07658b93-ad0f-11e8-b5b6-000c29399d7c";

    /**
     * 黑人或非裔美国人
     */
    public static final String RACE_AFRICAN = "90486dc2-ad0c-11e8-b5b6-000c29399d7c";

    /**
     * 亚洲
     */
    public static final String RACE_ASIAN = "ae71c41e-ad0b-11e8-b5b6-000c29399d7c";

    /**
     * 白人
     */
    public static final String RACE_EUROPEAN = "f5a17710-ad0e-11e8-b5b6-000c29399d7c";
    // endregion

    // region 报告者职业
    /**
     * 护士
     */
    public static final String NURSE = "0cf0a60d-bda0-4b42-8e02-fbf5aabcfc13";

    /**
     * 其他医务人员
     */
    public static final String OTHER_HCP = "f622dd90-31a3-11e8-b467-0ed5f89f718b";

    /**
     * 医生
     */
    public static final String DOCTOR = "7ade006f-461b-4bb1-84ca-d3d16d777d91";

    /**
     * 药师
     */
    public static final String PHARMACIST = "cc0f6306-becc-41fb-b947-fc4c36115a65";

    /**
     * 律师
     */
    public static final String LAWYER = "ae6424d5-624e-11e8-9882-000c29399d7c";

    /**
     * 法规部门
     */
    public static final String REGULATORY_AUTHORITY = "fe65f13a-832e-4a60-8156-210044c60a44";

    /**
     * 消费者或非医务人员
     */
    public static final String CONSUMER_OR_NON_HCP = "4043d302-31a4-11e8-b467-0ed5f89f718b";

    /**
     * 其他
     */
    public static final String REPORT_CAREER_OTHER = "e405e56a-4e35-435c-b947-eeafea9ee28c";
    // endregion

    //begindrugstarteventintervalunit4
    public static final String REPORT_FIRSTSTART_DRUG_EVENT_INTERVAL_UNIT_YEAR  = "f0282dd7-7d50-4377-98d0-41e8cbeda71f";
    public static final String REPORT_FIRSTSTART_DRUG_EVENT_INTERVAL_UNIT_MONTH = "1aae8a89-264b-4fdb-923b-1d544d974b08";
    public static final String REPORT_FIRSTSTART_DRUG_EVENT_INTERVAL_UNIT_DAY   = "d8c61f99-acd1-44ce-8cfa-0e9168350fa5";
    public static final String REPORT_FIRSTSTART_DRUG_EVENT_INTERVAL_UNIT_HOUR  = "32910629-e674-4ecf-a519-1806f43c8a57";
    public static final String REPORT_FIRSTSTART_DRUG_EVENT_INTERVAL_UNIT_MINUTE = "b94be051-f68c-44eb-b4c9-96a795229dd7";
    public static final String REPORT_FIRSTSTART_DRUG_EVENT_INTERVAL_UNIT_SECOND = "659355ef-e640-4759-ac3e-5ed87d578d1f";
    public static final String REPORT_FIRSTSTART_DRUG_EVENT_INTERVAL_UNIT_WEEK = "36155cf9-8c83-45c8-bc0e-79a61515a1ac";
    public static final String REPORT_FIRSTSTART_DRUG_EVENT_INTERVAL_UNIT_THREEMONTH = "05b0be54-7d60-4a04-bb5b-5006492b6e23";
    public static final String REPORT_FIRSTSTART_DRUG_EVENT_INTERVAL_UNIT_TENYEAR = "2b707d25-a26f-4322-a89f-d7356920f28c";

    //lastdrugstarteventintervalunit
    public static final String REPORT_LASTEND_DRUG_EVENT_INTERVAL_UNIT_YEAR  = "b9cb26c3-0a0c-4d4a-9432-63620dbd6bce";
    public static final String REPORT_LASTEND_DRUG_EVENT_INTERVAL_UNIT_MONTH = "abfa6ea0-6e90-44e3-b206-4992d70c866d";
    public static final String REPORT_LASTEND_DRUG_EVENT_INTERVAL_UNIT_DAY   = "7c3f4d9f-d819-4b86-a5cf-143498a0d908";
    public static final String REPORT_LASTEND_DRUG_EVENT_INTERVAL_UNIT_HOUR  = "e2884f6a-646b-4bbf-8339-5f773e696dd7";
    public static final String REPORT_LASTEND_DRUG_EVENT_INTERVAL_UNIT_MINUTE = "ec73f644-fa1e-4a73-8759-0c523c106df0";
    public static final String REPORT_LASTEND_DRUG_EVENT_INTERVAL_UNIT_SECOND = "d9411c84-859d-4246-ae33-86ce339b7f12";
    public static final String REPORT_LASTEND_DRUG_EVENT_INTERVAL_UNIT_WEEK = "ed53c65a-c4c2-41ed-b2cb-2211d42144f0";
    public static final String REPORT_LASTEND_DRUG_EVENT_INTERVAL_UNIT_THREEMONTH = "9cef59ac-11a0-4c80-8874-cec3979339d9";
    public static final String REPORT_LASTEND_DRUG_EVENT_INTERVAL_UNIT_TENYEAR = "54b12fb7-f194-44bc-8893-20207a86899e";

    /**
     * 报告相关
     */
    public static final String REPORT_CORRELATION_TRUE = "96425825-49fd-4454-9587-8bc194f65f51";
    /**
     * 报告不相关
     */
    public static final String REPORT_CORRELATION_FALSE = "21afdb09-05cf-4a2e-a834-fd7d90947420";


    // region reporterevaluationdcitkey
    // Marshford法
    public static final String REPORTEREVALUATIONDCITKEY_MARSHFORD = "13af65e7-c161-11e8-a962-000c29399d7c";
    // ADR法
    public static final String REPORTEREVALUATIONDCITKEY_ADR = "1e5e6c69-c161-11e8-a962-000c29399d7c";
    // Naranjo法
    public static final String REPORTEREVALUATIONDCITKEY_NARANJO = "26880227-c161-11e8-a962-000c29399d7c";
    // 六分选项法
    public static final String REPORTEREVALUATIONDCITKEY_SIX = "2def0998-c161-11e8-a962-000c29399d7c";
    // 二分选项法
    public static final String REPORTEREVALUATIONDCITKEY_TWO = "365533d2-c161-11e8-a962-000c29399d7c";
    // SAE法
    public static final String REPORTEREVALUATIONDCITKEY_SAE = "3e2d0479-c161-11e8-a962-000c29399d7c";
    // 七分选项法
    public static final String REPORTEREVALUATIONDCITKEY_SEVEN = "4896b014-c161-11e8-a962-000c29399d7c";
    // WHO-UMC法
    public static final String REPORTEREVALUATIONDCITKEY_WHO_UMC = "53a1cc01-c161-11e8-a962-000c29399d7c";
    // endregion
    /**
     * 反馈数据导出
     */
    public static final String FEED_BACK_CONFIG_EXPORT_ENGLISH = "8258c057-4c86-11e9-8df3-000c29ee981b";
    /**
     * 反馈数据导入配置ID ADR
     */
    public static final String FEED_BACK_CONFIG_ADR_ID = "c9552785-7cfb-49c6-a717-113edcc06e5c";
    // 历史 5FE349E1-B614-4351-ABB2-98FE09FD6D07
    /**
     * 反馈导入疫苗模板 ADR
     */
    public static final String FEED_BACK_CONFIG_VACCINE_ADR_ID = "2760d8a2-dc0e-11e8-a236-000c29ee981b";
    /**
     * 反馈导入药物模板 ADR
     */
    public static final String FEED_BACK_CONFIG_DRUG_ADR_ID = "d9cd5372-01f7-11e9-a885-000c29ee981b";
    /**
     * 反馈导入模板 疾控中心
     */
    public static final String FEED_BACK_CONFIG_VACCINE_CDC_ID = "0b5aef8d-d2ad-11e8-a962-000c29399d7c";
    /**
     * 相关
     */
    public static final String CORRELATION_TRUE =  "2162f72c-c154-11e8-a962-000c29399d7c";
    /**
     * 不相关
     */
    public static final String CORRELATION_FALSE = "2162ee06-c154-11e8-a962-000c29399d7c";
    /**
     * 逻辑是
     */
    public static final String YES_UNIQUECODE="3464e6b0-358a-4ef5-911c-c2b7cd438ae0";

    // region 公司因果评价
    // 肯定有关
    public static final String COMPANY_CAUSALITY_CERTAIN = "215f6cca-c154-11e8-a962-000c29399d7c";
    // 可能有关
    public static final String COMPANY_CAUSALITY_POSSIBLE = "215fd28c-c154-11e8-a962-000c29399d7c";
    // 无法判断
    public static final String COMPANY_CAUSALITY_UNASSESSABLE = "215e12c5-c154-11e8-a962-000c29399d7c";
    // 可能无关
    public static final String COMPANY_CAUSALITY_UNLIKELY = "215fcfc3-c154-11e8-a962-000c29399d7c";
    // 肯定无关
    public static final String COMPANY_CAUSALITY_CERTAINLYNOT = "215fce44-c154-11e8-a962-000c29399d7c";
    //待评价
    public static final String COMPANY_CAUSALITY_REMAIN="215fbba5-c154-11e8-a962-000c29399d7c";
    //无法评价
    public static final String COMPANY_CAUSALITY_INVALUABLY="f08c863f-c2c6-11e8-a962-000c29399d7c";
    // endregion


    /**
     * 报告首位发送者
     * 主管机构
     */
    public static final String FIRST_SENDER_REGULATOR = "3891ec54-624e-11e8-9882-000c29399d7c";
    /**
     * 报告首位发送者
     * 其他
     */
    public static final String FIRST_SENDER_OTHER = "3d10489c-624e-11e8-9882-000c29399d7c";

    /**
     * 诊断结果 其他
     */
    public static final String CLINICAL_DIAGNOSIS_OTHER = "6f1362d1-d12e-11e8-a962-000c29399d7c";

    /**
     * 产品分类 --疫苗
     */
    public static final String PRODUCT_CATEGORY_VACCINE = "a68d3fbf-9908-4661-8d93-d66b68b4de63";
    /**
     * 产品分类 --药品
     */
    public static final String PRODUCT_CATEGORY_DRUG = "ca8e6376-278b-4fbd-82f4-f2808f85349c";

    public static final String AGE_GROUP_FETUS = "45ff29d8-fe9f-4740-bb37-cc34c2f3e392";

    /**
     * 年
     */
    public static final String PARENT_AGE_UNIT_YEAR = "c6062aed-6251-11e8-9882-000c29399d7c";
    /**
     * 十年
     */
    public static final String PARENT_AGE_UNIT_DECADE = "cf96e9b6-6251-11e8-9882-000c29399d7c";

    // region 杨森随机qc
    /**
     * 杨森随机qc—随机方式-比例
     */
    public static final String REPORT_QC_LOG_RANDOMTYPE_PERCENTAGE = "dabda6e2-8fd4-423b-ac18-3fda37a8f09e";
    /**
     * 杨森随机qc—随机方式-数量
     */
    public static final String REPORT_QC_LOG_RANDOMTYPE_NUMBER = "a03ef25f-d30f-47a9-af10-9c1e93c635b4";
    /**
     * 杨森随机qc—未被随机到的报告处理方式-提交至复合提交
     */
    public static final String REPORT_QC_LOG_SUBMIT_Y = "18d306bb-5c10-42d1-91d9-36e0d24f0338";
    /**
     * 杨森随机qc—未被随机到的报告处理方式-暂不提交
     */
    public static final String REPORT_QC_LOG_SUBMIT_N = "8e7ae546-e154-40ea-a459-33aaf31df1f7";
    // endregion

    // region cioms task 状态
    /**
     * 未完成
     */
    public static String CIOMS_TASK_UNFINISH = "3578607a-6bfd-418a-90e4-e3ca8728e43c";
    /**
     * 新建报告
     */
    public static String CIOMS_TASK_CREATEREPORT = "dd22b627-7f98-4acd-b3c7-d9786e8e77d3";
    /**
     * 创建新版本
     */
    public static String CIOMS_TASK_CREATENEWVERSION = "9058ee54-c02a-4e30-a417-a7f49a5da5ce";
    /**
     * 拒绝导入
     */
    public static String CIOMS_TASK_REJECT = "0635d0da-2840-41b4-81f8-5217c3b17ccb";
    // endregion

    /**
     * 发热（腋温°C）范围：
     */
    public static final String FEVER_RANGE_ONE="aea82960-d1b9-11e8-a962-000c29399d7c";//_37.1_37.5
    public static final String FEVER_RANGE_TWO="aea82bff-d1b9-11e8-a962-000c29399d7c";//_37.6-38.5
    public static final String FEVER_RANGE_THREE="aea82e2a-d1b9-11e8-a962-000c29399d7c";//≥38.6
    public static final String FEVER_RANGE_NONE="aea83052-d1b9-11e8-a962-000c29399d7c";//无

    /**
     * 局部红肿(直径cm)范围：
     */
    public static final String FEVER_SWELLING_RANGE_ONE="3cc19047-d1b9-11e8-a962-000c29399d7c";//≤2.5
    public static final String FEVER_SWELLING_RANGE_TWO="3cc192a8-d1b9-11e8-a962-000c29399d7c";//2.6-5.0
    public static final String FEVER_SWELLING_RANGE_THREE="3cc19553-d1b9-11e8-a962-000c29399d7c";//＞5.0
    public static final String FEVER_SWELLING_RANGE_NONE="3cc1979b-d1b9-11e8-a962-000c29399d7c";//无

    /**
     * 局部硬结(直径cm)范围
     */
    public static final String LOCALINDURATION_RANGE_ONE="0145eed9-d1b9-11e8-a962-000c29399d7c";//≤2.5
    public static final String LOCALINDURATION_RANGE_TWO="0145f163-d1b9-11e8-a962-000c29399d7c";//2.6-5.0
    public static final String LOCALINDURATION_RANGE_THREE="0145f380-d1b9-11e8-a962-000c29399d7c";//＞5.0
    public static final String LOCALINDURATION_RANGE_NONE="0145f593-d1b9-11e8-a962-000c29399d7c";//无

    /**
     * 初步分类
     */
    public static final String THEGENERALRESPONSE ="67fb5d90-d04a-11e8-a962-000c29399d7c";//一般反应
    public static final String UNDETERMINED="67fb6176-d04a-11e8-a962-000c29399d7c";//待定（其他）

    /**
     * 反应获得方式
     */
    public static final String PASSIVE_MONITORING_REPORT  ="c03869fd-d040-11e8-a962-000c29399d7c";//被动监测报告
    public static final String ACTIVE_MONITORING_REPORT="56e9f41b-d041-11e8-a962-000c29399d7c";//主动监测报告
    /**
     *做出结论的组织
     */
    public static final String MEDICAL_ASSOCIATION ="e0c3ded2-d042-11e8-a962-000c29399d7c";//医学会
    public static final String DIAGNSTIC_EXPERT_GROUP="1b51e430-d043-11e8-a962-000c29399d7c";//调查诊断专家组
    public static final String CDC="22fa65f6-d043-11e8-a962-000c29399d7c";//疾控机构
    public static final String MEDICAL_INSTITUTIONS ="2c05aff6-d043-11e8-a962-000c29399d7c";//医疗机构
    public static final String THE_VACCINATION_UNITS="3319809f-d043-11e8-a962-000c29399d7c";//接种单位

    /**
     *组织级别
     */
    public static final String PROVINCIAL_LEVEL ="5d03d1f1-d043-11e8-a962-000c29399d7c";//省级
    public static final String CITY_LEVEL="5d063abc-d043-11e8-a962-000c29399d7c";//市级
    public static final String COUNTY_LEVEL ="5d085100-d043-11e8-a962-000c29399d7c";//县级
    public static final String TOWNSHIP ="5d0a7476-d043-11e8-a962-000c29399d7c";//乡级
    public static final String VILLAGE_LEVEL="5d0cf58b-d043-11e8-a962-000c29399d7c";//村级
    /**
     *反应分类
     */
    public static final String THE_GENERAL ="e5ec4827-d043-11e8-a962-000c29399d7c";//一般反应
    public static final String PARADOXICAL="e5ec4c98-d043-11e8-a962-000c29399d7c";//异常反应
    public static final String VACCINE_QUALITY ="e5ec503b-d043-11e8-a962-000c29399d7c";//疫苗质量事故
    public static final String INOCULATION ="e5ec53bd-d043-11e8-a962-000c29399d7c";//接种事故
    public static final String COULPED_DISEASE="e5ec5746-d043-11e8-a962-000c29399d7c";//偶合症
    public static final String PSYCHOGENIC_REACTION="e5ec5b09-d043-11e8-a962-000c29399d7c";//心因性反应
    public static final String DETERMINED ="e5ec5f1c-d043-11e8-a962-000c29399d7c";//待定

    /**
     * 中国
     */
    public static final String COUNTRY_CN = "COUNTRY_CN";
    /**
     * 美国
     */
    public static final String COUNTRY_US = "COUNTRY_US";
    /**
     * 台湾
     */
    public static final String COUNTRY_TW = "COUNTRY_TW";
    /**
     * 中国递交方式
     */
    public static final List<String> CN_SUBMIT_TYPE =  Arrays.asList("adr","cde");
    /**
     * 美国递交方式
     */
    public static final List<String> US_SUBMIT_TYPE =  Arrays.asList("fda_cder","fda_faers");
    /**
     * 欧盟递交方式
     */
    public static final List<String> EU_SUBMIT_TYPE =  Arrays.asList("evpm","evctm");
    /**
     * 周期性
     */
    public static final String UNIT_INTERVAL_UNIT_CYCLICAL = "5c941e76-0b01-4348-92e0-58aee9ccf4ad";
    /**
     * 必要性
     */
    public static final String UNIT_INTERVAL_UNIT_NECESSARY = "5c941e76-0b01-4348-92e0-58aee9ccf4ad";
    /**
     * 总剂量
     */
    public static final String UNIT_INTERVAL_UNIT_TOTAL = "5c941e76-0b01-4348-92e0-58aee9ccf4ad";

    /**
     *pv_common_english：英文
     */
    public static final String PV_COMMON_ENGLISH_I18N = "pv_common_english";
    /**
     *pv_common_chinese：中文
     */
    public static final String PV_COMMON_CHINESE_I18N = "pv_common_chinese";

    public static final String EXPORT_WORD_TABLE_START="tableStart:";
    public static final String EXPORT_WORD_CHEECKBOX_YES="23966fbf-1d71-11e9-a99b-000c29399d7c";
    public static final String EXPORT_WORD_CHEECKBOX_NO="23966fbf-1d71-11e9-a99b-123c29299d7c";
    //研究后揭盲
    public static final String POST_STUDY="7AEA9A4D-F74D-4AFF-9C81-F95187F69D8F";
    //研究者揭盲
    public static final String INVESTIGATOR="EE53342C-00B3-4E65-8ADD-8AD1C9DBC4EB";
    //盲态
    public static final String BLINDED="d0bcdaff-3c39-4339-ba28-a1d49c38fb85";
    //非盲态
    public static final String NON_BLIND="B60FA369-1BB0-4438-96ED-891B2547D4B9";
    //申办方揭盲
    public static final String SPONSOR_UNBLINDING="2195F75C-EF55-4EE8-857B-7FA270039708";
    //双盲
    public static final String DOUBLE_BLIND="d1804746-eeb2-4e02-ba9a-d7d4dfb8726d";
    //项目状态进行中
    public static final String PROJECT_STATUS_ONGOING = "66c38aef-ac05-11e8-87d1-000c293171ca";
    //adr对象名既往药品不良反应
    public static final String SYSTEMPREVDRUGEVENT="systemprevdrugevent";
    //adr对象名相关疾病
    public static final String SYSTEMRELATEDDISEASE="systemrelateddisease";
    //adr对象名怀疑药物合并药物
    public static final String SYSTEMDRUG="systemDrug";
    //adr对象名使用原因
    public static final String DISEASELIST="diseaseList";
    //adr对象名剂量信息
    public static final String SYSTEMTMDRUGDOSEDTLIST="systemtmDrugDoseDtList";
    //adr对象名不良反应
    public static final String EVENTLIST="eventList";
    //adr对象名死亡原因
    public static final String CAUSEOFDEATH="死亡原因";
    //adr对象名尸检结果
    public static final String AUTOPSYRESULTS="尸检结果";
    //adr对象名关联性评价
    public static final String SYSTEMCAUSALITYLIST="systemCausalityList";
    //adr对象名实验室检查
    public static final String SYSTEMLABDATALIST="systemLabDataList";
    //adr对象名报告
    public static final String SYSTEMREPORT="systemReport";
    //adr对象患者信息
    public static final String SYSTEMPATIENT="systempatient";
}
