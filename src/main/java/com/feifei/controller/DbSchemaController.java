package com.qding.bigdata.controller;

import com.qding.bigdata.model.DBTableSchema;
import com.qding.bigdata.model.DbConfig;
import com.qding.bigdata.po.DbTableSchemaPo;
import com.qding.bigdata.service.DbConfigService;
import com.qding.bigdata.service.DbSchemaService;
import com.qding.bigdata.vo.DbSchemaVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yanpf
 * @date 2019/6/18 14:49
 * @description
 * @Validated 这个用于基本类型的校验，也就是在controller里面直接做校验
 */

@Validated
@Api(tags = "业务库相关信息")
@RestController
public class DbSchemaController {

    @Autowired
    DbConfigService dbConfigService;

    @Autowired
    DbSchemaService dbSchemaService;


    @ApiOperation(value = "获取所有配置库的连接信")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功"),
            @ApiResponse(code = 500, message = "服务不可用"),
    })
    @PostMapping("listAllDbConfigs")
    public List<DbConfig> listAllDbConfigs(){
        return dbConfigService.listAllDbConfigs();
    }

    @ApiOperation(value = "获取某个连接配置下的所有库")
    @PostMapping("listDbSchemasByName")
    public List<DbSchemaVo> listDbSchemasById(@RequestBody String dbConfigName){
        List<DbSchemaVo> schemaVos = dbSchemaService.listDbSchemasById(dbConfigName);
        return schemaVos;
    }


    @ApiOperation("获取表的基本信息")
    @PostMapping("dbTableSchema")
    public DBTableSchema dbTableSchema(@Validated DbTableSchemaPo dbTableSchemaPo){
        DBTableSchema dbTableSchema = dbSchemaService.getDbTableSchema(dbTableSchemaPo);
        return dbTableSchema;
    }
}
