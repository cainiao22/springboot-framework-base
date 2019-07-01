package com.qding.bigdata.mapper;

import com.qding.bigdata.annotations.DataSourceRoute;
import com.qding.bigdata.model.HiveTableSchema;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;


@DataSourceRoute
public interface HiveTableSchemaMapper {
    @Delete({
        "delete from hive_table_schema",
        "where table_id = #{tableId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long tableId);

    @Insert({
        "insert into hive_table_schema (table_id, db_name, ",
        "table_name, temporary, ",
        "external, table_type, ",
        "comment, store_type, location, ",
        "fields_terminate, lines_terminate)",
        "values (#{tableId,jdbcType=BIGINT}, #{dbName,jdbcType=CHAR}, ",
        "#{tableName,jdbcType=CHAR}, #{temporary,jdbcType=TINYINT}, ",
        "#{external,jdbcType=TINYINT}, #{tableType,jdbcType=CHAR}, ",
        "#{comment,jdbcType=CHAR}, #{storeType,jdbcType=CHAR}, #{location,jdbcType=VARCHAR}, ",
        "#{fieldsTerminate,jdbcType=CHAR}, #{linesTerminate,jdbcType=CHAR})"
    })
    int insert(HiveTableSchema record);

    @Options(useGeneratedKeys = true, keyProperty = "tableId", keyColumn = "table_id")
    @InsertProvider(type=HiveTableSchemaSqlProvider.class, method="insertSelective")
    int insertSelective(HiveTableSchema record);

    @Select({
        "select",
        "table_id, db_name, table_name, temporary, external, table_type, comment, store_type, ",
        "location, fields_terminate, lines_terminate",
        "from hive_table_schema",
        "where table_id = #{tableId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="table_id", property="tableId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="db_name", property="dbName", jdbcType=JdbcType.CHAR),
        @Result(column="table_name", property="tableName", jdbcType=JdbcType.CHAR),
        @Result(column="temporary", property="temporary", jdbcType=JdbcType.TINYINT),
        @Result(column="external", property="external", jdbcType=JdbcType.TINYINT),
        @Result(column="table_type", property="tableType", jdbcType=JdbcType.CHAR),
        @Result(column="comment", property="comment", jdbcType=JdbcType.CHAR),
        @Result(column="store_type", property="storeType", jdbcType=JdbcType.CHAR),
        @Result(column="location", property="location", jdbcType=JdbcType.VARCHAR),
        @Result(column="fields_terminate", property="fieldsTerminate", jdbcType=JdbcType.CHAR),
        @Result(column="lines_terminate", property="linesTerminate", jdbcType=JdbcType.CHAR)
    })
    HiveTableSchema selectByPrimaryKey(Long tableId);

    @UpdateProvider(type=HiveTableSchemaSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(HiveTableSchema record);

    @Update({
        "update hive_table_schema",
        "set db_name = #{dbName,jdbcType=CHAR},",
          "table_name = #{tableName,jdbcType=CHAR},",
          "temporary = #{temporary,jdbcType=TINYINT},",
          "external = #{external,jdbcType=TINYINT},",
          "table_type = #{tableType,jdbcType=CHAR},",
          "comment = #{comment,jdbcType=CHAR},",
          "store_type = #{storeType,jdbcType=CHAR},",
          "location = #{location,jdbcType=VARCHAR},",
          "fields_terminate = #{fieldsTerminate,jdbcType=CHAR},",
          "lines_terminate = #{linesTerminate,jdbcType=CHAR}",
        "where table_id = #{tableId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(HiveTableSchema record);
}