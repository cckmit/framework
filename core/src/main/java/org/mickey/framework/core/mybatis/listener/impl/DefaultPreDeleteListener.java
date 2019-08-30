package org.mickey.framework.core.mybatis.listener.impl;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.core.mybatis.listener.spi.PreDeleteListener;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class DefaultPreDeleteListener implements PreDeleteListener {

    @Override
    public boolean preDelete(Object object) {return true;}
}
