package org.mickey.framework.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class BeanUtil {
    public static <T> T convert(Object obj, Class<? extends T> clazz) {
        if (obj == null) {
            return null;
        }
        T instantiateClass = BeanUtils.instantiateClass(clazz);
        BeanUtils.copyProperties(obj, instantiateClass);
        return instantiateClass;
    }
}
