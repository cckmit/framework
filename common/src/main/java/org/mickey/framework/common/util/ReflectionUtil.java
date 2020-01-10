package org.mickey.framework.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.mickey.framework.common.SystemConstant;
import org.mickey.framework.common.exception.BusinessException;


import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;

import static org.apache.commons.beanutils.BeanUtils.getSimpleProperty;

/**
 * 提供PV使用的反射工具
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class ReflectionUtil {
    public static final List<String> DateOfPropertyType = Arrays.asList(new String[]{"dateofonset", "dateofresolution", "startdate", "enddate"});

    public static Object getPropertyValue(Object bean, String name) {
        try {
            if (bean == null) {
                return null;
            }
            return PropertyUtils.getSimpleProperty(bean, name);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getPropertyValue2String(Object bean, String name) {
        try {
            if (bean == null) {
                return "";
            }
            Object value = getPropertyValue(bean, name);
            if (value != null && value.getClass().equals(Date.class)) {
                return DateUtils.format((Date) value, DateUtils.YDMW);
            }
            return getSimpleProperty(bean, name);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void setPropertyValue(Object sourceObj, String propName, Object value) {
        try {
            String className = sourceObj.getClass().getSimpleName();
            Class<?> propertyType = PropertyUtils.getPropertyType(sourceObj, propName);
            if (propertyType == null) {
                throw new BusinessException(propName + " not exist in " + sourceObj.getClass());
            }
            setPropertyValue(sourceObj, propName, value, className, propertyType);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static void setPropertyValue(Object sourceObj, String propName, Object value, String className, Class<?> propertyType) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        if (value == null) {
            PropertyUtils.setSimpleProperty(sourceObj, propName, null);
            return;
        }
        if (propertyType == null) {
            return;
        }
        if (propertyType.equals(value.getClass())) {
            PropertyUtils.setSimpleProperty(sourceObj, propName, value);
            return;
        }

        String simpleName = propertyType.getSimpleName();
        if ("Meddrafidldinfo".equalsIgnoreCase(className) && simpleName.contains(SystemConstant.Encoding_intermediate_symbol)) {
            String[] split = simpleName.split(" ");

        } else {
            if (propertyType.equals(Date.class) && (value instanceof Long)) {
                PropertyUtils.setSimpleProperty(sourceObj, propName, new Date((Long) value));
            } else if ("Date".equals(simpleName) && value != null) {
                try {
                    Date dateTime = DateUtils.formatString((String) value, DateUtils.YDMW);
                    PropertyUtils.setSimpleProperty(sourceObj, propName, dateTime);
                } catch (Exception e) {
                    Date d = DateUtils.formatString(String.valueOf(value));
                    PropertyUtils.setSimpleProperty(sourceObj, propName, d);
                }
            } else if ("byte".equalsIgnoreCase(simpleName) && value != null) {
                PropertyUtils.setSimpleProperty(sourceObj, propName, Byte.valueOf(value.toString()));
            } else if ("Integer".equalsIgnoreCase(simpleName) || "int".equalsIgnoreCase(simpleName)) {
                String tempInteger = StringUtil.valueOf(value);
                Matcher matcher = FixedValue.ZERO_FLOAT_INTEGER.matcher(tempInteger);
                if (matcher.matches()) {
                    if (tempInteger.contains(".")) {
                        tempInteger = tempInteger.substring(0, tempInteger.indexOf("."));
                    }
                    PropertyUtils.setSimpleProperty(sourceObj, propName, Integer.parseInt(tempInteger));
                }
            } else if ("String".equalsIgnoreCase(simpleName) && DateOfPropertyType.contains(propName)) {
                PropertyUtils.setSimpleProperty(sourceObj, propName, DateUtils.convertDateString(String.valueOf(value).trim()));
            } else if ("Boolean".equalsIgnoreCase(simpleName)) {
                boolean booleanValue = BooleanUtils.toBoolean(value.toString());
                PropertyUtils.setSimpleProperty(sourceObj, propName, booleanValue);
            } else {
                PropertyUtils.setSimpleProperty(sourceObj, propName, value);
            }
        }
    }
}
