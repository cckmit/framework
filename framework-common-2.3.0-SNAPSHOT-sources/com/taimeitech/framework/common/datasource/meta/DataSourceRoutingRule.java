package com.taimeitech.framework.common.datasource.meta;


import com.taimeitech.framework.common.datasource.readwriteisolate.ReadWriteStrategy;

import java.io.Serializable;

/**
 * @author thomason
 * @version 1.0
 * @since 2017/12/7 下午1:03
 */
public class DataSourceRoutingRule implements Serializable {
	private ReadWriteStrategy strategy;
	private String routingKey;
	private String routingValue;
	private AllocateType allocateType;//来源
	private DataSourceMetaInfo metaInfo;

	public ReadWriteStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(ReadWriteStrategy strategy) {
		this.strategy = strategy;
	}

	public String getRoutingValue() {
		return routingValue;
	}

	public void setRoutingValue(String routingValue) {
		this.routingValue = routingValue;
	}

	public AllocateType getAllocateType() {
		return allocateType;
	}

	public void setAllocateType(AllocateType allocateType) {
		this.allocateType = allocateType;
	}

	public DataSourceMetaInfo getMetaInfo() {
		return metaInfo;
	}

	public void setMetaInfo(DataSourceMetaInfo metaInfo) {
		this.metaInfo = metaInfo;
	}

	public String getRoutingKey() {
		return routingKey;
	}

	public void setRoutingKey(String routingKey) {
		this.routingKey = routingKey;
	}
}
