package org.mickey.framework.common.po;

import io.swagger.annotations.ApiModelProperty;
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
public abstract class AbstractCommonPo implements Serializable, Cloneable {

    @ApiModelProperty(value = "主键，新增时不需要传值 系统自动生成，更新及删除时为必填参数", position = 1)
    @NotNull(message = "id 不能为空", groups = Groups.Update.class)
    @Null(message = "id不需要传值", groups = Groups.Insert.class)
    @Id
    @Column(updatable = false, columnDefinition = "varchar(36) not null comment 'primary key id value will removed -'")
    protected String id;

    @ApiModelProperty(value = "is deleted ; 0 false , 1 true", position = 95)
    @Column(columnDefinition = "tinyint not null default 0 comment 'is deleted ; 0 false , 1 true'")
    protected Integer isDeleted;

    @ApiModelProperty(value = "不需要传值，系统自动维护", position = 96)
    @Version
    @Column(updatable = false, columnDefinition = "bigint not null default 0 comment 'auto inspector'")
    protected Long version;

    @ApiModelProperty(value = "不需要传值，系统自动获取当前登录的用户ID", position = 97)
    @Column(updatable = false, columnDefinition = "varchar(36) comment 'create by'")
    protected String createBy;

    @ApiModelProperty(value = "不需要传值，系统自动获取当前服务器时间", position = 98)
    @Column(updatable = false, columnDefinition = "datetime(6) comment 'create time'")
    protected Date createTime;

    @ApiModelProperty(value = "不需要传值，系统自动获取当前登录的用户ID", position = 99)
    @Column(insertable = false, columnDefinition = "varchar(36)")
    protected String updateBy;

    @ApiModelProperty(value = "不需要传值，系统自动获取当前服务器时间", position = 100)
    @Column(insertable = false, columnDefinition = "datetime(6)")
    protected Date updateTime;

    public Date getCreateTime() {
        return (Date) this.createTime.clone();
    }

    public void setCreateTime(Date createTime) {
        this.createTime = (Date) createTime.clone();
    }

    public Date getUpdateTime() {
        return (Date) this.updateTime.clone();
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = (Date) updateTime.clone();
    }

    // region generate Geter and Seter code

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
    // endregion
}
