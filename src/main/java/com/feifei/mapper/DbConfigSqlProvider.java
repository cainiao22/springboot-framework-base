package com.qding.bigdata.mapper;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.qding.bigdata.model.DbConfig;

public class DbConfigSqlProvider {

    public String insertSelective(DbConfig record) {
        BEGIN();
        INSERT_INTO("db_config");
        
        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getName() != null) {
            VALUES("name", "#{name,jdbcType=CHAR}");
        }
        
        if (record.getDbType() != null) {
            VALUES("db_type", "#{dbType,jdbcType=CHAR}");
        }
        
        if (record.getUrl() != null) {
            VALUES("url", "#{url,jdbcType=VARCHAR}");
        }
        
        if (record.getUserName() != null) {
            VALUES("user_name", "#{userName,jdbcType=CHAR}");
        }
        
        if (record.getPassword() != null) {
            VALUES("password", "#{password,jdbcType=CHAR}");
        }
        
        if (record.getComment() != null) {
            VALUES("comment", "#{comment,jdbcType=VARCHAR}");
        }
        
        if (record.getAutodestroy() != null) {
            VALUES("autoDestroy", "#{autodestroy,jdbcType=BIT}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(DbConfig record) {
        BEGIN();
        UPDATE("db_config");
        
        if (record.getName() != null) {
            SET("name = #{name,jdbcType=CHAR}");
        }
        
        if (record.getDbType() != null) {
            SET("db_type = #{dbType,jdbcType=CHAR}");
        }
        
        if (record.getUrl() != null) {
            SET("url = #{url,jdbcType=VARCHAR}");
        }
        
        if (record.getUserName() != null) {
            SET("user_name = #{userName,jdbcType=CHAR}");
        }
        
        if (record.getPassword() != null) {
            SET("password = #{password,jdbcType=CHAR}");
        }
        
        if (record.getComment() != null) {
            SET("comment = #{comment,jdbcType=VARCHAR}");
        }
        
        if (record.getAutodestroy() != null) {
            SET("autoDestroy = #{autodestroy,jdbcType=BIT}");
        }
        
        WHERE("id = #{id,jdbcType=BIGINT}");
        
        return SQL();
    }
}