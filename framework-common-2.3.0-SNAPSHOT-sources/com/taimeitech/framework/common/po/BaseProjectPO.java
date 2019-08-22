package com.taimeitech.framework.common.po;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * 所有的实体类的父类，集成了一些公用属性
 *
 * @author Thomason
 */
@SuppressWarnings("serial")
public abstract class BaseProjectPO extends BasePO implements Serializable, Cloneable {
	/**
	 * 租户Id
	 */
	@Column(columnDefinition = "varchar(50) not null comment '项目Id'")
	protected String projectId;

	public BaseProjectPO() {
	}

	@Override
	public String toString() {
		return "BaseProjectPO{" +
				"projectId='" + projectId + '\'' +
				", tenantId='" + tenantId + '\'' +
				", id='" + id + '\'' +
				", version=" + version +
				", createBy='" + createBy + '\'' +
				", createTime=" + createTime +
				", updateBy='" + updateBy + '\'' +
				", updateTime=" + updateTime +
				", isDeleted=" + isDeleted +
				'}';
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
}
