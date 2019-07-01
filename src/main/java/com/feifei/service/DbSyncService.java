package com.qding.bigdata.service;

import com.qding.bigdata.model.DBColumnSchema;
import com.qding.bigdata.model.DbConfig;

import java.util.List;

/**
 * @author yanpf
 * @date 2019/6/13 10:15
 * @description
 */
public interface DbSyncService {

    List<DBColumnSchema> getDBColumnSchemaList(DbConfig dbConfig, String dbName, String tableName);
}
