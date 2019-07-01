package com.qding.bigdata.service;

import com.qding.bigdata.model.DBTableSchema;
import com.qding.bigdata.po.DbTableSchemaPo;
import com.qding.bigdata.vo.DbSchemaVo;

import java.util.List;

/**
 * @author yanpf
 * @date 2019/6/18 16:04
 * @description
 */
public interface DbSchemaService {

    List<DbSchemaVo> listDbSchemasById(String dbConfigName);

    DBTableSchema getDbTableSchema(DbTableSchemaPo po);
}
