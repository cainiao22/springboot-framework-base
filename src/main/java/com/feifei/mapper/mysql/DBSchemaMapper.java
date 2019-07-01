package com.qding.bigdata.mapper.mysql;

import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author yanpf
 * @date 2019/6/18 16:02
 * @description
 */
public interface DBSchemaMapper {

    @Select("show databases")
    List<String> showDataBases();
}
