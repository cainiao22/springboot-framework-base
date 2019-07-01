package com.qding.bigdata.mapper.mysql;

import com.qding.bigdata.model.DBTableSchema;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface DBTableSchemaMapper {

    @Results({
            @Result(column="TABLE_CATALOG", property="tableCatalog", jdbcType=JdbcType.VARCHAR),
            @Result(column="TABLE_SCHEMA", property="tableSchema", jdbcType=JdbcType.VARCHAR),
            @Result(column="TABLE_NAME", property="tableName", jdbcType=JdbcType.VARCHAR),
            @Result(column="TABLE_TYPE", property="tableType", jdbcType=JdbcType.VARCHAR),
            @Result(column="ENGINE", property="engine", jdbcType=JdbcType.VARCHAR),
            @Result(column="VERSION", property="version", jdbcType=JdbcType.BIGINT),
            @Result(column="ROW_FORMAT", property="rowFormat", jdbcType=JdbcType.VARCHAR),
            @Result(column="TABLE_ROWS", property="tableRows", jdbcType=JdbcType.BIGINT),
            @Result(column="AVG_ROW_LENGTH", property="avgRowLength", jdbcType=JdbcType.BIGINT),
            @Result(column="DATA_LENGTH", property="dataLength", jdbcType=JdbcType.BIGINT),
            @Result(column="MAX_DATA_LENGTH", property="maxDataLength", jdbcType=JdbcType.BIGINT),
            @Result(column="INDEX_LENGTH", property="indexLength", jdbcType=JdbcType.BIGINT),
            @Result(column="DATA_FREE", property="dataFree", jdbcType=JdbcType.BIGINT),
            @Result(column="AUTO_INCREMENT", property="autoIncrement", jdbcType=JdbcType.BIGINT),
            @Result(column="CREATE_TIME", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="UPDATE_TIME", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="CHECK_TIME", property="checkTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="TABLE_COLLATION", property="tableCollation", jdbcType=JdbcType.VARCHAR),
            @Result(column="CHECKSUM", property="checksum", jdbcType=JdbcType.BIGINT),
            @Result(column="CREATE_OPTIONS", property="createOptions", jdbcType=JdbcType.VARCHAR),
            @Result(column="TABLE_COMMENT", property="tableComment", jdbcType=JdbcType.VARCHAR)
    })
    @Select({
            "select * from information_schema.tables",
            "where table_schema = #{dbName} and table_name = #{tableName}"
    })
    DBTableSchema getDBTableSchema(String tableName, String dbName);

    @Select({"show tables from ${dbName}"})
    List<String> getTableNames(@Param("dbName") String dbName);
}