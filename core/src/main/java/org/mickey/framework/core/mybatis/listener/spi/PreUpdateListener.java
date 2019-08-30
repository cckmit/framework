package org.mickey.framework.core.mybatis.listener.spi;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.core.mybatis.listener.OrderedListener;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
public interface PreUpdateListener extends OrderedListener {
    boolean preUpdate(Object object);
}
