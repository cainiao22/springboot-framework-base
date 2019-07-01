package com.qding.bigdata.jobs;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * @author yanpf
 * @date 2019/6/4 16:47
 * @description
 */

@Slf4j
public class UserGetJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("执行库存检查定时任务，执行时间：{}",new Date());
    }
}
