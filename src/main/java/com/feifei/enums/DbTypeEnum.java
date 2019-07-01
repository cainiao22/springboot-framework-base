package com.qding.bigdata.enums;

/**
 * @author yanpf
 * @date 2019/6/10 18:54
 * @description
 */
public enum DbTypeEnum {

    MYSQL("com.mysql.cj.jdbc.Driver"),
    HIVE("org.apache.hive.jdbc.HiveDriver");

    String driver;

    DbTypeEnum(String driver){
        this.driver = driver;
    }

    public String getDriver() {
        return driver;
    }
}
