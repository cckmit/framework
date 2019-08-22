package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
}
