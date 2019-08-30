package org.mickey.framework.core.mybatis.listener;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
public interface OrderedListener {
    default int getOrder() {return 0;}
}
