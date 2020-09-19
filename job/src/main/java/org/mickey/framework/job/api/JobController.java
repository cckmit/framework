package org.mickey.framework.job.api;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.common.util.DateUtils;
import org.mickey.framework.job.service.BingImageJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * #Description
 *
 * @author wangmeng
 * @date 2020-06-11
 */
@Slf4j
@RestController
@Api(tags = "job")
@RequestMapping("/job")
public class JobController {
    /**
     * 加入Qualifier注解，通过名称注入bean
     */
    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;

    @PostConstruct
    public void init() throws SchedulerException {
        log.info("----------------------------------------------------- init job -----------------------------------------------------");

        String jobName = "BingImage";
        String groupName = "Image";

        scheduler.start();
        JobDetail jobDetail = JobBuilder.newJob(BingImageJob.class)
                .withIdentity(jobName, groupName)
                .withDescription("get bing.cn background image in everyday")
                .build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/20 * * * * ? ");
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName, groupName)
                .withSchedule(scheduleBuilder).build();
        Date date = scheduler.scheduleJob(jobDetail, trigger);

        log.info("%s next run time is %s", jobName, DateUtils.format(date));
    }
}
