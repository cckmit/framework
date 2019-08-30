package org.mickey.framework.core.mapping;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
public interface Mapper {
    /**
     * 从源对象复制属性到目标对象
     *
     * @param origin      源对象
     * @param destination 目标对象
     */
    void map(Object origin, Object destination);

    /**
     * 从源对象复制属性到目标对象
     *
     * @param origin      源对象
     * @param destination 目标对象
     * @see this#mapWithout(Object, Object, String[])
     */
    @Deprecated
    default void map(Object origin, Object destination, String[] excludeProps) {
        this.mapWithout(origin, destination, excludeProps);
    }

    /**
     * 从源对象复制属性到目标对象
     *
     * @param origin       源对象
     * @param destination  目标对象
     * @param excludeProps 不包含哪些属性
     */
    void mapWithout(Object origin, Object destination, String[] excludeProps);

    /**
     * 从源对象复制属性到目标对象
     *
     * @param origin      源对象
     * @param destination 目标对象
     */
    void mapWith(Object origin, Object destination, String[] includeProps);

    /**
     * 从源对象复制属性到目标对象
     *
     * @param origin      源对象
     * @param destination 目标对象
     * @param mappingId   映射文件Id
     */
    void map(Object origin, Object destination, String mappingId);

    /**
     * 复制源对象的所有属性到目标对象中
     *
     * @param origin      源对象
     * @param destination 目标对象
     */
    void clone(Object origin, Object destination);
}
