package org.mickey.framework.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class StringUtil extends StringUtils {

    private static final String SQL_REGEX = "('.+--)|(--)|(\\|)|(%7C)";

    public static String formatPath(String url) {
        if (url == null) {
            return null;
        }
        return url.replace("/+/", "/");
    }

    public static String escapeSql(String sql) {
        if (sql == null) {
            return null;
        }
        return sql.replaceAll(SQL_REGEX, "");
    }

    public static String fixDecimalExponent(String dString) {
        int ePos = dString.indexOf('E');

        if (ePos == -1) {
            ePos = dString.indexOf('e');
        }

        if (ePos != -1) {
            if (dString.length() > (ePos + 1)) {
                char maybeMinusChar = dString.charAt(ePos + 1);

                if (maybeMinusChar != '-' && maybeMinusChar != '+') {
                    StringBuilder strBuilder = new StringBuilder(dString.length() + 1);
                    strBuilder.append(dString.substring(0, ePos + 1));
                    strBuilder.append('+');
                    strBuilder.append(dString.substring(ePos + 1, dString.length()));
                    dString = strBuilder.toString();
                }
            }
        }

        return dString;
    }

    public static String consistentToString(BigDecimal decimal) {
        if (decimal == null) {
            return null;
        }

        return decimal.toString();
    }

    public static String valueOf(Object object) {
        if (object == null) {
            return "";
        }
        return object.toString();
    }

    public static int stringToInt(String str) {
        int value = 0;
        if (str == null || str.length() == 0) {
            return value;
        }

        try {
            value = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            log.warn("int转换错误:" + e.getMessage());
        }

        return value;
    }

    public static String camelToUnderline(String s) {
        if (s == null || "".equals(s.trim())) {
            return "";
        }
        int len = s.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append("_");
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
