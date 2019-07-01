package com.qding.bigdata.model;

import lombok.Data;

@Data
public class HiveColumnSchema {
    private Long columnId;

    private Long tableId;

    private String name;

    private String columnType;

    private String partitionType;

    private String dataType;

    private Long characterMaxLength;

    private Long numericPrecision;

    private Long numericScale;

    private Boolean isNullable;

    private String comment;

    private String fullDataType;

    private String defaultValue;
}