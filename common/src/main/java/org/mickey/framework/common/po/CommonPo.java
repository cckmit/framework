package org.mickey.framework.common.po;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Date;

@Data
public abstract class CommonPo implements Serializable, Cloneable {

    @Id
    @Column(nullable = false, updatable = false, columnDefinition = "varchar(32) not null comment 'primary key id value will removed -'")
    protected String id;

    @Version
    @Column(nullable = false, updatable = false, columnDefinition = "bigint not null default 0 comment 'auto inspector'")
    protected Long version;

    @Column(updatable = false, columnDefinition = "varchar(36)")
    protected String createBy;

    @Column(updatable = false, columnDefinition = "datetime(6)")
    protected Date createTime;

    @Column(columnDefinition = "varchar(36)")
    protected String updateBy;

    @Column(columnDefinition = "datetime(6)")
    protected Date updateTime;

    @Column(nullable = false, columnDefinition = "tinyint not null default 0")
    protected Integer isDeleted;
}
