package com.qding.bigdata.service.impl;

import com.qding.bigdata.po.DbSynConfigItemPo;
import com.qding.bigdata.po.DbSynConfigPo;
import com.qding.bigdata.service.HiveTableSchemaService;
import com.qding.bigdata.utils.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;


/**
 * @author yanpf
 * @date 2019/6/11 16:58
 * @description
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class HiveTableSchemaServiceImplTest {


    @Autowired
    HiveTableSchemaService hiveTableSchemaService;

    @Test
    public void testHiveSchema() throws SchedulerException {
        DbSynConfigPo po = new DbSynConfigPo();
        po.setDbConfigId(1L);
        po.setSourceDbName("ds");
        po.setSourceTableName("user");
        po.setSynConfigItemPoList(new ArrayList<>());
        DbSynConfigItemPo itemPo = new DbSynConfigItemPo();
        itemPo.setComment("测试表的注释");
        itemPo.setCrontab("");
        itemPo.setDestDbName("ds");
        itemPo.setDestTableName("user");
        itemPo.setSyncTimes(1);
        itemPo.setSyncType("DATA");
        po.getSynConfigItemPoList().add(itemPo);
        Result<String> result = hiveTableSchemaService.saveSyncConfig(po);
        System.out.println(result);
    }

}