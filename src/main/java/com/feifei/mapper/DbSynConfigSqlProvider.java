package com.qding.bigdata.mapper;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.qding.bigdata.model.DbSynConfig;

public class DbSynConfigSqlProvider {

    public String insertSelective(DbSynConfig record) {
        BEGIN();
        INSERT_INTO("db_syn_config");
        
        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getDbConfigId() != null) {
            VALUES("db_config_id", "#{dbConfigId,jdbcType=BIGINT}");
        }
        
        if (record.getSourceDbName() != null) {
            VALUES("source_db_name", "#{sourceDbName,jdbcType=CHAR}");
        }
        
        if (record.getSourceTableName() != null) {
            VALUES("source_table_name", "#{sourceTableName,jdbcType=CHAR}");
        }
        
        if (record.getDestDbName() != null) {
            VALUES("dest_db_name", "#{destDbName,jdbcType=CHAR}");
        }
        
        if (record.getDestTableName() != null) {
            VALUES("dest_table_name", "#{destTableName,jdbcType=CHAR}");
        }
        
        if (record.getSyncType() != null) {
            VALUES("sync_type", "#{syncType,jdbcType=CHAR}");
        }
        
        if (record.getCrontab() != null) {
            VALUES("crontab", "#{crontab,jdbcType=VARCHAR}");
        }
        
        if (record.getSyncTimes() != null) {
            VALUES("sync_times", "#{syncTimes,jdbcType=INTEGER}");
        }
        
        if (record.getHiveTableId() != null) {
            VALUES("hive_table_id", "#{hiveTableId,jdbcType=BIGINT}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(DbSynConfig record) {
        BEGIN();
        UPDATE("db_syn_config");
        
        if (record.getDbConfigId() != null) {
            SET("db_config_id = #{dbConfigId,jdbcType=BIGINT}");
        }
        
        if (record.getSourceDbName() != null) {
            SET("source_db_name = #{sourceDbName,jdbcType=CHAR}");
        }
        
        if (record.getSourceTableName() != null) {
            SET("source_table_name = #{sourceTableName,jdbcType=CHAR}");
        }
        
        if (record.getDestDbName() != null) {
            SET("dest_db_name = #{destDbName,jdbcType=CHAR}");
        }
        
        if (record.getDestTableName() != null) {
            SET("dest_table_name = #{destTableName,jdbcType=CHAR}");
        }
        
        if (record.getSyncType() != null) {
            SET("sync_type = #{syncType,jdbcType=CHAR}");
        }
        
        if (record.getCrontab() != null) {
            SET("crontab = #{crontab,jdbcType=VARCHAR}");
        }
        
        if (record.getSyncTimes() != null) {
            SET("sync_times = #{syncTimes,jdbcType=INTEGER}");
        }
        
        if (record.getHiveTableId() != null) {
            SET("hive_table_id = #{hiveTableId,jdbcType=BIGINT}");
        }
        
        WHERE("id = #{id,jdbcType=BIGINT}");
        
        return SQL();
    }
}