package com.qding.bigdata;

import com.qding.bigdata.config.columntype.ColumnTypeConfigure;
import com.qding.bigdata.config.columntype.JdbcConfig;
import com.qding.bigdata.mapper.UserMapper;
import com.qding.bigdata.model.*;
import com.qding.bigdata.service.DbConfigService;
import com.qding.bigdata.service.DbSyncService;
import com.qding.bigdata.service.HiveTableSchemaService;
import com.qding.bigdata.service.UserService;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class DatasourceManagementApplicationTests {


	@Autowired
	UserService userService;

	@Autowired
	UserMapper userMapper;

	@Autowired
	DbConfigService dbConfigService;

	@Autowired
	DbSyncService dbSyncService;

	@Autowired
	HiveTableSchemaService hiveTableSchemaService;

	@Autowired
	Configuration freeMarkerCfg;


	@Test
	public void testUserService(){
		userService.getAllUsers(1);
	}

	@Test
	public void testUserMapper(){
		this.userMapper.selectByPrimaryKey(1);
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testCreateTable(){
		//Long synConfigId = context.getJobDetail().getJobDataMap().getLong("synConfigId");
		DbSynConfig dbSynConfig = hiveTableSchemaService.getDbSyncConfigById(1L);
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

		//TODO 同步数据
		if("DATA".equals(dbSynConfig.getSyncType())){
			Map<String, Object> dataModel = new HashMap<>();
			dataModel.put("dbSynConfig", dbSynConfig);
			dataModel.put("dbConfig", dbConfig);
			dataModel.put("columnSchemaList", columnSchemaList);
			dataModel.put("hiveTableSchema", hiveTableSchema);
			FileWriter fileWriter = null;
			try {
				fileWriter = new FileWriter(new File(String.format("datax_dbSynConfig_%d.json", 1)));
				freeMarkerCfg.getTemplate("dataxFreemarker.ftl").process(dataModel, fileWriter);
			} catch (IOException | TemplateException e) {
				e.printStackTrace();
			}finally {
				try {
					fileWriter.flush();
				} catch (IOException e) {
					log.error("刷新文件失败", e);
				}
				try {
					fileWriter.close();
				} catch (IOException e) {
					log.error("关闭文件失败", e);
				}
			}

		}
	}

}
