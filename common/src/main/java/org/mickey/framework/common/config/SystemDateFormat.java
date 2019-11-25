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
    private SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public SystemDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
        return dateFormat.format(date, toAppendTo, fieldPosition);
    }

    @Override
    public Date parse(String source, ParsePosition pos) {
        Date date = null;
        try {
            date = format1.parse(source, pos);
        } catch (Exception ex) {
            date = dateFormat.parse(source, pos);
        }
        return date;
    }

    @Override
    public Date parse(String source) throws ParseException {
        Date date = null;
        try {
            date = format1.parse(source);
        } catch (Exception ex) {
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
