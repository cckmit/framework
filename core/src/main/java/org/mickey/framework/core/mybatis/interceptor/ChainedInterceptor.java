package org.mickey.framework.core.mybatis.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
public interface ChainedInterceptor  extends Interceptor {

    int PAGE_HELPER_ORDER = 0;
    int SHARDING_BATCH_ORDER = 1;
    int SIMPLE_BATCH_ORDER = 2;
    int UQ_CONSTRAINT_ORDER = 10;

    int SHARDING_LIST_BY_IDS = 10000;


    /**
     * @param order       小的在后面， 0最小
     * @param interceptor
     */
    void addInterceptor(int order, NamedInterceptor interceptor);
}
