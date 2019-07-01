package com.qding.bigdata.mapper;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.qding.bigdata.model.HiveTableSchema;

public class HiveTableSchemaSqlProvider {

    public String insertSelective(HiveTableSchema record) {
        BEGIN();
        INSERT_INTO("hive_table_schema");
        
        if (record.getTableId() != null) {
            VALUES("table_id", "#{tableId,jdbcType=BIGINT}");
        }
        
        if (record.getDbName() != null) {
            VALUES("db_name", "#{dbName,jdbcType=CHAR}");
        }
        
        if (record.getTableName() != null) {
            VALUES("table_name", "#{tableName,jdbcType=CHAR}");
        }
        
        if (record.getTemporary() != null) {
            VALUES("temporary", "#{temporary,jdbcType=TINYINT}");
        }
        
        if (record.getExternal() != null) {
            VALUES("external", "#{external,jdbcType=TINYINT}");
        }
        
        if (record.getTableType() != null) {
            VALUES("table_type", "#{tableType,jdbcType=CHAR}");
        }
        
        if (record.getComment() != null) {
            VALUES("comment", "#{comment,jdbcType=CHAR}");
        }
        
        if (record.getStoreType() != null) {
            VALUES("store_type", "#{storeType,jdbcType=CHAR}");
        }
        
        if (record.getLocation() != null) {
            VALUES("location", "#{location,jdbcType=VARCHAR}");
        }
        
        if (record.getFieldsTerminate() != null) {
            VALUES("fields_terminate", "#{fieldsTerminate,jdbcType=CHAR}");
        }
        
        if (record.getLinesTerminate() != null) {
            VALUES("lines_terminate", "#{linesTerminate,jdbcType=CHAR}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(HiveTableSchema record) {
        BEGIN();
        UPDATE("hive_table_schema");
        
        if (record.getDbName() != null) {
            SET("db_name = #{dbName,jdbcType=CHAR}");
        }
        
        if (record.getTableName() != null) {
            SET("table_name = #{tableName,jdbcType=CHAR}");
        }
        
        if (record.getTemporary() != null) {
            SET("temporary = #{temporary,jdbcType=TINYINT}");
        }
        
        if (record.getExternal() != null) {
            SET("external = #{external,jdbcType=TINYINT}");
        }
        
        if (record.getTableType() != null) {
            SET("table_type = #{tableType,jdbcType=CHAR}");
        }
        
        if (record.getComment() != null) {
            SET("comment = #{comment,jdbcType=CHAR}");
        }
        
        if (record.getStoreType() != null) {
            SET("store_type = #{storeType,jdbcType=CHAR}");
        }
        
        if (record.getLocation() != null) {
            SET("location = #{location,jdbcType=VARCHAR}");
        }
        
        if (record.getFieldsTerminate() != null) {
            SET("fields_terminate = #{fieldsTerminate,jdbcType=CHAR}");
        }
        
        if (record.getLinesTerminate() != null) {
            SET("lines_terminate = #{linesTerminate,jdbcType=CHAR}");
        }
        
        WHERE("table_id = #{tableId,jdbcType=BIGINT}");
        
        return SQL();
    }
}