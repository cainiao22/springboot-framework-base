package com.qding.bigdata.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.qding.bigdata.aop.dynamic.DynamicDataSource;
import com.qding.bigdata.utils.DataSourceHolder;
import com.qding.bigdata.utils.DataSourceManager;
import com.qding.bigdata.utils.DriverConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author yanpf
 * @date 2019/5/30 15:35
 * @description
 */

@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.driver-config")
    public DriverConfig defaultDriverConfig(){
        return new DriverConfig();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource defaultDataSource(@Qualifier("defaultDriverConfig") DriverConfig driverConfig){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(driverConfig.getUrl());
        druidDataSource.setUsername(driverConfig.getUserName());
        druidDataSource.setPassword(driverConfig.getPassword());
        druidDataSource.setDriverClassName(driverConfig.getDriverClass());
        return druidDataSource;
    }

    @Bean
    public DataSourceHolder defaultDataSourceHolder(@Qualifier("defaultDriverConfig") DriverConfig driverConfig, @Qualifier("defaultDataSource") DataSource dataSource){
        return new DataSourceHolder(driverConfig, dataSource, false);
    }

    @Bean(destroyMethod = "destroy")
    public DataSourceManager dataSourceManager(@Qualifier("defaultDataSourceHolder") DataSourceHolder dataSourceHolder, @Qualifier("defaultDataSource") DataSource dataSourceTemplate){
        List<DataSourceHolder> initList = new LinkedList<>();
        initList.add(dataSourceHolder);
        return new DataSourceManager(10, TimeUnit.SECONDS, initList, dataSourceTemplate);
    }

    @Bean
    public DataSource dynamicDataSource(@Qualifier("defaultDataSource") DataSource dataSource) {
        return new DynamicDataSource(dataSource);
    }


    @Bean
    public PlatformTransactionManager txManager(@Qualifier("dynamicDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}