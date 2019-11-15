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
@Table
public class MeetingCheckIn extends BasePo {
    @Column(columnDefinition = "varchar(36) not null comment '会议ID'")
    private String meetingId;
    @Column(columnDefinition = "varchar(50) not null comment '签到人'")
    private String name;
    @Column(columnDefinition = "varchar(50) default null comment '部门'")
    private String department;
    @Column(columnDefinition = "varchar(50) default null comment '职务'")
    private String title;
    @Column(columnDefinition = "datetime not null comment '签到时间'")
    private String checkedTime;
}
