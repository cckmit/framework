package org.mickey.framework.common.util;

import com.alibaba.druid.support.json.JSONUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class JsonUtil {

    public static void print(Object object) {
        log.info(JSONUtils.toJSONString(object));
    }
}
