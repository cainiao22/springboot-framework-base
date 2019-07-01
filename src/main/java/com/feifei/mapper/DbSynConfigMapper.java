package com.qding.bigdata.mapper;

import com.qding.bigdata.model.DbSynConfig;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface DbSynConfigMapper {
    @Delete({
        "delete from db_syn_config",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into db_syn_config (id, db_config_id, ",
        "source_db_name, source_table_name, ",
        "dest_db_name, dest_table_name, ",
        "sync_type, crontab, ",
        "sync_times, hive_table_id)",
        "values (#{id,jdbcType=BIGINT}, #{dbConfigId,jdbcType=BIGINT}, ",
        "#{sourceDbName,jdbcType=CHAR}, #{sourceTableName,jdbcType=CHAR}, ",
        "#{destDbName,jdbcType=CHAR}, #{destTableName,jdbcType=CHAR}, ",
        "#{syncType,jdbcType=CHAR}, #{crontab,jdbcType=VARCHAR}, ",
        "#{syncTimes,jdbcType=INTEGER}, #{hiveTableId,jdbcType=BIGINT})"
    })
    int insert(DbSynConfig record);

    @InsertProvider(type=DbSynConfigSqlProvider.class, method="insertSelective")
    int insertSelective(DbSynConfig record);

    @Select({
        "select",
        "id, db_config_id, source_db_name, source_table_name, dest_db_name, dest_table_name, ",
        "sync_type, crontab, sync_times, hive_table_id",
        "from db_syn_config",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="db_config_id", property="dbConfigId", jdbcType=JdbcType.BIGINT),
        @Result(column="source_db_name", property="sourceDbName", jdbcType=JdbcType.CHAR),
        @Result(column="source_table_name", property="sourceTableName", jdbcType=JdbcType.CHAR),
        @Result(column="dest_db_name", property="destDbName", jdbcType=JdbcType.CHAR),
        @Result(column="dest_table_name", property="destTableName", jdbcType=JdbcType.CHAR),
        @Result(column="sync_type", property="syncType", jdbcType=JdbcType.CHAR),
        @Result(column="crontab", property="crontab", jdbcType=JdbcType.VARCHAR),
        @Result(column="sync_times", property="syncTimes", jdbcType=JdbcType.INTEGER),
        @Result(column="hive_table_id", property="hiveTableId", jdbcType=JdbcType.BIGINT)
    })
    DbSynConfig selectByPrimaryKey(Long id);

    @UpdateProvider(type=DbSynConfigSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(DbSynConfig record);

    @Update({
        "update db_syn_config",
        "set db_config_id = #{dbConfigId,jdbcType=BIGINT},",
          "source_db_name = #{sourceDbName,jdbcType=CHAR},",
          "source_table_name = #{sourceTableName,jdbcType=CHAR},",
          "dest_db_name = #{destDbName,jdbcType=CHAR},",
          "dest_table_name = #{destTableName,jdbcType=CHAR},",
          "sync_type = #{syncType,jdbcType=CHAR},",
          "crontab = #{crontab,jdbcType=VARCHAR},",
          "sync_times = #{syncTimes,jdbcType=INTEGER},",
          "hive_table_id = #{hiveTableId,jdbcType=BIGINT}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(DbSynConfig record);
}