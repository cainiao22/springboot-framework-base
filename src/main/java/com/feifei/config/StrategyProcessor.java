package com.qding.bigdata.config;

import com.qding.bigdata.annotations.HandlerType;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author yanpf
 * @date 2019/6/3 17:34
 * @description
 */

@Slf4j
@Component
public class StrategyProcessor implements ApplicationContextAware {

    private static Map<Class, Map<String, Object>> cache = new HashMap<>();


    public static <T> T getInstance(Class<T> clazz, String type) {
        return cache.get(clazz) == null ? null : (T) cache.get(clazz).get(type);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        Map<String, Object> map = applicationContext.getBeansWithAnnotation(HandlerType.class);
        map.forEach((k, v) -> {
            HashSet<Class<?>> interfaces = getInterfaces(v.getClass());
            HandlerType handlerType = v.getClass().getAnnotation(HandlerType.class);
            if (CollectionUtils.isEmpty(interfaces)) {
                throw new ApplicationContextException("策略类型必须至少实现一个接口:obj->" + k);
            }
            for (Class<?> clazz : interfaces) {
                addComponent(clazz, v, handlerType.value());
            }

        });

    }

    HashSet<Class<?>> getInterfaces(Class<?> clazz){
        HashSet<Class<?>> set = new HashSet<>();
        Class<?>[] interfaces = clazz.getInterfaces();
        if(interfaces != null){
            set.addAll(Arrays.asList(interfaces));
        }
        Class<?> parent = clazz.getSuperclass();
        if(parent != null) {
            set.addAll(getInterfaces(parent));
        }
        return set;
    }

    private void addComponent(Class<?> clazz, Object obj, String type) {
        Map<String, Object> objectMap = cache.computeIfAbsent(clazz, (k -> new HashMap<>()));
        if (objectMap.get(type) != null) {
            throw new ApplicationContextException("当前类型的处理策略存在多个:class->"
                    + clazz.getName() + ", type->" + type + ", obj->{" + objectMap.get(type) + "," + obj + "}");
        }

        objectMap.put(type, obj);
    }
}
