package com.qding.bigdata.mapper;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.qding.bigdata.model.HiveColumnSchema;

public class HiveColumnSchemaSqlProvider {

    public String insertSelective(HiveColumnSchema record) {
        BEGIN();
        INSERT_INTO("hive_column_schema");
        
        if (record.getColumnId() != null) {
            VALUES("column_id", "#{columnId,jdbcType=BIGINT}");
        }
        
        if (record.getTableId() != null) {
            VALUES("table_id", "#{tableId,jdbcType=BIGINT}");
        }
        
        if (record.getName() != null) {
            VALUES("name", "#{name,jdbcType=CHAR}");
        }
        
        if (record.getColumnType() != null) {
            VALUES("column_type", "#{columnType,jdbcType=CHAR}");
        }
        
        if (record.getPartitionType() != null) {
            VALUES("partition_type", "#{partitionType,jdbcType=CHAR}");
        }
        
        if (record.getDataType() != null) {
            VALUES("data_type", "#{dataType,jdbcType=CHAR}");
        }
        
        if (record.getCharacterMaxLength() != null) {
            VALUES("character_max_length", "#{characterMaxLength,jdbcType=BIGINT}");
        }
        
        if (record.getNumericPrecision() != null) {
            VALUES("numeric_precision", "#{numericPrecision,jdbcType=BIGINT}");
        }
        
        if (record.getNumericScale() != null) {
            VALUES("numeric_scale", "#{numericScale,jdbcType=BIGINT}");
        }
        
        if (record.getIsNullable() != null) {
            VALUES("is_nullable", "#{isNullable,jdbcType=TINYINT}");
        }
        
        if (record.getComment() != null) {
            VALUES("comment", "#{comment,jdbcType=VARCHAR}");
        }
        
        if (record.getFullDataType() != null) {
            VALUES("full_data_type", "#{fullDataType,jdbcType=LONGVARCHAR}");
        }
        
        if (record.getDefaultValue() != null) {
            VALUES("default_value", "#{defaultValue,jdbcType=LONGVARCHAR}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(HiveColumnSchema record) {
        BEGIN();
        UPDATE("hive_column_schema");
        
        if (record.getTableId() != null) {
            SET("table_id = #{tableId,jdbcType=BIGINT}");
        }
        
        if (record.getName() != null) {
            SET("name = #{name,jdbcType=CHAR}");
        }
        
        if (record.getColumnType() != null) {
            SET("column_type = #{columnType,jdbcType=CHAR}");
        }
        
        if (record.getPartitionType() != null) {
            SET("partition_type = #{partitionType,jdbcType=CHAR}");
        }
        
        if (record.getDataType() != null) {
            SET("data_type = #{dataType,jdbcType=CHAR}");
        }
        
        if (record.getCharacterMaxLength() != null) {
            SET("character_max_length = #{characterMaxLength,jdbcType=BIGINT}");
        }
        
        if (record.getNumericPrecision() != null) {
            SET("numeric_precision = #{numericPrecision,jdbcType=BIGINT}");
        }
        
        if (record.getNumericScale() != null) {
            SET("numeric_scale = #{numericScale,jdbcType=BIGINT}");
        }
        
        if (record.getIsNullable() != null) {
            SET("is_nullable = #{isNullable,jdbcType=TINYINT}");
        }
        
        if (record.getComment() != null) {
            SET("comment = #{comment,jdbcType=VARCHAR}");
        }
        
        if (record.getFullDataType() != null) {
            SET("full_data_type = #{fullDataType,jdbcType=LONGVARCHAR}");
        }
        
        if (record.getDefaultValue() != null) {
            SET("default_value = #{defaultValue,jdbcType=LONGVARCHAR}");
        }
        
        WHERE("column_id = #{columnId,jdbcType=BIGINT}");
        
        return SQL();
    }
}