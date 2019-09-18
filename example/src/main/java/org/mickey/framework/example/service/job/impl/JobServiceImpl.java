package org.mickey.framework.example.service.job.impl;

import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.example.mapper.JobMapper;
import org.mickey.framework.example.po.JobPo;
import org.mickey.framework.example.service.job.IJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
@Service
public class JobServiceImpl implements IJobService {
    @Autowired
    private JobMapper jobMapper;

    @Override
    public void insert(JobPo jobPo) {
        jobMapper.insert(jobPo);
    }
}
