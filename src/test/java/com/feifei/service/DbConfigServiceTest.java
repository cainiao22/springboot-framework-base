package com.qding.bigdata.service;

import com.qding.bigdata.config.columntype.ColumnTypeConfigure;
import com.qding.bigdata.config.columntype.JdbcConfig;
import com.qding.bigdata.mapper.mysql.DBColumnSchemaMapper;
import com.qding.bigdata.mapper.mysql.DBTableSchemaMapper;
import com.qding.bigdata.model.DBColumnSchema;
import com.qding.bigdata.model.DBTableSchema;
import com.qding.bigdata.model.DbConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * @author yanpf
 * @date 2019/6/10 18:12
 * @description
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class DbConfigServiceTest {

    @Autowired
    DbConfigService dbConfigService;

    @Autowired
    DBTableSchemaMapper dbTableSchemaMapper;

    @Autowired
    DBColumnSchemaMapper dbColunmMapper;

    @Test
    public void getDbConfigByName() {
        DbConfig configByName = dbConfigService.getDbConfigByName("test");
        System.out.println(configByName);
    }

    @Test
    public void testTableSchema(){
        DBTableSchema tableSchema = dbTableSchemaMapper.getDBTableSchema("user", "ds");
        System.out.println(tableSchema);
    }

    @Test
    public void testColumnSchema(){
        List<DBColumnSchema> tableSchema = dbColunmMapper.selectByTable("user", "ds");
        System.out.println(tableSchema);
    }

    @Test
    public void testConfigure(){
        JdbcConfig hive = ColumnTypeConfigure.getJdbcConfig("hive");
        System.out.println(hive);
    }
}