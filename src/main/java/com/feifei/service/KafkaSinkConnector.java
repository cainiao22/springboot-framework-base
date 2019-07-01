package com.qding.bigdata.service;

import com.qding.bigdata.vo.KafkaConnectorSinkVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author yanpf
 * @date 2019/6/26 10:12
 * @description
 */

@FeignClient(name = "KafkaSinkConnector", url = "http://m7-qding-bd-241:8083/connectors/")
public interface KafkaSinkConnector {

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    KafkaConnectorSinkVo getSinkByName(@PathVariable String name);
}
