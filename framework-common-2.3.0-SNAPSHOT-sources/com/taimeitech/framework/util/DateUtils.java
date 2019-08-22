package com.taimeitech.framework.util;

import com.taimeitech.framework.common.SystemContext;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.TimeZone;

/**
 * @author thomason
 * @version 1.0
 * @since 2018/6/19 下午1:49
 */
public class DateUtils {
	private static Logger logger = LoggerFactory.getLogger(DateUtils.class);

	public static String format(Date date, String... patterns) {
		TimeZone timeZone = SystemContext.getTimeZone();
		if (patterns != null && patterns.length > 0) {
			for (String pattern : patterns) {
				try {
					return DateFormatUtils.format(date, pattern, timeZone);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return null;
	}
}
