package org.mickey.framework.core.mybatis.listener.impl;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.SystemContext;
import org.mickey.framework.common.po.BasePo;
import org.mickey.framework.common.po.BaseProjectPo;
import org.mickey.framework.common.po.CommonPo;
import org.mickey.framework.core.mybatis.listener.spi.PreUpdateListener;

import java.util.Date;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class DefaultPreUpdateListener implements PreUpdateListener {
    @Override
    public boolean preUpdate(Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof CommonPo) {
            CommonPo po = (CommonPo) object;
            if (po.getUpdateBy() == null) {
                po.setUpdateBy(SystemContext.getUserId());
            }
            if (po.getUpdateTime() == null) {
                po.setUpdateTime(new Date());
            }
            if (po.getIsDeleted() == null) {
                po.setIsDeleted(0);
            }
        }
//        if (object instanceof BasePo) {
//            ((BasePo) object).setTenantId(SystemContext.getTenantId());
//        }
//        if (object instanceof BaseProjectPo) {
//            ((BaseProjectPo) object).setProjectId(SystemContext.getProjectId());
//        }
        return true;
    }
}
