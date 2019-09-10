package org.mickey.framework.example.po;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.po.BasePo;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Data
@Table(name = "pv_meeting")
public class Meeting extends BasePo {
    @Column(columnDefinition = "varchar(20) not null comment '发起人姓名 required'")
    private String sponsorName;
    @Column(columnDefinition = "varchar(10) default null comment '发起人角色'")
    private String sponsorRole;
    @Column(columnDefinition = "varchar(50) default null comment '发起人企业'")
    private String sponsorCompany;
    @Column(columnDefinition = "varchar(50) not null comment '会议名称 required'")
    private String meetingName;
    @Column(columnDefinition = "varchar(500) default null comment '会议资料'")
    private String meetingFile;
    @Column(columnDefinition = "varchar(500) not null comment '会议人员邮箱，邮箱之间用 , 分割开 required'")
    private String emails;
    @Column(columnDefinition = "datetime not null comment '会议开始时间'")
    private String startDate;
    @Column(columnDefinition = "datetime not null comment '会议结束时间'")
    private String endDate;
}
