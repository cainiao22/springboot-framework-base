package com.qding.bigdata.mapper;

import com.qding.bigdata.model.DbConfig;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface DbConfigMapper {
    @Delete({
            "delete from db_config",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
            "insert into db_config (id, name, db_type, ",
            "url, user_name, password, ",
            "comment, autoDestroy)",
            "values (#{id,jdbcType=BIGINT}, #{name,jdbcType=CHAR}, #{dbType,jdbcType=CHAR}, ",
            "#{url,jdbcType=VARCHAR}, #{userName,jdbcType=CHAR}, #{password,jdbcType=CHAR}, ",
            "#{comment,jdbcType=VARCHAR}, #{autodestroy,jdbcType=BIT})"
    })
    int insert(DbConfig record);

    @InsertProvider(type = DbConfigSqlProvider.class, method = "insertSelective")
    int insertSelective(DbConfig record);

    @Select({
            "select",
            "id, name, db_type, url, user_name, password, comment, autoDestroy",
            "from db_config",
            "where id = #{id,jdbcType=BIGINT}"
    })
    @Results(id = "dbConfig", value = {
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "name", property = "name", jdbcType = JdbcType.CHAR),
            @Result(column = "db_type", property = "dbType", jdbcType = JdbcType.CHAR),
            @Result(column = "url", property = "url", jdbcType = JdbcType.VARCHAR),
            @Result(column = "user_name", property = "userName", jdbcType = JdbcType.CHAR),
            @Result(column = "password", property = "password", jdbcType = JdbcType.CHAR),
            @Result(column = "comment", property = "comment", jdbcType = JdbcType.VARCHAR),
            @Result(column = "autoDestroy", property = "autodestroy", jdbcType = JdbcType.BIT)
    })
    DbConfig selectByPrimaryKey(Long id);

    @UpdateProvider(type = DbConfigSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(DbConfig record);

    @Update({
            "update db_config",
            "set name = #{name,jdbcType=CHAR},",
            "db_type = #{dbType,jdbcType=CHAR},",
            "url = #{url,jdbcType=VARCHAR},",
            "user_name = #{userName,jdbcType=CHAR},",
            "password = #{password,jdbcType=CHAR},",
            "comment = #{comment,jdbcType=VARCHAR},",
            "autoDestroy = #{autodestroy,jdbcType=BIT}",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(DbConfig record);

    @Select({
            "select",
            "id, name, db_type, url, user_name, password, comment, autoDestroy",
            "from db_config",
            "where name = #{name,jdbcType=CHAR}"
    })
    @ResultMap(value = "dbConfig")
    DbConfig selectByName(String name);

    @Select({
            "select",
            "id, name, db_type, url, user_name, password, comment, autoDestroy",
            "from db_config"
    })
    @ResultMap(value = "dbConfig")
    List<DbConfig> listAllDbConfigs();
}