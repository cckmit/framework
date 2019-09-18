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
@Table(name = "kangce_job")
public class JobPo extends BasePo {
    @Column(columnDefinition = "varchar(50) not null comment '应用id'")
    private String appId;
    @Column(columnDefinition = "varchar(50)")
    private String name;
    @Column(columnDefinition = "varchar(200) default null comment 'job 描述'")
    private String description;
    @Column(columnDefinition = "varchar(20) not null comment 'corn 表达式; 在线生成 http://cron.qqe2.com/ '")
    private String cron;
    @Column(columnDefinition = "varchar(200) null default null comment 'GET 请求参数'")
    private String parameter;
    @Column(columnDefinition = "varchar(10) default 'OPEN' comment '设置为OPEN/CLOSE且只有该值为OPEN才会执行该Job'")
    private String status;
    @Column(columnDefinition = "varchar(200) not null comment '定时任务回调接口'")
    private String url;
    @Column(columnDefinition = "varchar(10) not null default 'GET' comment 'http 请求方式；默认：GET'")
    private String httpMethod;
    @Column(columnDefinition = "varchar(200) null default null comment 'http headers'")
    private String httpHeaders;
}
