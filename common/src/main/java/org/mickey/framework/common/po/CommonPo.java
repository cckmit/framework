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
    @Column(updatable = false, columnDefinition = "varchar(36) not null comment 'primary key id value will removed -'")
    protected String id;

    @Version
    @Column(updatable = false, columnDefinition = "bigint not null default 0 comment 'auto inspector'")
    protected Long version;

    @Column(updatable = false, columnDefinition = "varchar(36) comment 'create by'")
    protected String createBy;

    @Column(updatable = false, columnDefinition = "datetime(6) comment 'create time'")
    protected Date createTime;

    @Column(columnDefinition = "varchar(36)")
    protected String updateBy;

    @Column(columnDefinition = "datetime(6)")
    protected Date updateTime;

    @Column(columnDefinition = "tinyint not null default 0 comment 'is deleted ; 0 false , 1 true'")
    protected Integer isDeleted;
}
