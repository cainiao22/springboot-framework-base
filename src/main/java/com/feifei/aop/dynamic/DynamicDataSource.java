package com.qding.bigdata.aop.dynamic;

import org.springframework.jdbc.datasource.AbstractDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author yanpf
 * @date 2019/5/30 15:36
 * @description
 */
public class DynamicDataSource extends AbstractDataSource implements DataSource {

    private final DataSource defaultDataSource;

    public DynamicDataSource(DataSource defaultDataSource){
        this.defaultDataSource = defaultDataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if(DataSourceContextHolder.getDB() == null){
            return defaultDataSource.getConnection();
        }
        return DataSourceContextHolder.getDB().getDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        if(DataSourceContextHolder.getDB() == null){
            return defaultDataSource.getConnection(username, password);
        }
        return DataSourceContextHolder.getDB().getDataSource().getConnection(username, password);
    }
}
