package com.qding.bigdata.aop.dynamic;

import com.qding.bigdata.utils.DataSourceHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yanpf
 * @date 2019/5/30 15:48
 * @description
 */
public class DataSourceContextHolder {

    private DataSourceContextHolder(){

    }

    private static final ThreadLocal<AtomicInteger> indexHolder = ThreadLocal.withInitial(() -> new AtomicInteger(0));

    private static final ThreadLocal<Map<Integer, DataSourceHolder>> contextHolder = ThreadLocal.withInitial(HashMap::new);

    public static void setDB(DataSourceHolder driverConfig) {
        AtomicInteger index = indexHolder.get();
        contextHolder.get().put(index.incrementAndGet(), driverConfig);
    }

    public static DataSourceHolder getDB() {
        AtomicInteger index = indexHolder.get();
        return (contextHolder.get().get(index.get()));
    }

    public static void clearDB() {
        AtomicInteger index = indexHolder.get();
        contextHolder.get().remove(index.getAndDecrement());
    }
}
