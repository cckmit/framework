package org.mickey.framework.e2b.dto.e2b;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * description
 *
 * @author mickey
 * 2020-02-21
 */
@Slf4j
@Data
public class E2bR3CompanyInfoDto {
    /**
     * 批量信息类型
      */
    private int batchMessageType;
    /**
     * 批号
     */
    private String batchNumber;
    //批量（信息）发送者标识符
    private String batchSenderIdentifier;
    //批量（信息）接收者标识符
    private String batchReceiverIdentifier;
    //批次传输日期
    private String batchTransmissionDate;
    //信息标识符
    private String messageIdentifierPrefix;
    //信息发送者标识符
    private String messageSenderIdentifier;
    //信息接收者标识符
    private String messageReceiverIdentifier;
    // 信息创建日期
    private String messageCreateDate;
    //发送者的（病例）安全报告唯一标识码
    private String senderSafetyReportIdentifier;
    //创建时间
    private String createDate;
    //发送者类型
    private String senderType;
    //发送者所在机构
    private String senderOrganization;
    //发送者所在部门
    private String senderDepartment;
    //发送者的职位名称
    private String senderJobName;
    //发送者的名字
    private String senderGivenName;
    //发送者的中间名字
    private String senderMiddleName;
    //发送者的姓氏
    private String senderFamilyName;
    //发送者的街道地址
    private String senderStreetAddress;
    //发送者所在城市
    private String senderCity;
    //发送者所在州或省
    private String senderProvince;
    //发送者所在地区的邮政编码
    private String senderZipCode;
    //发送者的国家代码
    private String senderCountryCode;
    //发送者的电话号码
    private String senderPhoneNumber;
    //发送者的传真号码
    private String senderFaxNumber;
    //发送者的邮箱地址
    private String senderEmail;
    /**
     * 国家
     */
    private String countryCode;
    /**
     * 机构国际唯一缩写/识别码
     */
    private String organizationInternationalCode;
}
