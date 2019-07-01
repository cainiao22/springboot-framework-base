package com.qding.bigdata.jobs;

import com.qding.bigdata.po.DbSyncTaskPo;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;


import static com.qding.bigdata.components.SyncDbTaskListener.TOPIC_SYNC_DB_TASK;
import static com.qding.bigdata.utils.Constants.PARTITION_VALUE;


/**
 * @author yanpf
 * @date 2019/6/12 11:51
 * @description
 */

@Component
@Slf4j
public class KafkaDbSyncJob extends QuartzJobBean {

    @Autowired
    KafkaTemplate<String, DbSyncTaskPo> kafkaTemplate;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        DbSyncTaskPo po = new DbSyncTaskPo();
        Long synConfigId = context.getJobDetail().getJobDataMap().getLong("synConfigId");
        po.setSynConfigId(synConfigId);
        po.setPartitionValue(context.getJobDetail().getJobDataMap().getString(PARTITION_VALUE));
        kafkaTemplate.send(TOPIC_SYNC_DB_TASK, po);
    }
}
