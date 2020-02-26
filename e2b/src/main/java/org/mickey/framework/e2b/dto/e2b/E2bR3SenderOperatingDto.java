package org.mickey.framework.e2b.dto.e2b;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * description
 *
 * @author mickey
 * 2020-02-23
 */
@Slf4j
@Data
public class E2bR3SenderOperatingDto {
    private String id;
    //是否默认，1：默认
    private Integer isDefault;
    //发送者类型 itemClassId:426321ce-6865-11e8-a602-000c29399d7c
    private String senderType;
    //发送者类型名
    private String senderTypeName;
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

    private String senderOrganizationEn;
    private String senderDepartmentEn;
    private String senderJobNameEn;
    private String senderGivenNameEn;
    private String senderMiddleNameEn;
    private String senderFamilyNameEn;
    private String senderStreetAddressEn;
    private String senderCityEn;
    private String senderProvinceEn;
}
