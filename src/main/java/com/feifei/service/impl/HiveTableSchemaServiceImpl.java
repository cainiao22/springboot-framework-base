package com.qding.bigdata.service.impl;

import com.qding.bigdata.annotations.DataSourceRoute;
import com.qding.bigdata.jobs.KafkaDbSyncJob;
import com.qding.bigdata.mapper.DbSynConfigMapper;
import com.qding.bigdata.mapper.HiveColumnSchemaMapper;
import com.qding.bigdata.mapper.HiveTableSchemaMapper;
import com.qding.bigdata.mapper.hive.HiveMapper;
import com.qding.bigdata.mapper.mysql.DBColumnSchemaMapper;
import com.qding.bigdata.mapper.mysql.DBTableSchemaMapper;
import com.qding.bigdata.model.*;
import com.qding.bigdata.po.DbSynConfigItemPo;
import com.qding.bigdata.po.DbSynConfigPo;
import com.qding.bigdata.po.DbSyncTaskPo;
import com.qding.bigdata.service.HiveTableSchemaService;
import com.qding.bigdata.utils.Result;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.qding.bigdata.utils.Constants.PARTITION_VALUE;

/**
 * @author yanpf
 * @date 2019/6/11 9:29
 * @description
 */

@Slf4j
@Service
public class HiveTableSchemaServiceImpl implements HiveTableSchemaService {

    /**
     * 直接操作hive数据库
     */
    @Autowired
    HiveMapper hiveMapper;

    @Autowired
    DBTableSchemaMapper dbTableSchemaMapper;

    @Autowired
    DBColumnSchemaMapper dbColumnSchemaMapper;

    @Autowired
    Configuration freeMarkerCfg;

    @Autowired
    DbSynConfigMapper dbSynConfigMapper;

    @Autowired
    HiveTableSchemaMapper hiveTableSchemaMapper;

    @Autowired
    HiveColumnSchemaMapper hiveColumnSchemaMapper;

    @Autowired
    Scheduler scheduler;


    @Override
    @DataSourceRoute("#dbConfigName")
    public void createTable(String dbConfigName, HiveTableSchema table, List<HiveColumnSchema> columnSchemaList) throws IOException, TemplateException {
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("table", table);
        dataModel.put("columns", columnSchemaList);
        HiveColumnSchema partitionColumn = hiveColumnSchemaMapper.getPartitionHiveColumn(table.getTableId());
        if(partitionColumn != null) {
            dataModel.put("partitionColumn", partitionColumn);
        }
        StringWriter writer = new StringWriter();
        freeMarkerCfg.getTemplate("create_hive_table.ftl").process(dataModel, writer);
        hiveMapper.execute(writer.toString());
    }

    @Override
    public Result<String> saveHiveColumnSchema(List<HiveColumnSchema> hiveColumnSchemas) {
        for (HiveColumnSchema hiveColumnSchema : hiveColumnSchemas) {
            hiveColumnSchemaMapper.insertSelective(hiveColumnSchema);
        }

        return Result.success();
    }

    @Override
    public HiveColumnSchema getPartitionHiveColumn(Long tableId) {
        return hiveColumnSchemaMapper.getPartitionHiveColumn(tableId);
    }

    @Override
    public void triggerJob(DbSyncTaskPo po) throws SchedulerException {
        DbSynConfig synConfig = dbSynConfigMapper.selectByPrimaryKey(po.getSynConfigId());

        String group = String.format("dbsync-group-%s~%s", synConfig.getDbConfigId(), synConfig.getSourceDbName());
        String jobName = String.format("dbsync-job-%d~%s~%d", synConfig.getDbConfigId(), synConfig.getSourceDbName(), synConfig.getId());
        JobKey jobKey = JobKey.jobKey(group, jobName);

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("synConfigId", synConfig.getId());
        jobDataMap.put("dbConfigId", synConfig.getDbConfigId());
        jobDataMap.put(PARTITION_VALUE, po.getPartitionValue());
        scheduler.triggerJob(jobKey, jobDataMap);
    }

    @Transactional
    @Override
    public Result<String> saveSyncConfig(DbSynConfigPo po) throws SchedulerException {
        for (DbSynConfigItemPo itemPo : po.getSynConfigItemPoList()) {

            HiveTableSchema hiveTableSchema = new HiveTableSchema();
            hiveTableSchema.setDbName(itemPo.getDestDbName());
            hiveTableSchema.setTableName(itemPo.getDestTableName());
            hiveTableSchema.setComment(itemPo.getComment());
            hiveTableSchema.setLocation(getHiveLocation(itemPo));

            hiveTableSchemaMapper.insertSelective(hiveTableSchema);


            DbSynConfig dbSynConfig = new DbSynConfig();
            dbSynConfig.setDbConfigId(po.getDbConfigId());
            dbSynConfig.setSourceDbName(po.getSourceDbName());
            dbSynConfig.setSourceTableName(po.getSourceTableName());

            dbSynConfig.setSourceTableName(po.getSourceTableName());
            dbSynConfig.setDestDbName(itemPo.getDestDbName());
            dbSynConfig.setDestTableName(itemPo.getDestTableName());
            dbSynConfig.setSyncType(itemPo.getSyncType());
            dbSynConfig.setCrontab(itemPo.getCrontab());
            dbSynConfig.setSyncTimes(itemPo.getSyncTimes());
            dbSynConfig.setHiveTableId(hiveTableSchema.getTableId());
            dbSynConfigMapper.insert(dbSynConfig);


            String group = String.format("dbsync-group-%s~%s", po.getDbConfigId(), po.getSourceDbName());
            String jobName = String.format("dbsync-job-%d~%s~%d", po.getDbConfigId(), po.getSourceDbName(), dbSynConfig.getId());
            String triggerName = String.format("dbsync-trigger-%d~%s~%d", po.getDbConfigId(), po.getSourceDbName(), dbSynConfig.getId());
            //创建任务
            JobDetail jobDetail = JobBuilder.newJob(KafkaDbSyncJob.class)
                    .withIdentity(jobName, group)
                    .usingJobData("synConfigId", dbSynConfig.getId())
                    .usingJobData("dbConfigId", dbSynConfig.getDbConfigId())
                    .build();
            //创建任务触发器
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerName, group)
                    .withSchedule(CronScheduleBuilder.cronSchedule(itemPo.getCrontab()))
                    .startNow()
                    .build();
            //.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).withRepeatCount(5)).build();
            //将触发器与任务绑定到调度器内
            scheduler.scheduleJob(jobDetail, trigger);
        }

        return Result.success();
    }

    @Override
    @Cacheable(value = "DbSynConfig", key = "'id'.concat(':').concat(#id)", unless = "#result == null")
    public DbSynConfig getDbSyncConfigById(Long id) {
        return dbSynConfigMapper.selectByPrimaryKey(id);
    }

    @Override
    @Cacheable(value = "HiveTableSchema", key = "'id'.concat(':').concat(#hiveTableId)", unless = "#result == null")
    public HiveTableSchema getHiveTableById(Long hiveTableId) {
        return hiveTableSchemaMapper.selectByPrimaryKey(hiveTableId);
    }

    private String getHiveLocation(DbSynConfigItemPo itemPo){
        return String.format("/%s/%s", itemPo.getDestDbName(), itemPo.getDestTableName());
    }
}
