package com.qding.bigdata.mapper;

import com.qding.bigdata.model.DbSyncLogs;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface DbSyncLogsMapper {
    @Delete({
        "delete from db_sync_logs",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into db_sync_logs (id, db_sync_config_id, ",
        "sync_status, start_time, ",
        "end_time, remark)",
        "values (#{id,jdbcType=BIGINT}, #{dbSyncConfigId,jdbcType=BIGINT}, ",
        "#{syncStatus,jdbcType=CHAR}, #{startTime,jdbcType=TIMESTAMP}, ",
        "#{endTime,jdbcType=TIMESTAMP}, #{remark,jdbcType=VARCHAR})"
    })
    int insert(DbSyncLogs record);

    @InsertProvider(type=DbSyncLogsSqlProvider.class, method="insertSelective")
    int insertSelective(DbSyncLogs record);

    @Select({
        "select",
        "id, db_sync_config_id, sync_status, start_time, end_time, remark",
        "from db_sync_logs",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="db_sync_config_id", property="dbSyncConfigId", jdbcType=JdbcType.BIGINT),
        @Result(column="sync_status", property="syncStatus", jdbcType=JdbcType.CHAR),
        @Result(column="start_time", property="startTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="end_time", property="endTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.VARCHAR)
    })
    DbSyncLogs selectByPrimaryKey(Long id);

    @UpdateProvider(type=DbSyncLogsSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(DbSyncLogs record);

    @Update({
        "update db_sync_logs",
        "set db_sync_config_id = #{dbSyncConfigId,jdbcType=BIGINT},",
          "sync_status = #{syncStatus,jdbcType=CHAR},",
          "start_time = #{startTime,jdbcType=TIMESTAMP},",
          "end_time = #{endTime,jdbcType=TIMESTAMP},",
          "remark = #{remark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(DbSyncLogs record);
}