package com.qding.bigdata.model;

import java.util.Date;
import lombok.Data;

@Data
public class DbSyncLogs {
    private Long id;

    private Long dbSyncConfigId;

    private String syncStatus;

    private Date startTime;

    private Date endTime;

    private String remark;
}