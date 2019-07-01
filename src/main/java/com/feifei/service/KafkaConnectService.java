package com.qding.bigdata.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author yanpf
 * @date 2019/6/26 10:03
 * @description
 */

@FeignClient(name = "kafkaConnector", url = "http://10.50.8.241:8083/")
public interface KafkaConnectService {

    @RequestMapping("connectors")
    List<String> getAll();
}
