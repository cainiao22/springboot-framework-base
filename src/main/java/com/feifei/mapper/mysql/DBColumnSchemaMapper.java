package com.qding.bigdata.mapper.mysql;

import com.qding.bigdata.model.DBColumnSchema;

import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface DBColumnSchemaMapper {

    @Results({
        @Result(column="TABLE_CATALOG", property="tableCatalog", jdbcType=JdbcType.VARCHAR),
        @Result(column="TABLE_SCHEMA", property="tableSchema", jdbcType=JdbcType.VARCHAR),
        @Result(column="TABLE_NAME", property="tableName", jdbcType=JdbcType.VARCHAR),
        @Result(column="COLUMN_NAME", property="columnName", jdbcType=JdbcType.VARCHAR),
        @Result(column="ORDINAL_POSITION", property="ordinalPosition", jdbcType=JdbcType.BIGINT),
        @Result(column="IS_NULLABLE", property="isNullable", jdbcType=JdbcType.VARCHAR),
        @Result(column="DATA_TYPE", property="dataType", jdbcType=JdbcType.VARCHAR),
        @Result(column="CHARACTER_MAXIMUM_LENGTH", property="characterMaximumLength", jdbcType=JdbcType.BIGINT),
        @Result(column="CHARACTER_OCTET_LENGTH", property="characterOctetLength", jdbcType=JdbcType.BIGINT),
        @Result(column="NUMERIC_PRECISION", property="numericPrecision", jdbcType=JdbcType.BIGINT),
        @Result(column="NUMERIC_SCALE", property="numericScale", jdbcType=JdbcType.BIGINT),
        @Result(column="DATETIME_PRECISION", property="datetimePrecision", jdbcType=JdbcType.BIGINT),
        @Result(column="CHARACTER_SET_NAME", property="characterSetName", jdbcType=JdbcType.VARCHAR),
        @Result(column="COLLATION_NAME", property="collationName", jdbcType=JdbcType.VARCHAR),
        @Result(column="COLUMN_KEY", property="columnKey", jdbcType=JdbcType.VARCHAR),
        @Result(column="EXTRA", property="extra", jdbcType=JdbcType.VARCHAR),
        @Result(column="PRIVILEGES", property="privileges", jdbcType=JdbcType.VARCHAR),
        @Result(column="COLUMN_COMMENT", property="columnComment", jdbcType=JdbcType.VARCHAR)
    })
    @Select({
           "select * from information_schema.columns",
           "where table_schema =#{dbName}  and table_name = #{tableName}"
    })
    List<DBColumnSchema> selectByTable(String tableName, String dbName);
}