//package com.taimeitech.framework.org.mickey.framework.dbinspector.common.cache;
//
//
//public class CacheItem<T> {
//
//	private String key;
//	private T value;
//	private long timeout; //seconds
//	private ExpirationMode expirationMode;
//
//	public CacheItem(String key, T value) {
//		this(key, value, ExpirationMode.NONE, 0);
//	}
//
//	public CacheItem(String key, T value, ExpirationMode expirationMode, long timeout) {
//		this.key = key;
//		this.value = value;
//		this.timeout = timeout;
//		this.expirationMode = expirationMode;
//	}
//
//	public String getKey() {
//		return key;
//	}
//
//	public T getValue() {
//		return value;
//	}
//
//	public long getTimeout() {
//		return timeout;
//	}
//
//	public ExpirationMode getExpirationMode() {
//		return expirationMode;
//	}
//
//}