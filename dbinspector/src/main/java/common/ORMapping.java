package common;

import database.Table;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ReflectUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * description
 *
 * @author mickey
 * 05/07/2019
 */
public class ORMapping {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static final Map<Class<?>, Table> orm = new ConcurrentHashMap<Class<?>, Table>();
    private static final Map<String, Class<?>> nameMap = new ConcurrentHashMap<String, Class<?>>();

    public static Table get(Class<?> classzz) {
        javax.persistence.Table clazzAnnotation = classzz.getAnnotation(javax.persistence.Table.class);
        if (clazzAnnotation == null) {
            return null;
        }
        Table jpaTable = orm.computeIfAbsent(classzz, k -> {
            Table table = new Table();
            table.setSimpleJavaName(k.getSimpleName());
            table.setJavaName(k.getName());
            javax.persistence.Table tableAnno = k.getAnnotation(javax.persistence.Table.class);
            if (tableAnno == null) {
                throw new RuntimeException("not jpa standard class:" + k.getName());
            }
            String name = tableAnno.name();
            if (StringUtils.isBlank(name)) {
                name = k.getSimpleName();
            }
            table.setSqlName(name);
            table.setCatalog(tableAnno.catalog());
            table.setSchema(tableAnno.schema());

            List<Field> fields = ReflectUtils.getDeclaredFields(k);

        });
    }
}
