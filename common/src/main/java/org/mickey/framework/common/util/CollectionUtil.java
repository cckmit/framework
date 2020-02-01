package org.mickey.framework.common.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * description
 *
 * @author mickey
 * 05/07/2019
 */
public class CollectionUtil {

    public static boolean isNotEmpty(@Nullable Collection<?> collection) {
        return !CollectionUtils.isEmpty(collection);
    }

    public static <R> void forEach(Collection<R> collection, Consumer<? super R> action){
        if (CollectionUtil.isNotEmpty(collection)) {
            collection.forEach(action);
        }
    }

    public static <R> void forEach(Collection<R> collection, BiConsumer<Integer, ? super R> action){
        if (CollectionUtil.isNotEmpty(collection)) {
            int index = 0;
            for (R t : collection) {
                action.accept(index,t);
                index++;
            }
        }
    }

    public static <V> List<V> selectNonNullToList(Collection<V> collection, Predicate<V> function) {
        if (null == collection) {
            return null;
        }
        List<V> collect = collection.parallelStream().filter(function).collect(Collectors.toList());
        return collect;
    }

    public static <R> R firstOrDefault(Collection<R> collection, Predicate<R> function, R defaultReturnObject) {
        if (CollectionUtils.isEmpty(collection)) {
            return defaultReturnObject;
        }
        Optional<R> rOptional;
        if (function == null) {
            rOptional = collection.parallelStream().findFirst();
        } else {
            rOptional = collection.parallelStream().filter(function).findFirst();
        }
        return rOptional.isPresent() ? rOptional.get() : defaultReturnObject;
    }

    public static <T, R> List<R> mapNonNullToList(Collection<T> collection, Function<? super T, ? extends R> mapper) {
        if (null == collection) {
            return null;
        }
        return collection.parallelStream().map(mapper).distinct().collect(Collectors.toList());
    }

    public static <R> Boolean any(Collection<R> collection, Predicate<? super R> predicate) {
        return collection.parallelStream().anyMatch(predicate);
    }

    public static <R>boolean all(Collection<R> collection,Predicate<R> function) {
        return collection.parallelStream().allMatch(function);
    }

    public static List intersection (List... params){
        if (params.length==0) {
            return null;
        }
        if (params.length==1) {
            return params[0];
        }

        Set a = Sets.newHashSet(params[0]);
        for (int i=1;i<params.length;i++){
            Set b = Sets.newHashSet(params[i]);
            a = Sets.intersection(a, b);
        }
        return Lists.newArrayList(a);
    }

    /**
     * 两集合的交集 返回第一个集合交集部分
     * @param list
     * @param other_list
     * @param combiner
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T,R> List<T> intersection(List<T> list,List<T> other_list, Function<T, R> combiner){
        if(CollectionUtil.isNotEmpty(list)&&CollectionUtil.isNotEmpty(other_list)){
            //to map
            Map<R, T> otherMap = other_list.stream().filter(t -> combiner.apply(t) != null).filter(t -> t != null).collect(Collectors.toMap(combiner, t -> t, (o, o2) -> o));
            return list.stream().filter(t -> otherMap.containsKey(combiner.apply(t))).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 两集合的差集 返回第一个集合差集部分
      * @param list
     * @param other_list
     * @param combiner
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T,R> List<T> reduce(List<T> list,List<T> other_list, Function<T, R> combiner){
        if(CollectionUtil.isNotEmpty(list)){
            if(!CollectionUtil.isNotEmpty(other_list)){
                return list;
            }
            //to map
            Map<R, T> otherMap = other_list.stream().filter(t -> combiner.apply(t) != null).filter(t -> t != null).collect(Collectors.toMap(combiner, t -> t, (o, o2) -> o));
            return list.stream().filter(t -> !otherMap.containsKey(combiner.apply(t))).collect(Collectors.toList());
        }
        return null;
    }
}
