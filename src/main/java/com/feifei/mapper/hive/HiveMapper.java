package com.qding.bigdata.mapper.hive;

import org.apache.ibatis.annotations.Update;

/**
 * @author yanpf
 * @date 2019/6/11 15:09
 * @description
 */
public interface HiveMapper {

    /**
     * 执行DML操作
     * @param sql 必须提前拼好，这个是通过freemarker模板实现的
     */
    @Update({
            "#{sql}"
    })
    void execute(String sql);
}
