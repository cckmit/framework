//package com.taimeitech.framework.common.cache;
//
//
//import com.fasterxml.jackson.core.type.TypeReference;
//
//import java.util.Objects;
//
//public abstract class CacheResult<T> {
//	protected TypeReference<T> valueType;
//	protected String placeHolder;
//	protected String cachedJson;
//
//	public CacheResult(TypeReference<T> valueType, String cachedJson) {
//		this(valueType, CacheConst.EMPTY_VALUE_PLACEHOLDER, cachedJson);
//	}
//
//	public CacheResult(TypeReference<T> valueType, String placeHolder, String cachedJson) {
//		this.valueType = valueType;
//		this.placeHolder = placeHolder;
//		this.cachedJson = cachedJson;
//	}
//
//	//在缓存中取到的是空占位符
//	public boolean isEmpty() {
//		return Objects.equals(cachedJson, placeHolder);
//	}
//
//	//缓存中没有值
//	public boolean existed() {
//		return cachedJson != null;
//	}
//
//	public String getCachedJson() {
//		return cachedJson;
//	}
//
//	/**
//	 * 先调用 existed 和 isEmpty
//	 *
//	 * @return
//	 */
//	public abstract T getObject();
//}
