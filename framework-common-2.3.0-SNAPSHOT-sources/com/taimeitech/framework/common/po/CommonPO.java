package com.taimeitech.framework.common.po;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Date;

/**
 * @author thomason
 * @version 1.0
 * @since 2017/9/8 上午9:32
 */
public abstract class CommonPO implements Serializable, Cloneable {
	/**
	 * 主键 为了兼容 老数据,将主键长度该长
	 */
	@Column(columnDefinition = "varchar(50) not null comment '主键'", nullable = false, updatable = false)
	@Id
	protected String id;
	/**
	 * 数据版本号
	 */
	@Column(nullable = false, updatable = false, columnDefinition = "bigint not null default 0 comment '版本号'")
	@Version
	protected Long version;
	/**
	 * 创建者
	 */
	@Column(updatable = false, columnDefinition = "varchar(50) comment '创建者Id'")
	protected String createBy;
	/**
	 * 创建时间
	 */
	@Column(updatable = false, columnDefinition = "datetime(6) comment '创建时间'")
	protected Date createTime;
	/**
	 * 最后修改者
	 */
	@Column(columnDefinition = "varchar(50) comment '最后修改人Id'")
	protected String updateBy;
	/**
	 * 更新时间
	 */
	@Column(columnDefinition = "datetime(6) comment '最后修改时间'")
	protected Date updateTime;
	/**
	 * isDeleted 标记
	 */
	@Column(nullable = false, columnDefinition = "tinyint not null default 0 comment '删除标记 0 未删除 1 删除'")
	protected Integer isDeleted;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
}
