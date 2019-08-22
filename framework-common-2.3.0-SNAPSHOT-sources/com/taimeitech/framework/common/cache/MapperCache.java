//package com.taimeitech.framework.common.cache;
//
//import java.util.List;
//
//public interface MapperCache {
//	<T> CacheResult<T> get(Class<T> clazz, String primaryKey);
//
//	<T> void set(Class<T> clazz, String primaryKey, T value);
//
//	<T> void setEmpty(Class<T> clazz, String primaryKey);
//
//	<T> void remove(Class<T> clazz, String primaryKey);
//
//	<T> void remove(Class<T> clazz, List<String> primaryKeys);
//
//	<T> void remove(Class<T> clazz);
//
//	String generateCacheKey(CacheMetaData cacheMetaData, Class<?> clazz, String primaryKey);
//}
