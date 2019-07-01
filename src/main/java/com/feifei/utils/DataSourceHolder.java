package com.qding.bigdata.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yanpf
 * @date 2019/5/30 11:19
 * @description
 */

@Slf4j
public class DataSourceHolder {

    private DriverConfig driverConfig;

    private Method closeMethod;

    private Method initMethod;

    private boolean inited;

    private DataSource dataSource;

    private AtomicInteger count = new AtomicInteger(0);

    private AtomicInteger destroyPeriod = new AtomicInteger(0);

    private volatile boolean autoDestroy;

    public DataSourceHolder(DriverConfig driverConfig, DataSource dataSource){
        this(driverConfig, dataSource, true);
    }

    public DataSourceHolder(DriverConfig driverConfig, DataSource dataSource, boolean autoDestroy){
        this(driverConfig, dataSource, "close", null, autoDestroy);
    }


    public DataSourceHolder(DriverConfig driverConfig, DataSource dataSource, String destoryMethod, String initMethodName, boolean autoDestroy){
        this.driverConfig = driverConfig;
        this.dataSource = dataSource;
        this.autoDestroy = autoDestroy;
        if(!StringUtils.isEmpty(initMethodName)){
            try {
                this.initMethod = dataSource.getClass().getMethod(initMethodName);
            } catch (NoSuchMethodException e) {
                log.error("初始化方法不存在>> {}", e.getMessage(), e);
            }
        }
        if(!StringUtils.isEmpty(destoryMethod)){
            try {
                this.closeMethod = dataSource.getClass().getMethod(destoryMethod);
            } catch (NoSuchMethodException e) {
                log.error("销毁方法不存在>> {}", e.getMessage(), e);
            }
        }

        this.destroyPeriod.set(5);
    }


    //一般不需要初始化方法
    public void init(){
        if(inited && this.initMethod != null && dataSource != null){
            try {
                this.initMethod.invoke(dataSource);
            } catch (IllegalAccessException | InvocationTargetException e) {
                log.error("调用初始化方法失败>> {}", e.getMessage(), e);
            }
        }
        this.inited = true;
    }

    public DriverConfig getDriverConfig() {
        return driverConfig;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int getDestroyPeriod() {
        return destroyPeriod.get();
    }

    public int decrementAndGetDestoryPeriod() {
        return destroyPeriod.decrementAndGet();
    }

    public void resetDestoryPeriod() {
        this.destroyPeriod.set(10);
    }

    public int getReferenceCount(){
        return count.get();
    }

    public int increaseReferenceCount(){
       return this.count.incrementAndGet();
    }

    public int decreaseReferenceCount(){
       return this.count.decrementAndGet();
    }

    public void close(){
        if(closeMethod != null){
            try {
                closeMethod.invoke(dataSource);
            } catch (IllegalAccessException | InvocationTargetException e) {
                log.error("调用销毁方法失败>> {}", e.getMessage(), e);
            }
        }
    }

    public boolean isAutoDestroy() {
        return autoDestroy;
    }
}
