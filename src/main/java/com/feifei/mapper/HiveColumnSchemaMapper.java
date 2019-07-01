package com.qding.bigdata.mapper;

import com.qding.bigdata.model.HiveColumnSchema;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface HiveColumnSchemaMapper {
    @Delete({
        "delete from hive_column_schema",
        "where column_id = #{columnId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long columnId);

    @Insert({
        "insert into hive_column_schema (column_id, table_id, ",
        "name, column_type, partition_type, ",
        "data_type, character_max_length, ",
        "numeric_precision, numeric_scale, ",
        "is_nullable, comment, ",
        "full_data_type, default_value)",
        "values (#{columnId,jdbcType=BIGINT}, #{tableId,jdbcType=BIGINT}, ",
        "#{name,jdbcType=CHAR}, #{columnType,jdbcType=CHAR}, #{partitionType,jdbcType=CHAR}, ",
        "#{dataType,jdbcType=CHAR}, #{characterMaxLength,jdbcType=BIGINT}, ",
        "#{numericPrecision,jdbcType=BIGINT}, #{numericScale,jdbcType=BIGINT}, ",
        "#{isNullable,jdbcType=TINYINT}, #{comment,jdbcType=VARCHAR}, ",
        "#{fullDataType,jdbcType=LONGVARCHAR}, #{defaultValue,jdbcType=LONGVARCHAR})"
    })
    int insert(HiveColumnSchema record);

    @InsertProvider(type=HiveColumnSchemaSqlProvider.class, method="insertSelective")
    int insertSelective(HiveColumnSchema record);

    @Select({
        "select",
        "column_id, table_id, name, column_type, partition_type, data_type, character_max_length, ",
        "numeric_precision, numeric_scale, is_nullable, comment, full_data_type, default_value",
        "from hive_column_schema",
        "where column_id = #{columnId,jdbcType=BIGINT}"
    })
    @Results(id="hiveColumnSchema", value = {
        @Result(column="column_id", property="columnId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="table_id", property="tableId", jdbcType=JdbcType.BIGINT),
        @Result(column="name", property="name", jdbcType=JdbcType.CHAR),
        @Result(column="column_type", property="columnType", jdbcType=JdbcType.CHAR),
        @Result(column="partition_type", property="partitionType", jdbcType=JdbcType.CHAR),
        @Result(column="data_type", property="dataType", jdbcType=JdbcType.CHAR),
        @Result(column="character_max_length", property="characterMaxLength", jdbcType=JdbcType.BIGINT),
        @Result(column="numeric_precision", property="numericPrecision", jdbcType=JdbcType.BIGINT),
        @Result(column="numeric_scale", property="numericScale", jdbcType=JdbcType.BIGINT),
        @Result(column="is_nullable", property="isNullable", jdbcType=JdbcType.TINYINT),
        @Result(column="comment", property="comment", jdbcType=JdbcType.VARCHAR),
        @Result(column="full_data_type", property="fullDataType", jdbcType=JdbcType.LONGVARCHAR),
        @Result(column="default_value", property="defaultValue", jdbcType=JdbcType.LONGVARCHAR)
    })
    HiveColumnSchema selectByPrimaryKey(Long columnId);

    @UpdateProvider(type=HiveColumnSchemaSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(HiveColumnSchema record);

    @Update({
        "update hive_column_schema",
        "set table_id = #{tableId,jdbcType=BIGINT},",
          "name = #{name,jdbcType=CHAR},",
          "column_type = #{columnType,jdbcType=CHAR},",
          "partition_type = #{partitionType,jdbcType=CHAR},",
          "data_type = #{dataType,jdbcType=CHAR},",
          "character_max_length = #{characterMaxLength,jdbcType=BIGINT},",
          "numeric_precision = #{numericPrecision,jdbcType=BIGINT},",
          "numeric_scale = #{numericScale,jdbcType=BIGINT},",
          "is_nullable = #{isNullable,jdbcType=TINYINT},",
          "comment = #{comment,jdbcType=VARCHAR},",
          "full_data_type = #{fullDataType,jdbcType=LONGVARCHAR},",
          "default_value = #{defaultValue,jdbcType=LONGVARCHAR}",
        "where column_id = #{columnId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(HiveColumnSchema record);

    @Update({
        "update hive_column_schema",
        "set table_id = #{tableId,jdbcType=BIGINT},",
          "name = #{name,jdbcType=CHAR},",
          "column_type = #{columnType,jdbcType=CHAR},",
          "partition_type = #{partitionType,jdbcType=CHAR},",
          "data_type = #{dataType,jdbcType=CHAR},",
          "character_max_length = #{characterMaxLength,jdbcType=BIGINT},",
          "numeric_precision = #{numericPrecision,jdbcType=BIGINT},",
          "numeric_scale = #{numericScale,jdbcType=BIGINT},",
          "is_nullable = #{isNullable,jdbcType=TINYINT},",
          "comment = #{comment,jdbcType=VARCHAR}",
        "where column_id = #{columnId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(HiveColumnSchema record);

    @Select({
            "select * from hive_column_schema",
            "where table_id = #{tableId,jdbcType=BIGINT}",
            "and column_type='PARTITION'"
    })
    @ResultMap("hiveColumnSchema")
    HiveColumnSchema getPartitionHiveColumn(Long tableId);
}