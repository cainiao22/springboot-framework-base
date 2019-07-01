package com.qding.bigdata.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qding.bigdata.vo.KafkaConnectorSinkVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * @author yanpf
 * @date 2019/6/26 10:05
 * @description
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class KafkaConnectServiceTest {

    @Autowired
    KafkaConnectService kafkaConnectService;

    @Autowired
    KafkaSinkConnector kafkaSinkConnector;

    @Test
    public void testGetAll(){
        KafkaConnectorSinkVo sink = kafkaSinkConnector.getSinkByName("hdfs-sink-student");
        System.out.println(sink);
    }

    @Test
    public void testDecode(){
        String json = "{" +
                "    \"name\": \"hdfs-sink-student\"," +
                "    \"config\": {" +
                "        \"connector.class\": \"io.confluent.connect.hdfs.HdfsSinkConnector\"," +
                "        \"topics.dir\": \"/data/kafka_connect\"," +
                "        \"flush.size\": \"3\"," +
                "        \"schema.compatibility\": \"FULL\"," +
                "        \"tasks.max\": \"1\"," +
                "        \"topics\": \"mysql.devds.student_test\"," +
                "        \"timezone\": \"UTC\"," +
                "        \"hive.home\": \"/data/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hive\"," +
                "        \"hdfs.url\": \"hdfs://10.50.8.112:8020\"," +
                "        \"hive.database\": \"test\"," +
                "        \"hive.metastore.uris\": \"thrift://BJ-HOST-112:9083\"," +
                "        \"locale\": \"en_US\"," +
                "        \"partition.field.name\": \"day\"," +
                "        \"format.class\": \"io.confluent.connect.hdfs.string.StringFormat\"," +
                "        \"hive.integration\": \"true\"," +
                "        \"partitioner.class\": \"io.confluent.connect.storage.partitioner.DailyPartitioner\"," +
                "        \"name\": \"hdfs-sink-student\"," +
                "        \"hive.conf.dir\": \"/data/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hive/conf\"" +
                "    }," +
                "    \"tasks\": [" +
                "        {" +
                "            \"connector\": \"hdfs-sink-student\"," +
                "            \"task\": 0" +
                "        }" +
                "    ]," +
                "    \"type\": \"sink\"" +
                "}";

        JSONObject jsonObject = JSON.parseObject(json);
        KafkaConnectorSinkVo sinkVo = JSON.parseObject(json, KafkaConnectorSinkVo.class);
        System.out.println(sinkVo);
    }
}
