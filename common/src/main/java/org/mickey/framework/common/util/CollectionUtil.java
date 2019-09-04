package org.mickey.framework.common.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger logger = LoggerFactory.getLogger(getClass());

    public static <T> void add(List<T> list, T instance) {
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(instance);
    }

    public static boolean isNotEmpty(@Nullable Collection<?> collection) {
        return !org.springframework.util.CollectionUtils.isEmpty(collection);
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
        return collection.parallelStream().map(mapper).collect(Collectors.toList());
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
}
