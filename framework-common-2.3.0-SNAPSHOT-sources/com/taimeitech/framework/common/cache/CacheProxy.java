//package com.taimeitech.framework.common.cache;
//
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.taimeitech.framework.common.SystemContext;
//import org.apache.commons.lang.StringUtils;
//
//import java.util.List;
//import java.util.Set;
//
//import static com.taimeitech.framework.common.cache.CacheConst.CacheItemMetaPrefix;
//import static com.taimeitech.framework.common.cache.CacheConst.DefaultRegion;
//
//@Deprecated
//public interface CacheProxy {
//
//	<T> void put(CacheItem<T> cacheItem, String region);
//
//	/**
//	 * 无失效时间
//	 *
//	 * @param key
//	 * @param value
//	 * @param region
//	 * @param <T>
//	 */
//	<T> void put(String key, T value, String region);
//
//	/**
//	 * 带失效时间
//	 *
//	 * @param key
//	 * @param value
//	 * @param expirationMode
//	 * @param expirationTime
//	 * @param region
//	 * @param <T>
//	 */
//	<T> void put(String key, T value, ExpirationMode expirationMode, long expirationTime, String region);
//
//	String get(String key, String region);
//
//	<T> CacheResult<T> get(String key, TypeReference<T> typeReference, String region);
//
//	boolean exists(String key, String region);
//
//	void remove(String key, String region);
//
//	void remove(List<String> keys, String region);
//
//	void clear(String region);
//
//	void clearPattern(String pattern, String region);
//
//	void expire(String key, long seconds, ExpirationMode mode, String region);
//
//	Set<String> getAllKeys(String pattern, String region);
//
//
//	default String generateMetaKey(String key, String region) {
//		return CacheItemMetaPrefix + ":" + (StringUtils.isBlank(region) ? DefaultRegion : region) + ":" + SystemContext.getTenantId() + ":" + key;
//	}
//
//	default String generateCacheKey(String key, String region) {
//		return StringUtils.isBlank(region) ? DefaultRegion : region + ":" + SystemContext.getTenantId() + ":" + key;
//	}
//}
