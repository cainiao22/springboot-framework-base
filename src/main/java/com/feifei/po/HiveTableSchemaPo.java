package com.qding.bigdata.po;

import lombok.Data;

import java.util.List;

/**
 * @author yanpf
 * @date 2019/6/11 18:06
 * @description
 */

@Data
public class HiveTableSchemaPo {

    private String dbConfigName;

    private String sourceDbName;

    private String sourceTableName;

    private List<DbSynConfigPo> dbSynConfigPoList;
}
