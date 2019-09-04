package org.mickey.framework.common.util;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class MapUtils {
    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static <K, V> Map<String, String> toStringMap(Map<K, V> map, Function<K, String> keyConverter, Function<V, String> valueConverter) {
        if (map == null) {
            return null;
        }
        if (map.isEmpty()) {
            return new HashMap<>();
        }
        Map<String, String> newStringMap = new HashMap<>();
        map.forEach((k, v) -> newStringMap.put(keyConverter.apply(k), valueConverter.apply(v)));
        return newStringMap;
    }

    public static <K, V, J, U> Map<J, U> converter(Map<K, V> map, Function<K, ? extends J> keyConverter, Function<V, ? extends U> valueConverter) {
        if (map == null) {
            return null;
        }
        if (map.isEmpty()) {
            return new HashMap<>();
        }
        Map<J, U> newStringMap = new HashMap<>();
        map.forEach((k, v) -> newStringMap.put(keyConverter.apply(k), valueConverter.apply(v)));
        return newStringMap;
    }
}
