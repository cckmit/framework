package org.mickey.framework.common.po;

import lombok.Data;
import org.mickey.framework.common.groups.Groups;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;

/**
 * 抽象的基础common po类
 *
 * @author mickey
 * @date 2020-01-10
 */
@Data
public abstract class AbstractCommonPo implements Serializable, Cloneable {
    
    /**
     * 主键，新增时不需要传值 系统自动生成，更新及删除时为必填参数
     */
    @NotNull(message = "id 不能为空", groups = Groups.Update.class)
    @Null(message = "id不需要传值", groups = Groups.Insert.class)
    @Id
    @Column(updatable = false, columnDefinition = "varchar(36) not null comment 'primary key id value will removed -'")
    protected String id;
    
    /**
     * is deleted ; 0 false , 1 true
     */
    @Column(columnDefinition = "tinyint(1) not null default 0 comment 'is deleted ; 0 false , 1 true'")
    protected Boolean isDeleted;
    
    /**
     * 系统自动维护
     */
    @Version
    @Column(updatable = false, columnDefinition = "bigint not null default 0 comment 'auto inspector'")
    protected Long version;
    
    /**
     * 不需要传值，系统自动获取当前登录的用户ID
     */
    @Column(updatable = false, columnDefinition = "varchar(36) comment 'create by'")
    protected String createBy;
    
    /**
     * 不需要传值，系统自动获取当前服务器时间
     */
    @Column(updatable = false, columnDefinition = "datetime(6) comment 'create time'")
    protected Date createTime;
    
    /**
     * 不需要传值，系统自动获取当前登录的用户ID
     */
    @Column(insertable = false, columnDefinition = "varchar(36)")
    protected String updateBy;
    
    /**
     * 不需要传值，系统自动获取当前服务器时间
     */
    @Column(insertable = false, columnDefinition = "datetime(6)")
    protected Date updateTime;

}
