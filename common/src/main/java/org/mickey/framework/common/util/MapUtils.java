package org.mickey.framework.common.util;

import lombok.extern.slf4j.Slf4j;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * map工具
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class MapUtils {

    private MapUtils() {

    }

    public static int getInitialCapacity(int expectedSize) {
        return (int) ((float) expectedSize / 0.75F + 1.0F);
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    /**
     * 将map转换为字符串map
     * @param map 原始map
     * @param keyConverter key的转换字符串方法
     * @param valueConverter value的转换字符串方法
     * @param <K> 原始map中的key类型
     * @param <V> 原始map中的value类型
     * @return 转换后的字符串map
     */
    public static <K, V> Map<String, String> toStringMap(Map<K, V> map, Function<K, String> keyConverter, Function<V, String> valueConverter) {
        if (map == null) {
            return null;
        }
        if (map.isEmpty()) {
            return new HashMap<>(0);
        }
        Map<String, String> newStringMap = new HashMap<>(getInitialCapacity(map.size()));
        map.forEach((k, v) -> newStringMap.put(keyConverter.apply(k), valueConverter.apply(v)));
        return newStringMap;
    }

    /**
     * map类型转换
     * @param map 需要转换的map
     * @param keyConverter key的转换方法
     * @param valueConverter value的转换方法
     * @param <K> 原始map中的key类型
     * @param <V> 原始map中的value类型
     * @param <J> 转换后的key类型
     * @param <U> 转换后的value类型
     * @return 转换后的map
     */
    public static <K, V, J, U> Map<J, U> converter(Map<K, V> map, Function<K, ? extends J> keyConverter, Function<V, ? extends U> valueConverter) {
        if (map == null) {
            return null;
        }
        if (map.isEmpty()) {
            return new HashMap<>(0);
        }
        Map<J, U> newStringMap = new HashMap<>(getInitialCapacity(map.size()));
        map.forEach((k, v) -> newStringMap.put(keyConverter.apply(k), valueConverter.apply(v)));
        return newStringMap;
    }

    /**
     * 将object转换为map
     * @param object object实例
     * @return map
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Map<String, Object> toMap(Object object) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        if (object == null) {
            return null;
        }

        BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

        Map<String, Object> map = new HashMap<>(getInitialCapacity(propertyDescriptors.length));
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = getter != null ? getter.invoke(object) : null;
            map.put(key, value);
        }

        return map;
    }
}
