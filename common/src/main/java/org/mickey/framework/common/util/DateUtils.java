package org.mickey.framework.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.mickey.framework.common.SystemConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class DateUtils {

    static Logger logger = LoggerFactory.getLogger(DateUtils.class);

    public static final String HMS = "HHmmss";
    public static final String YMD = "yyyyMMdd";
    public static final String ZYMD = "yyyy年MM月dd日";
    public static final String YM = "yyyyMM";
    public static final String Y = "yyyy";
    public static final String YMDHMS = "yyyyMMddHHmmss";
    public static final String datefullPattern = "yyyyMMdd HH:mm:ss";
    public static final String datefullPatternWithLine = "yyyy-MM-dd HH:mm:ss";
    public static final String fullPatternSSS = "yyyyMMddHHmmssSSS";
    public static final String YDMW = "yyyy-MM-dd";
    public static final String YMW = "yyyy-MM";
    public static final String YYYYMMDDX = "yyyy/MM/dd";
    public static final String YYYYMMDDX_HHmmss = "yyyy/MM/dd HH:mm:ss";
    public static final String DDMMYYYYX = "dd/MM/yyyy";
    public static final String DDMMYYYYX_HHmmss = "dd/MM/yyyy HH:mm:ss";
    public static final Pattern PATTERN_YYYYMMDDW = Pattern.compile("([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-9])))");
    public static final Pattern PATTERN_YYYYMMDDX = Pattern.compile("([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})/(((0[13578]|1[02])/(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)/(0[1-9]|[12][0-9]|30))|(02/(0[1-9]|[1][0-9]|2[0-9])))");
    public static final Pattern PATTERN_YYYYMDX = Pattern.compile("([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})/((([13578]|1[02])/([12][0-9]|3[01]|[1-9]))|(([469]|11)/([12][0-9]|30|[1-9]))|(2/([1][0-9]|2[0-9]|[1-9])))");
    public static final Pattern PATTERN_DDMMYYX = Pattern.compile("(((0[1-9]|[12][0-9]|3[01])/(0[13578]|1[02]))|((0[1-9]|[12][0-9]|30)/(0[469]|11))|((0[1-9]|[1][0-9]|2[0-8])/02))/([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})");
    public static final Pattern PATTERN_YYYYMMDDW_HHmmss = Pattern.compile("([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-9]))) (([0-1]{1}[0-9]{1})|2[0-3]{1}):([0-5]{1}[0-9]{1}):([0-5]{1}[0-9]{1})");
    public static final Pattern PATTERN_YYYYMMDDX_HHmmss = Pattern.compile("([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})/(((0[13578]|1[02])/(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)/(0[1-9]|[12][0-9]|30))|(02/(0[1-9]|[1][0-9]|2[0-9]))) (([0-1]{1}[0-9]{1})|2[0-3]{1}):([0-5]{1}[0-9]{1}):([0-5]{1}[0-9]{1})");
    public static final Pattern PATTERN_YYYYMDX_HHmmss = Pattern.compile("([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})/((([13578]|1[02])/([12][0-9]|3[01]|[1-9]))|(([469]|11)/([12][0-9]|30|[1-9]))|(2/([1][0-9]|2[0-9]|[1-9]))) (([0-1]{1}[0-9]{1})|2[0-3]{1}):([0-5]{1}[0-9]{1}):([0-5]{1}[0-9]{1})");
    public static final Pattern PATTERN_DDMMYYX_HHmmss = Pattern.compile("(((0[1-9]|[12][0-9]|3[01])/(0[13578]|1[02]))|((0[1-9]|[12][0-9]|30)/(0[469]|11))|((0[1-9]|[1][0-9]|2[0-8])/02))/([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3}) (([0-1]{1}[0-9]{1})|2[0-3]{1}):([0-5]{1}[0-9]{1}):([0-5]{1}[0-9]{1})");
    public static final Pattern PATTERN_YYYYMDW_HHmmss = Pattern.compile("([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-((([13578]|1[02])-([12][0-9]|3[01]|[1-9]))|(([469]|11)-([12][0-9]|30|[1-9]))|(2-([1][0-9]|2[0-9]|[1-9]))) (([0-1]{1}[0-9]{1})|2[0-3]{1}):([0-5]{1}[0-9]{1}):([0-5]{1}[0-9]{1})");
    public static final Pattern PATTERN_YYYYMDW = Pattern.compile("([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-((([13578]|1[02])-([12][0-9]|3[01]|[1-9]))|(([469]|11)-([12][0-9]|30|[1-9]))|(2-([1][0-9]|2[0-9]|[1-9])))");


    /**
     * this is for joda time
     */
    public static final String fullPatternWithZ = "yyyyMMddHHmmssZ";
    public static Map<Integer, String> importLength2format = new HashMap<>();
    public static Map<Integer, String> importLength2Destformat = new HashMap<>();

    public static Map<Integer, String> exportLength2format = new HashMap<>();
    public static Map<Integer, String> exportLength2DestFormat = new HashMap<>();

    static {
        importLength2format.put(4, Y);
        importLength2format.put(6, YM);
        importLength2format.put(8, YMD);
        importLength2format.put(14, YMDHMS);
        importLength2format.put(17, fullPatternWithZ);


        importLength2Destformat.put(4, Y);
        importLength2Destformat.put(6, YMW);
        importLength2Destformat.put(8, YDMW);
        importLength2Destformat.put(14, datefullPatternWithLine);
        importLength2Destformat.put(17, datefullPatternWithLine);
    }

    static {
        exportLength2format.put(4, Y);
        exportLength2format.put(7, YMW);
        exportLength2format.put(10, YDMW);
        exportLength2format.put(19, datefullPatternWithLine);

        exportLength2DestFormat.put(4, Y);
        exportLength2DestFormat.put(7, YM);
        exportLength2DestFormat.put(10, YMD);
        exportLength2DestFormat.put(19, fullPatternWithZ);
    }


    public static Date getCurrentDate() {
        return DateTime.now().toDate();
    }

    public static String getCurrent() {
        return getCurrent(YMDHMS);
    }

    public static String getCurrent(String pattern) {
        return DateTime.now().toString(pattern);
    }

    public static String format(Date date) {
        return format(date, YMDHMS);
    }

    public static void main(String[] args) {

        System.out.println(formatString("22/06/1234", checkDatePattern("22/06/1234")));
        System.out.println(e2bDateImportConvert("20141011120000"));
        System.out.println(e2bDateImportConvert("20141011120000+06"));
        System.out.println(e2bDateImportConvert("2014"));
        System.out.println(e2bDateImportConvert("201405"));
        System.out.println(e2bDateImportConvert("20140509"));
        System.out.println(e2bR3DateExportConvert("2014-uk"));
        System.out.println(e2bR3DateExportConvert("2014-11-uk"));
        System.out.println(e2bR3DateExportConvert("2014-uk-11"));
        System.out.println(formatString("2014-12-11 12:12:12"));
        System.out.println(formatString("2014-12-11 12:12:12."));
        System.out.println(formatString("2014-12-11 12:12:12.123"));
        System.out.println(formatString("2014-12-11 24:12:12.123"));
    }

    public static String e2bR3DateExportConvert(String dateString) {
        if (StringUtils.isBlank(dateString)) {
            return null;
        }
        dateString = StringUtils.substringBefore(dateString, "-uk");

        String destFormat = exportLength2DestFormat.get(dateString.length());
        String format = exportLength2format.get(dateString.length());
        return convertDateString(dateString, format, destFormat);
    }

    public static String e2bR3DateImportConvert(String dateString) {
        if (StringUtils.isBlank(dateString)) {
            return null;
        }
        String format = importLength2format.get(dateString.length());
        String destFormat = importLength2Destformat.get(dateString.length());
        return convertDateString(dateString, format, destFormat);

    }

    public static String e2bDateImportConvert(String dateString) {
        if (StringUtils.isBlank(dateString)) {
            return null;
        }
        int length = dateString.length();
        String format = importLength2format.get(length);
        String destFormat = importLength2Destformat.get(dateString.length());
        String value = convertDateString(dateString, format, destFormat);
        if (length == 4) {
            value = value + "-uk-uk";
        } else if (length == 6) {
            value = value + "-uk";
        }
        return value;
    }

    public static String convertDateString(String date, String sourceFormat, String destFormat) {
        Date curDate = formatString(date, sourceFormat);
        if (curDate == null) {
            return StringUtils.EMPTY;
        }
        return format(curDate, destFormat);
    }

    public static String commonFormat(String date, String sourceFormat) {
        return convertDateString(date, sourceFormat, YDMW);
    }

    public static Date formatString(String date, String format) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        try {
            DateTimeFormatter fmt = DateTimeFormat.forPattern(format).withLocale(Locale.ENGLISH).withZone(DateTimeZone.forID("+08:00"));
            return DateTime.parse(date, fmt).toDate();
        } catch (IllegalArgumentException e) {
            logger.error("date parse fail for:" + date + " with format: " + format + " detail:" + e.getMessage());
            return null;
        }
    }

    public static String format(Date date, String pattern, Locale locale) {
        return DateFormatUtils.format(date, pattern, locale);
    }

    public static String formatString(String date, String sourcePattern, String targetPattern, Locale targetLocale) {
        Date dateTime = formatString(date, sourcePattern);
        return format(dateTime, targetPattern, targetLocale);
    }

    /**
     * 自动识别字符串格式并转为默认格式。日期格式不支持则返回为 null <br/>
     * 支持格式<br/>
     * YYYY-MM-DD 2018-02-03<br/>
     * YYYY/MM/DD 2018/02/03<br/>
     * YYYY/M/D 2014/2/3<br/>
     * DD/MM/YYYY 10/09/2018<br/>
     * 默认格式 YYYY-MM-DD 2018-02-03
     *
     * @return
     */
    public static String convertDateString(String date) {
        String pattern = checkDatePattern(date);
        if (pattern == null) {
            return null;
        }
        if (DateUtils.YDMW.equals(pattern)) {
            return date;
        }
        return format(formatString(date, pattern), DateUtils.YDMW);
    }

    /**
     * 自动识别字符串格式，并格式化为Date
     * 支持格式<br/>
     * YYYY-MM-DD 2018-02-03 <br/>
     * YYYY-M-D 2018-2-21 <br/>
     * YYYY/MM/DD 2018/02/03 <br/>
     * YYYY/M/D 2014/2/3 <br/>
     * DD/MM/YYYY 10/09/2018 <br/>
     * YYYY-MM-DD hh:mm:ss 2018-02-03 22:22:22<br/>
     * YYYY-M-D hh:mm:ss 2018-2-21 22:22:22<br/>
     * YYYY/MM/DD hh:mm:ss 2018/02/03 22:22:22 <br/>
     * YYYY/M/D hh:mm:ss 2014/2/3 22:22:22 <br/>
     * DD/MM/YYYY hh:mm:ss 10/09/2018 22:22:22 <br/>
     */
    public static Date formatString(String date) {
        String pattern = checkDatePattern(date);
        if (pattern == null) {
            return null;
        }
        return formatString(date, pattern);
    }

    /**
     * 正则匹配获取日期串的格式
     *
     * @param date 支持格式 <br/>
     *             YYYY-MM-DD 2018-02-03 <br/>
     *             YYYY-M-D 2018-2-21 <br/>
     *             YYYY/MM/DD 2018/02/03 <br/>
     *             YYYY/M/D 2014/2/3 <br/>
     *             DD/MM/YYYY 10/09/2018 <br/>
     *             YYYY-MM-DD hh:mm:ss 2018-02-03 22:22:22<br/>
     *             YYYY-M-D hh:mm:ss 2018-2-21 22:22:22<br/>
     *             YYYY/MM/DD hh:mm:ss 2018/02/03 22:22:22 <br/>
     *             YYYY/M/D hh:mm:ss 2014/2/3 22:22:22 <br/>
     *             DD/MM/YYYY hh:mm:ss 10/09/2018 22:22:22 <br/>
     * @return
     */
    public static String checkDatePattern(String date) {
        if (date == null || "".equals(date)) {
            return null;
        }
        Matcher matcher;
        //2018-01-31 21:21:21
        matcher = PATTERN_YYYYMMDDW_HHmmss.matcher(date);
        if (matcher.matches()) {
            return datefullPatternWithLine;
        }
        // 2018-1-31 21:21:21
        matcher = PATTERN_YYYYMDW_HHmmss.matcher(date);
        if (matcher.matches()) {
            return datefullPatternWithLine;
        }
        // 2018-1-31
        matcher = PATTERN_YYYYMDW.matcher(date);
        if (matcher.matches()) {
            return YDMW;
        }

        //2018/01/31 21:21:21
        matcher = PATTERN_YYYYMMDDX_HHmmss.matcher(date);
        if (matcher.matches()) {
            return YYYYMMDDX_HHmmss;
        }
        matcher = PATTERN_YYYYMDX_HHmmss.matcher(date);
        if (matcher.matches()) {
            return YYYYMMDDX_HHmmss;
        }
        matcher = PATTERN_DDMMYYX_HHmmss.matcher(date);
        if (matcher.matches()) {
            return DDMMYYYYX_HHmmss;
        }
        matcher = PATTERN_YYYYMMDDW.matcher(date);
        if (matcher.matches()) {
            return YDMW;
        }
        matcher = PATTERN_YYYYMMDDX.matcher(date);
        if (matcher.matches()) {
            return YYYYMMDDX;
        }
        matcher = PATTERN_YYYYMDX.matcher(date);
        if (matcher.matches()) {
            return YYYYMMDDX;
        }
        matcher = PATTERN_DDMMYYX.matcher(date);
        if (matcher.matches()) {
            return DDMMYYYYX;
        }
        return null;

    }

    /**
     * 从给定字符串中截取 YYYY-MM-DD格式的日期
     *
     * @param date
     * @return
     */
    public static String subDateStr(String date) {
        Matcher matcher = PATTERN_YYYYMMDDW.matcher(date);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return "";
        }
    }

    public static Date plusDate(Date date, int day) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusDays(day).toDate();
    }

    public static Date plusMinute(Date date, int minute) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMinutes(minute).toDate();
    }

    public static String format(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        return new DateTime(date).toString(pattern);
    }

    public static DateTime parseTime(String date, String pattern) {
        return DateTimeFormat.forPattern(pattern).parseDateTime(date);
    }

    public static Date parseDate(String date, String pattern) {
        return parseTime(date, pattern).toDate();
    }

    public static Date parseTime(String pattern) {
        return DateTimeFormat.forPattern(pattern).parseDateTime(getCurrent(pattern)).toDate();
    }

    public static String appendBeginTime(String date) {
        if (StringUtils.isNotBlank(date)) {
            return date + " 00:00:00";
        }
        return "";
    }

    public static String appendEndTime(String date) {
        if (StringUtils.isNotBlank(date)) {
            return date + " 23:59:59";
        }
        return "";
    }

    public static Date getTodayBeginTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getTodayEndTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    public static String convertMonthEng(int month) {
        Map<Integer, String> monthDic = new HashMap<>(MapUtils.getInitialCapacity(12));
        monthDic.put(1, "Jan");
        monthDic.put(2, "Feb");
        monthDic.put(3, "Mar");
        monthDic.put(4, "Apr");
        monthDic.put(5, "May");
        monthDic.put(6, "Jun");
        monthDic.put(7, "Jul");
        monthDic.put(8, "Aug");
        monthDic.put(9, "Sep");
        monthDic.put(10, "Oct");
        monthDic.put(11, "Nov");
        monthDic.put(12, "Dec");
        return monthDic.getOrDefault(month, "");
    }

    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return (calendar.get(Calendar.MONTH) + 1);
    }

    public static String getMonthStr(Date date) {
        int month = getMonth(date);
        return convertMonthEng(month);
    }

    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static String addDay(String date, int interval) {
        Date source = formatString(date, DateUtils.datefullPatternWithLine);
        Date target = addDay(source, interval);
        return format(target, DateUtils.datefullPatternWithLine);
    }

    public static Date addDay(Date date, int interval) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, interval);
        return calendar.getTime();
    }

    /**
     * 一年的最早时间
     *
     * @return
     */
    public static String getCurrentYearFirstTime() {
        return DateTime.now().toString(Y) + "-01-01 00:00:00";
    }


    /**
     * yyyy-MM-dd格式的字符串format为 dd-MM-yyyy,可以包含uk
     *
     * @param strDate
     * @return
     */
    public static String formatEnDate(String strDate) {
        if (StringUtils.isBlank(strDate)) {
            return "";
        }
        if (strDate.contains(SystemConstant.monthUnknowValue) || strDate.contains(SystemConstant.yearUnknowValue)) {
            String[] split = strDate.split("-");
            if (split.length < 3) {
                return StringUtils.EMPTY;
            }
            if (!SystemConstant.monthUnknowValue.equals(split[1])) {
                String mouth = formatString(split[1], "MM", "MMM", Locale.ENGLISH);
                return String.format("%s-%s-%s", split[2], mouth, split[0]);
            }
            return String.format("%s-%s-%s", split[2], split[1], split[0]);

        }
        return formatString(strDate, "yyyy-MM-dd", "dd-MMM-yyyy", Locale.ENGLISH);
    }

}

