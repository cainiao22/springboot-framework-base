package com.qding.bigdata.model;

import lombok.Data;

@Data
public class DbSynConfig {
    private Long id;

    private Long dbConfigId;

    private String sourceDbName;

    private String sourceTableName;

    private String destDbName;

    private String destTableName;

    private String syncType;

    private String crontab;

    private Integer syncTimes;

    private Long hiveTableId;
}