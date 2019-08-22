package com.taimeitech.framework.common.datasource.meta;

import org.apache.zookeeper.data.Stat;

public class ZNode<T> {

	private Stat stat;

	private T data;

	public ZNode(Stat stat, T data) {
		this.stat = stat;
		this.data = data;
	}

	public Stat getStat() {
		return stat;
	}

	public T getData() {
		return data;
	}
}
