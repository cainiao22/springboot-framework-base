package com.qding.bigdata.config.columntype;

import lombok.Data;

import java.util.HashMap;

/**
 * @author yanpf
 * @date 2018/5/14 15:05
 * @description
 */

@Data
public class JdbcConfig implements Cloneable {

    private String jdbcType;
    /**
     * 从本地类型转换到公共类型的映射关系
     */
    private HashMap<String, String> writerTypes = new HashMap  <>();

    /**
     *  从公共类型映射到本地类型的映射关系
     */
    private HashMap<String, String> readerTypes = new HashMap<>();


    @Override
    public JdbcConfig clone() throws CloneNotSupportedException {
         JdbcConfig clone = (JdbcConfig) super.clone();
         clone.setReaderTypes((HashMap<String, String>) readerTypes.clone());
         clone.setWriterTypes((HashMap<String, String>) writerTypes.clone());
         return clone;
    }
}
