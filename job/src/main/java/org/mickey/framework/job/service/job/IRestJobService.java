package org.mickey.framework.job.service.job;

import org.mickey.framework.job.po.RestJobPo;

/**
 * description
 *
 * @author wangmeng
 * @version 1.0.0
 * 2020年11月11日 11:24:00
 */
public interface IRestJobService {

    void schedule(RestJobPo restJobPo);

    void reschedule(RestJobPo restJobPo);

    void remove(RestJobPo restJobPo);

    void insert(RestJobPo restJobPo);

    void update(RestJobPo restJobPo);

    void delete(String id);

}
