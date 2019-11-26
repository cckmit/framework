package org.mickey.framework.common.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.text.*;
import java.util.Date;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Data
@Slf4j
public class SystemDateFormat extends DateFormat {
    private DateFormat dateFormat;

    private static final SimpleDateFormat DATE_FORMAT_PATTERN = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public SystemDateFormat() {
        super();
    }

    public SystemDateFormat(DateFormat dateFormat) {
        super();
        this.dateFormat = dateFormat;
    }

    public static void main(String[] args) {
        SystemDateFormat format = new SystemDateFormat();

        for(int i=0; i<20; ++i) {
            new Thread(()->{
                Date parse;
                try {
                    parse = format.parse("yyyy-MM-dd HH:mm:ss");
                    System.out.println(parse);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }

    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {

        StringBuffer result = null;
        try {
            result = DATE_FORMAT_PATTERN.format(date, toAppendTo, fieldPosition);
        } catch (Exception e) {
            result = dateFormat.format(date, toAppendTo, fieldPosition);
        }

        return result;
    }

    @Override
    public Date parse(String source, ParsePosition pos) {

        Date date = null;
        try {
            date = DATE_FORMAT_PATTERN.parse(source, pos);
        } catch (Exception e) {
            date = dateFormat.parse(source, pos);
        }
        return date;
    }

    @Override
    public Date parse(String source) throws ParseException {

        Date date = null;
        try {
            date = DATE_FORMAT_PATTERN.parse(source);
        } catch (Exception e) {
            date = dateFormat.parse(source);
        }
        return date;
    }

    @Override
    public Object clone() {
        Object format = dateFormat.clone();
        return new SystemDateFormat((DateFormat) format);
    }
}
