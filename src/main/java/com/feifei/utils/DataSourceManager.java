package com.qding.bigdata.utils;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author yanpf
 * @date 2019/5/29 16:42
 * @description
 */

@Slf4j
public class DataSourceManager {

    private Timer timer = new Timer();

    private DataSource dataSourceTemplate;

    private int period;

    private TimeUnit periodUnit;

    private Map<DriverConfig, DataSourceHolder> dataSourceMap = new ConcurrentHashMap<>();

    public DataSourceManager(int period, TimeUnit periodUnit, List<DataSourceHolder> initList, DataSource dataSourceTemplate){
        this.period = period;
        this.periodUnit = periodUnit;
        if(initList != null){
            for (DataSourceHolder dataSourceHolder : initList) {
                dataSourceMap.put(dataSourceHolder.getDriverConfig(), dataSourceHolder);
            }
        }
        this.dataSourceTemplate = dataSourceTemplate;
        this.schedule();
    }

    public DataSourceHolder getDataSource(DriverConfig driverConfig){
        while (true) {
            DataSourceHolder dataSourceHolder = dataSourceMap.get(driverConfig);
            if (dataSourceHolder == null || dataSourceHolder.getDestroyPeriod() == 0) {
                dataSourceHolder = createNewDataSourceHolder(driverConfig);
                DataSourceHolder oldDataSourceHolder = dataSourceMap.putIfAbsent(driverConfig, dataSourceHolder);
                if (oldDataSourceHolder != null) {
                    dataSourceHolder.close();
                    dataSourceHolder = oldDataSourceHolder;
                }
            }
            synchronized (dataSourceHolder) {
                if (dataSourceHolder.getDestroyPeriod() != 0) {
                    dataSourceHolder.increaseReferenceCount();
                    dataSourceHolder.resetDestoryPeriod();
                    return dataSourceHolder;
                }
            }
        }
    }

    private DataSourceHolder createNewDataSourceHolder(DriverConfig driverConfig) {
        DruidDataSource dataSource = new DruidDataSource();
        BeanUtils.copyProperties(dataSourceTemplate, dataSource);
        dataSource.setDriverClassName(driverConfig.getDriverClass());
        dataSource.setUsername(driverConfig.getUserName());
        dataSource.setPassword(driverConfig.getPassword());
        dataSource.setUrl(driverConfig.getUrl());
        return new DataSourceHolder(driverConfig, dataSource);
    }


    public void returnDataSource(DriverConfig driverConfig, DataSourceHolder dataSourceHolder){
        DataSourceHolder oldDataSourceHolder = this.dataSourceMap.get(driverConfig);
        if(oldDataSourceHolder != dataSourceHolder){
            dataSourceHolder.close();
            return;
        }
        dataSourceHolder.decreaseReferenceCount();
    }


    private void schedule(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                log.info("定时销毁任务开始执行");
                for(Map.Entry<DriverConfig, DataSourceHolder> entry : dataSourceMap.entrySet()){
                    if(!entry.getValue().isAutoDestroy()){
                        continue;
                    }
                    if(entry.getValue().getReferenceCount() == 0){
                       synchronized (entry.getValue()) {
                           if (entry.getValue().decrementAndGetDestoryPeriod() == 0) {
                               dataSourceMap.remove(entry.getKey());
                               entry.getValue().close();
                               log.info("任务[{}]被销毁", entry.getKey());
                           }
                       }
                    }
                }
            }
        }, periodUnit.toMillis(period), periodUnit.toMillis(period));
    }

    public void destroy(){
        log.info("应用关闭 destroy方法开始执行");
        timer.cancel();
        for (Map.Entry<DriverConfig, DataSourceHolder> entry : dataSourceMap.entrySet()) {
            entry.getValue().close();
        }
    }

}
