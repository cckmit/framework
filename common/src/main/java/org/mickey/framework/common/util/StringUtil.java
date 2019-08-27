package org.mickey.framework.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class StringUtil extends StringUtils {
    public static String formatPath(String url) {
        if (url == null) {
            return null;
        }
        return url.replace("/+/", "/");
    }
}
