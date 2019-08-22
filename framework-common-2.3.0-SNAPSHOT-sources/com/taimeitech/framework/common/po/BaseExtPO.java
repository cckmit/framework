package com.taimeitech.framework.common.po;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Thomason
 * @version 1.0
 * @since 2017/6/27 10:40
 */
public abstract class BaseExtPO extends BasePO implements Serializable {
	/**
	 * 扩展属性
	 */
	protected Map<String, Object> extMap = new HashMap<>();

	public Map<String, Object> getExtMap() {
		return extMap;
	}

	public void setExtMap(Map<String, Object> extMap) {
		this.extMap = extMap;
	}
}
