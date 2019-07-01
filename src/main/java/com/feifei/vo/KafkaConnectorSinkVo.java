package com.qding.bigdata.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author yanpf
 * @date 2019/6/26 10:13
 * @description
 */

@Data
public class KafkaConnectorSinkVo {

    private String name;

    private Map<String, String> config;

    private List<Map<String, String>> tasks;

    private String type;
}
