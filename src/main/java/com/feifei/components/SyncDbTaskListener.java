package com.qding.bigdata.components;

import com.qding.bigdata.config.StrategyProcessor;
import com.qding.bigdata.config.columntype.ColumnTypeConfigure;
import com.qding.bigdata.config.columntype.JdbcConfig;
import com.qding.bigdata.model.*;
import com.qding.bigdata.po.DbSyncTaskPo;
import com.qding.bigdata.service.DbConfigService;
import com.qding.bigdata.service.DbSyncService;
import com.qding.bigdata.service.HiveTableSchemaService;
import com.qding.bigdata.service.PartitionStrategy;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.qding.bigdata.utils.Constants.PARTITION_VALUE;

/**
 * @author yanpf
 * @date 2019/6/18 17:28
 * @description
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "spring.kafka.consumer", name = "enabled", havingValue = "true")
public class SyncDbTaskListener {

    public static final String TOPIC_SYNC_DB_TASK = "topic_sync_db_task";

    @Autowired
    DbConfigService dbConfigService;

    @Autowired
    DbSyncService dbSyncService;

    @Autowired
    HiveTableSchemaService hiveTableSchemaService;

    @Autowired
    Configuration freeMarkerCfg;

    @KafkaListener(topics = {TOPIC_SYNC_DB_TASK})
    public void consumeSyncDbTask(ConsumerRecord<?, DbSyncTaskPo> record) {
        DbSyncTaskPo po = record.value();
        DbSynConfig dbSynConfig = hiveTableSchemaService.getDbSyncConfigById(po.getSynConfigId());
        DbConfig dbConfig = dbConfigService.getDbConfigById(dbSynConfig.getDbConfigId());
        List<DBColumnSchema> columnSchemaList = dbSyncService.getDBColumnSchemaList(dbConfig, dbSynConfig.getSourceDbName(), dbSynConfig.getSourceTableName());

        JdbcConfig jdbcConfig = ColumnTypeConfigure.getJdbcConfig(dbConfig.getDbType().toLowerCase());
        JdbcConfig hiveJdbcConfig = ColumnTypeConfigure.getJdbcConfig("hive");

        List<HiveColumnSchema> hiveColumnSchemas = new ArrayList<>();
        for (DBColumnSchema columnSchema : columnSchemaList) {
            HiveColumnSchema hiveColumn = new HiveColumnSchema();
            hiveColumn.setName(columnSchema.getColumnName());
            hiveColumn.setDataType(hiveJdbcConfig.getReaderTypes().get(jdbcConfig.getReaderTypes().get(columnSchema.getDataType())));
            hiveColumn.setCharacterMaxLength(columnSchema.getCharacterMaximumLength());
            hiveColumn.setNumericPrecision(columnSchema.getNumericPrecision());
            hiveColumn.setNumericScale(columnSchema.getNumericScale());
            hiveColumn.setFullDataType(columnSchema.getColumnType());
            hiveColumn.setComment(columnSchema.getColumnComment());
            hiveColumn.setTableId(dbSynConfig.getHiveTableId());
            hiveColumn.setIsNullable("YES".equals(columnSchema.getIsNullable()));
            hiveColumn.setDefaultValue(columnSchema.getColumnDefault());

            hiveColumnSchemas.add(hiveColumn);
        }

        HiveTableSchema hiveTableSchema = hiveTableSchemaService.getHiveTableById(dbSynConfig.getHiveTableId());
        try {
            hiveTableSchemaService.createTable("hive", hiveTableSchema, hiveColumnSchemas);
            hiveTableSchemaService.saveHiveColumnSchema(hiveColumnSchemas);
        } catch (IOException | TemplateException e) {
            log.error("保存hive同步信息失败", e);
        }


        if ("DATA".equals(dbSynConfig.getSyncType())) {
            HiveColumnSchema partitionHiveColumn = hiveTableSchemaService.getPartitionHiveColumn(hiveTableSchema.getTableId());
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("dbSynConfig", dbSynConfig);
            dataModel.put("dbConfig", dbConfig);
            dataModel.put("columnSchemaList", columnSchemaList);
            dataModel.put("hiveTableSchema", hiveTableSchema);
            dataModel.put("partitionHiveColumn", partitionHiveColumn);
            if(StringUtils.isEmpty(po.getPartitionValue())){
                String partitionValue = StrategyProcessor.getInstance(PartitionStrategy.class, partitionHiveColumn.getPartitionType()).paratitionValue();
                dataModel.put(PARTITION_VALUE, partitionValue);
            }else {
                dataModel.put(PARTITION_VALUE, po.getPartitionValue());
            }

            File jsonFile = new File(String.format("datax_dbSynConfig_%d_%d.json", po.getSynConfigId(), System.currentTimeMillis()));
            try (FileWriter fileWriter = new FileWriter(jsonFile)) {
                freeMarkerCfg.getTemplate("dataxFreemarker.ftl").process(dataModel, fileWriter);
            } catch (IOException | TemplateException e) {
                log.error("生成dataX文件失败", e);
                Throwable[] suppressed = e.getSuppressed();
                for (int i = 0; i < suppressed.length; i++)
                    log.error(suppressed[i].getMessage());
            }
            String exe = "python";
            String command = "dataX.py";
            String jsonPath = jsonFile.getAbsolutePath();
            String[] cmdArr = new String[]{exe, command, jsonPath};
            try {
                Process process = Runtime.getRuntime().exec(cmdArr);
                InputStream is = process.getInputStream();
                BufferedReader dis = new BufferedReader(new InputStreamReader(is));
                process.waitFor();
                String str = dis.readLine();
                log.info(str);
            } catch (IOException | InterruptedException e) {
                log.error("执行dataX脚本失败", e);
            }
        }
    }
}
