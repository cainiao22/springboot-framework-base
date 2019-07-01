package com.qding.bigdata.service.impl;

import com.qding.bigdata.annotations.DataSourceRoute;
import com.qding.bigdata.mapper.mysql.DBColumnSchemaMapper;
import com.qding.bigdata.model.DBColumnSchema;
import com.qding.bigdata.model.DbConfig;
import com.qding.bigdata.service.DbSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yanpf
 * @date 2019/6/13 10:17
 * @description 业务库的表信息获取
 */

@Service
public class DbSyncServiceImpl implements DbSyncService {

    @Autowired
    DBColumnSchemaMapper dbColumnSchemaMapper;

    @Override
    @DataSourceRoute("#dbConfig.name")
    public List<DBColumnSchema> getDBColumnSchemaList(DbConfig dbConfig, String dbName, String tableName) {

        return dbColumnSchemaMapper.selectByTable(tableName, dbName);
    }
}
