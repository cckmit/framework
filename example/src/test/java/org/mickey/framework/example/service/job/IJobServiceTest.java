package org.mickey.framework.example.service.job;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.mickey.framework.example.po.JobPo;
import org.mickey.framework.example.test.BaseSpringTest;

import javax.annotation.Resource;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class IJobServiceTest extends BaseSpringTest {
    @Resource
    private IJobService jobService;

    @Test
    public void testMapper() {
        JobPo jobPo = new JobPo();
        jobPo.setAppId("mickey");
        jobPo.setName("job_bing");
        jobPo.setDescription("test call bing.com");
        jobPo.setCron("0 0/5 * * * ? ");
        jobPo.setUrl("https://cn.bing.com/");
        jobService.insert(jobPo);
    }
}
