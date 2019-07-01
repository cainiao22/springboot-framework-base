package com.qding.bigdata.model;

import lombok.Data;

@Data
public class HiveTableSchema {
    private Long tableId;

    private String dbName;

    private String tableName;

    private Boolean temporary;

    private Boolean external;

    private String tableType;

    private String comment;

    private String storeType;

    private String location;

    private String fieldsTerminate;

    private String linesTerminate;
}