package com.qding.bigdata.mapper;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.qding.bigdata.model.DbSyncLogs;

public class DbSyncLogsSqlProvider {

    public String insertSelective(DbSyncLogs record) {
        BEGIN();
        INSERT_INTO("db_sync_logs");
        
        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getDbSyncConfigId() != null) {
            VALUES("db_sync_config_id", "#{dbSyncConfigId,jdbcType=BIGINT}");
        }
        
        if (record.getSyncStatus() != null) {
            VALUES("sync_status", "#{syncStatus,jdbcType=CHAR}");
        }
        
        if (record.getStartTime() != null) {
            VALUES("start_time", "#{startTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getEndTime() != null) {
            VALUES("end_time", "#{endTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getRemark() != null) {
            VALUES("remark", "#{remark,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(DbSyncLogs record) {
        BEGIN();
        UPDATE("db_sync_logs");
        
        if (record.getDbSyncConfigId() != null) {
            SET("db_sync_config_id = #{dbSyncConfigId,jdbcType=BIGINT}");
        }
        
        if (record.getSyncStatus() != null) {
            SET("sync_status = #{syncStatus,jdbcType=CHAR}");
        }
        
        if (record.getStartTime() != null) {
            SET("start_time = #{startTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getEndTime() != null) {
            SET("end_time = #{endTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getRemark() != null) {
            SET("remark = #{remark,jdbcType=VARCHAR}");
        }
        
        WHERE("id = #{id,jdbcType=BIGINT}");
        
        return SQL();
    }
}