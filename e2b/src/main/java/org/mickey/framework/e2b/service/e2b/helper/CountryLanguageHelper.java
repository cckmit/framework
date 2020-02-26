package org.mickey.framework.e2b.service.e2b.helper;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * description
 *
 * @author mickey
 * 2020-02-24
 */
@Slf4j
@Data
public class CountryLanguageHelper {
    public final static String PREFIX_COUNTRY="COUNTRY_";
    public final static String PREFIX_LANGUAGE="LANGUAGE_";
    public final static String COUNTRY_OID_R3="1.0.3166.1.2.2";
    public final static String LANGUAGE_OID_R3="2.16.840.1.113883.6.100";
    public static boolean isCountryR3OID(String oid) {
        return StringUtils.equalsIgnoreCase(COUNTRY_OID_R3,oid);
    }
    public static boolean isLanguageR3OID(String oid) {
        return StringUtils.equalsIgnoreCase(LANGUAGE_OID_R3,oid);
    }
    public static String getCountryUniqueCode(String country) {
        return PREFIX_COUNTRY + country;
    }
    public static String getCountry(String countryUniqueCode) {
        return StringUtils.replaceOnce(countryUniqueCode,PREFIX_COUNTRY,"");
    }
    public static String getLanguage(String languageUniqueCode) {
        return StringUtils.replaceOnce(languageUniqueCode,PREFIX_LANGUAGE,"");
    }
    public static String getLanguageUnqueCode(String language) {
        return PREFIX_LANGUAGE + language;
    }
}
