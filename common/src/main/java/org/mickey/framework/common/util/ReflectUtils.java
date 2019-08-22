package org.mickey.framework.common.util;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * description
 *
 * @author mickey
 * 18/07/2019
 */
public class ReflectUtils {
    private static Logger logger = LoggerFactory.getLogger(ReflectUtils.class);

    private static Map<Class, List<Field>> classFiledMap = new ConcurrentHashMap<>(256);

    public static Object getFieldValue(final Object object, final Field field) {
        if (field == null) {
            throw new IllegalArgumentException("Could not find field on target [" + object + "]");
        }
        makeAccessible(field);
        Object result = null;
        try {
            result = field.get(object);
        } catch (IllegalAccessException e) {
            logger.error("��?�?�������������������{}", e);
        }
        return result;
    }

    /**
     * ����������?����������������, ������private/protected���������, ��?��?���getter������.
     * �?�����?�������������������
     *
     * @param object    ������
     * @param fieldName �������??
     * @return ���������
     */
    public static Object getFieldValue(final Object object, final String fieldName) {
        Field field = getDeclaredField(object, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
        }
        return getFieldValue(object, field);
    }

    /**
     * ���������������������������, ������private/protected���������, ��?��?���setter������.
     * �?����������������������������
     *
     * @param object    ������
     * @param fieldName �������??
     * @param value     ���������
     */
    public static void setFieldValue(final Object object, final String fieldName, final Object value) {
        Field field = getDeclaredField(object, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
        }
        makeAccessible(field);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            logger.error("��?�?�������������������:{}", e);
        }
    }

    /**
     * �?����������������������������getter������
     * �?�����?�������������������
     *
     * @param obj      ������
     * @param property �������??
     * @return ���������
     */
    public static Object getProperty(Object obj, String property) {
        try {
            return PropertyUtils.getProperty(obj, property);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ������������������������������������setter������
     * �?����������������������������
     *
     * @param obj      ������
     * @param property �������??
     * @param value    ���������
     */
    public static void setProperty(Object obj, String property, Object value) {
        try {
            PropertyUtils.setProperty(obj, property, value);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * ������������������������, ������private/protected���������.
     * �?�������������������������
     *
     * @param object         ������
     * @param methodName     �������??
     * @param parameterTypes ������������
     * @param parameters     �������?����
     * @return ������������������
     */
    public static Object invokeMethod(final Object object, final String methodName, final Class<?>[] parameterTypes, final Object[] parameters) {
        Method method = getDeclaredMethod(object, methodName, parameterTypes);
        if (method == null) {
            throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + object + "]");
        }
        method.setAccessible(true);
        try {
            return method.invoke(object, parameters);
        } catch (Exception e) {
            throw convertReflectionExceptionToUnchecked(e);
        }
    }

    /**
     * �������?����������, ����?����������DeclaredField.
     * ����?�������������Object��?������������, ������null.
     *
     * @param object    ������
     * @param fieldName �������??
     * @return ���������Field
     */
    public static Field getDeclaredField(final Object object, final String fieldName) {
        return getDeclaredField(object.getClass(), fieldName);
    }

    /**
     * �������?����������, ����?����������DeclaredField.
     * ����?�������������Object��?������������, ������null.
     *
     * @param object ������
     * @return ���������Field
     */
    public static List<Field> getDeclaredFields(final Object object) {
        return getDeclaredFields(object.getClass());
    }

    /**
     * �������?����������, ����?����������DeclaredField.
     * ����?�������������Object��?������������, ������null.
     *
     * @param clazz ������������
     * @return ���������Field
     */
    public static List<Field> getDeclaredFields(final Class clazz) {
        List<Field> classFieldList = classFiledMap.get(clazz);
        if (classFieldList != null) {
            return classFieldList;
        }
        List<Field> fieldList = new ArrayList<Field>();
        for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
            Field[] fields = superClass.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                for (Field field : fields) {
                    boolean contains = false;
                    for (Field field1 : fieldList) {
                        if (field1.getName().equals(field.getName())) {
                            contains = true;
                            break;
                        }
                    }
                    if (!contains) {
                        fieldList.add(field);
                    }
                }
            }
        }
        classFiledMap.put(clazz, fieldList);
        return fieldList;
    }

    /**
     * �������?����������, ����?����������DeclaredField.
     * ����?�������������Object��?������������, ������null.
     *
     * @param clazz     ������������
     * @param fieldName �������??
     * @return ���������Field
     */
    public static Field getDeclaredField(final Class clazz, final String fieldName) {
        return org.springframework.util.ReflectionUtils.findField(clazz, fieldName);
    }

    /**
     * ������������Field�?�������
     *
     * @param field ������
     */
    protected static void makeAccessible(final Field field) {
        if (!Modifier.isPublic(field.getModifiers())
                || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
            field.setAccessible(true);
        }
    }

    /**
     * �������?����������,����?����������DeclaredMethod.
     * ����?�������������Object��?������������, ������null.
     *
     * @param object         ������
     * @param methodName     �������??
     * @param parameterTypes �������?����������
     * @return ������
     */
    public static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes) {
        return getDeclaredMethod(object.getClass(), methodName, parameterTypes);
    }

    /**
     * �������?����������,����?����������DeclaredMethod.
     * ����?�������������Object��?������������, ������null.
     *
     * @param clazz          ������
     * @param methodName     �������??
     * @param parameterTypes �������?����������
     * @return ������
     */
    public static Method getDeclaredMethod(Class<?> clazz, String methodName, Class<?>[] parameterTypes) {
        return org.springframework.util.ReflectionUtils.findMethod(clazz, methodName, parameterTypes);
    }

    /**
     * �������?����������,����?����������DeclaredMethod.
     * ����?�������������Object��?������������, ������null.
     *
     * @param clazz      ������
     * @param methodName �������??
     * @return ������
     */
    public static Method getDeclaredMethod(Class<?> clazz, String methodName) {
        return getDeclaredMethod(clazz, methodName, new Class[0]);
    }

    public static List<Method> getDeclaredMethods(Class<?> clazz) {
        return Arrays.asList(org.springframework.util.ReflectionUtils.getAllDeclaredMethods(clazz));
    }

    /**
     * �������??���,������Class����������������������������������?�������������. ���������������, ������Object.class.
     * eg.
     * public UserDao extends HibernateDao<Role>
     *
     * @param clazz The class to introspect
     * @return the first generic declaration, or Object.class if cannot be
     * determined
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> getSuperClassGenericType(final Class clazz) {
        return getSuperClassGenericType(clazz, 0);
    }

    /**
     * �������??���,������������Class����������������������������?�������������. ���������������, ������Object.class.
     * <p/>
     * ���public UserDao extends HibernateDao<User,Long>
     *
     * @param clazz clazz The class to introspect
     * @param index the Index of the generic ddeclaration,start from 0.
     * @return the index generic declaration, or Object.class if cannot be
     * determined
     */
    @SuppressWarnings("unchecked")
    public static Class getSuperClassGenericType(final Class clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: " + params.length);
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
            return Object.class;
        }
        return (Class) params[index];
    }

    /**
     * �??�?�����?����������������������(������getter������), ����?���?List.
     *
     * @param collection   �?���?����?�.
     * @param propertyName ��?�??�?�����������??.
     */
    @SuppressWarnings("unchecked")
    public static List convertElementPropertyToList(final Collection collection, final String propertyName) {
        List list = new ArrayList();
        try {
            for (Object obj : collection) {
                list.add(PropertyUtils.getProperty(obj, propertyName));
            }
        } catch (Exception e) {
            throw convertReflectionExceptionToUnchecked(e);
        }
        return list;
    }

    /**
     * �??�?�����?����������������������(������getter������), ����?���?������������������������������.
     *
     * @param collection   �?���?����?�.
     * @param propertyName ��?�??�?�����������??.
     * @param separator    ���������.
     */
    @SuppressWarnings("unchecked")
    public static String convertElementPropertyToString(final Collection collection, final String propertyName, final String separator) {
        List list = convertElementPropertyToList(collection, propertyName);
        return StringUtils.join(list, separator);
    }

    /**
     * ����?�������������������clazz���property������������.
     *
     * @param value  �������?�������������
     * @param toType �??�������������?����Class
     */
    public static Object convertValue(Object value, Class<?> toType) {
        try {
            DateConverter dc = new DateConverter();
            dc.setUseLocaleFormat(true);
            dc.setPatterns(new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"});
            ConvertUtils.register(dc, Date.class);
            return ConvertUtils.convert(value, toType);
        } catch (Exception e) {
            throw convertReflectionExceptionToUnchecked(e);
        }
    }

    /**
     * ����??���������checked exception����?����unchecked exception.
     */
    public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
        if (e instanceof IllegalAccessException
                || e instanceof IllegalArgumentException
                || e instanceof NoSuchMethodException) {
            return new IllegalArgumentException("Reflection Exception.", e);
        } else if (e instanceof InvocationTargetException) {
            return new RuntimeException("Reflection Exception.",
                    ((InvocationTargetException) e).getTargetException());
        } else if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        return new RuntimeException("Unexpected Checked Exception.", e);
    }

//	*
//	 * ����������������������?�������������������
//	 * ������������������
//	 * 1 ������������������������������������
//	 * 2 �������������������������������?�<p>.</p> ��������?������ognl���������������
//	 *
//	 * @param clazz
//	 * @param propertyName
//	 * @return
//
//	public static boolean isProperty(Class<?> clazz, String propertyName) {
//		Assert.hasText(propertyName);
//		String[] ognls = propertyName.split(".");
//		if (ognls.length > 0) {
//			Object object = getFieldValue(clazz, ognls[0]);
//		}
//		Field field = getDeclaredField(clazz, propertyName);
//		if (field != null) {
//			return true;
//		}
//		return false;
//	}

    /**
     * �?����������������������������������
     *
     * @param clazz
     * @param associationPath
     * @return
     */
    public static Class getPropTypeByAssociationPath(Class clazz, String associationPath) {
        if (associationPath.contains(".")) {
            String outerProp = associationPath.substring(0, associationPath.indexOf("."));
            String innerProp = associationPath.substring(associationPath.indexOf(".") + 1);
            Field field = getDeclaredField(clazz, outerProp);
            int type = DataType.getDataType(field.getType());
            //�������������?�������
            if (DataType.isCollectionType(type)) {
                Class genericType = getGenericCollectionType(field);
                if (genericType == null) {
                    throw new RuntimeException("����?������������?�����������?");
                }
                return getPropTypeByAssociationPath(genericType, innerProp);
            }
            //���������map������
            if (DataType.isMapType(type)) {
                Class[] genericType = getGenericMapType(field);
                if (genericType == null) {
                    throw new RuntimeException("Map�����������?�����������?");
                }
                return getPropTypeByAssociationPath(genericType[1], innerProp);
            }
            return getPropTypeByAssociationPath(field.getType(), innerProp);
        } else {
            return getDeclaredField(clazz, associationPath).getType();
        }
    }

    /**
     * �?��������?����������������
     *
     * @return
     */
    public static Class getGenericCollectionType(Field field) {
        ParameterizedType pt = (ParameterizedType) field.getGenericType();
        if (pt.getActualTypeArguments().length > 0) {
            return (Class) pt.getActualTypeArguments()[0];
        }
        return null;
    }

    /**
     * �?����map���������������
     *
     * @return
     */
    public static Class[] getGenericMapType(Field field) {
        ParameterizedType pt = (ParameterizedType) field.getGenericType();
        if (pt.getActualTypeArguments().length > 1) {
            Class[] classes = new Class[2];
            classes[0] = (Class) pt.getActualTypeArguments()[0];
            classes[1] = (Class) pt.getActualTypeArguments()[1];
            return classes;
        }
        return null;
    }

    /**
     * �������������������?�����?���������������?���
     *
     * @param clazz1 ��?���
     * @param clazz2 �?����������
     * @return ����?������?���
     */
    public static boolean isSubClass(Class clazz1, Class clazz2) {
        Class parent = clazz1.getSuperclass();
        while (parent != null) {
            if (parent.getName().equals(clazz2.getName())) {
                return true;
            }
            parent = parent.getSuperclass();
        }
        return false;
    }

    /**
     * �������������������?������������?�������?�
     *
     * @param clazz1 ���������
     * @param clazz2 ����?�
     * @return true �������������?� false ����������������?�
     */
    public static boolean isInterfaceOf(Class clazz1, Class clazz2) {
        Class[] interfaces = clazz1.getInterfaces();
        if (interfaces == null || interfaces.length == 0) {
            return false;
        }
        for (Class anInterface : interfaces) {
            if (anInterface.getName().equals(clazz2.getName())) {
                return true;
            }
        }
        return false;
    }

    public static String buildMethodSignature(Class<?> clazz, String methodName, Class<?>[] parameterTypes) {
        StringBuilder builder = new StringBuilder();
        builder.append(clazz.getName());
        builder.append(".");
        builder.append(methodName);
        builder.append("(");
        for (int i = 0; i < parameterTypes.length; i++) {
            builder.append(i > 0 ? "," : "");
            builder.append(parameterTypes[i].getName());
        }
        builder.append(")");
        return builder.toString();
    }
}
