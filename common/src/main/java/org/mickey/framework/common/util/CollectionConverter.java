package org.mickey.framework.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class CollectionConverter {

    private CollectionConverter() {

    }

    public static <K, T> Map<K, List<T>> groupListToMap(List<T> list, Function<? super T, ? extends K> classifier){
        if (list != null) {
            Map<K, List<T>> listMap = list.stream().collect(Collectors.groupingBy(classifier));
            return listMap;
        }
        return null;
    }

    public static <R> List<R> listToLists(List<? extends Object> source, Class<R> classzz) {
        if (null == source) {
            return null;
        }
        List result = new ArrayList();



        CollectionUtil.forEach(source, item -> {
            try {
                Object o = classzz.newInstance();
                BeanUtils.copyProperties(item, o);
                result.add(o);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return result;
    }

    public static <K, V> Set<K> list2Set(Collection<V> sourceList, Function<? super V, ? extends K> classifier) {
        if (null != sourceList) {
            Set<K> set = sourceList.parallelStream().map(classifier).collect(Collectors.toSet());
            return set;
        }
        return null;
    }

    public static <K, V> Set<K> list2Set(Collection<V> sourceList, String key) {
        if (null != sourceList) {
            Set set = sourceList.parallelStream().map(i -> ReflectionUtil.getPropertyValue2String(i, key)).collect(Collectors.toSet());
            return set;
        }
        return null;
    }

    public static <K, V> Map<K, V> list2Map(Collection<V> sourceList, ListToMapConverter<K, V> converter) {
        Map<K, V> newMap = new HashMap<K, V>(sourceList.size());
        for (V item : sourceList) {
            newMap.put(converter.getKey(item), item);
        }
        return newMap;
    }

    public static String buildCombineKey(String[] strings) {
        return null;
    }

    public static <T> String buildCombineKey(T t, String[] keys) {
        StringBuilder combineKey = new StringBuilder();
        for (String key : keys) {
            Object o;
            try {
                o = PropertyUtils.getProperty(t, key);
                combineKey.append(o == null ? "" : o.toString()).append("_");
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                log.error(e.getMessage(), e);
            }
        }
        return combineKey.toString();
    }

    /**
     * 转换为map对象
     *
     * @param list
     * @param keys
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> listToMap(List<T> list, String[] keys) {
        if (list != null) {
            Map<String, T> m = new HashMap<>(list.size());
            for (T t : list) {
                String combineKey = buildCombineKey(t, keys);
                m.put(combineKey, t);
            }
            return m;
        } else {
            return null;
        }
    }

    public static <T, K, U> Map<K, U> listToMap(List<T> list, java.util.function.Function<? super T, ? extends K> keyMapper,
                                                java.util.function.Function<? super T, ? extends U> valueMapper) {
        if(CollectionUtils.isEmpty(list)){
            return new HashMap<>(0);
        }
        return list.stream().filter(t -> keyMapper.apply(t) != null).filter(t -> valueMapper.apply(t) != null).collect(Collectors.toMap(keyMapper, valueMapper, (o, o2) -> o));
    }


    /**
     * list to map converter
     * @param <K> key
     * @param <V> value
     */
    public interface ListToMapConverter<K, V> {
        /**
         * item of list get key
         * @param item item
         * @return key
         */
        K getKey(V item);
    }
}
