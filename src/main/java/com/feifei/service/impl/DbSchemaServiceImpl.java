package com.qding.bigdata.service.impl;

import com.qding.bigdata.annotations.DataSourceRoute;
import com.qding.bigdata.mapper.mysql.DBSchemaMapper;
import com.qding.bigdata.mapper.mysql.DBTableSchemaMapper;
import com.qding.bigdata.model.DBTableSchema;
import com.qding.bigdata.po.DbTableSchemaPo;
import com.qding.bigdata.service.DbSchemaService;
import com.qding.bigdata.vo.DbSchemaVo;
import com.qding.bigdata.vo.DbTableSchemaVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanpf
 * @date 2019/6/18 16:05
 * @description
 */

@Service
public class DbSchemaServiceImpl implements DbSchemaService {

    @Autowired
    DBSchemaMapper dbSchemaMapper;

    @Autowired
    DBTableSchemaMapper dbTableSchemaMapper;


    @Override
    @DataSourceRoute("#dbConfigName")
    public List<DbSchemaVo> listDbSchemasById(String dbConfigName) {
        List<String> dataBases = dbSchemaMapper.showDataBases();
        List<DbSchemaVo> result = new ArrayList<>();
        for (String databaseName : dataBases) {
            if("information_schema".equals(databaseName) || "sys".equals(databaseName)){
                continue;
            }
            DbSchemaVo dbSchemaVo = new DbSchemaVo();
            dbSchemaVo.setDbName(databaseName);
            dbSchemaVo.setTableSchemaVoList(new ArrayList<>());
            List<String> tableNames = dbTableSchemaMapper.getTableNames(databaseName);
            for (String tableName : tableNames) {
                DbTableSchemaVo vo = new DbTableSchemaVo();
                vo.setName(tableName);
                dbSchemaVo.getTableSchemaVoList().add(vo);
            }

            result.add(dbSchemaVo);
        }

        return result;
    }

    @Override
    @DataSourceRoute("#po.dbConfigName")
    public DBTableSchema getDbTableSchema(DbTableSchemaPo po) {
        return dbTableSchemaMapper.getDBTableSchema(po.getTableName(), po.getDbName());
    }
}
