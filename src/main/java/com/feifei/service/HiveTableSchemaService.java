package com.qding.bigdata.service;

import com.qding.bigdata.model.DbSynConfig;
import com.qding.bigdata.model.HiveColumnSchema;
import com.qding.bigdata.model.HiveTableSchema;
import com.qding.bigdata.po.DbSynConfigPo;
import com.qding.bigdata.po.DbSyncTaskPo;
import com.qding.bigdata.utils.Result;
import freemarker.template.TemplateException;
import org.quartz.SchedulerException;

import java.io.IOException;
import java.util.List;

/**
 * @author yanpf
 * @date 2019/6/11 9:29
 * @description
 */
public interface HiveTableSchemaService {

    void createTable(String dbConfigName, HiveTableSchema table, List<HiveColumnSchema> columnSchemaList) throws IOException, TemplateException;

    Result<String> saveSyncConfig(DbSynConfigPo po) throws SchedulerException;

    DbSynConfig getDbSyncConfigById(Long id);

    HiveTableSchema getHiveTableById(Long hiveTableId);

    Result<String> saveHiveColumnSchema(List<HiveColumnSchema> hiveColumnSchemas);

    HiveColumnSchema getPartitionHiveColumn(Long tableId);

    void triggerJob(DbSyncTaskPo po) throws SchedulerException;
}
