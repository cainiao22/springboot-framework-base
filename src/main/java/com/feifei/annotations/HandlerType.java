package com.qding.bigdata.annotations;

import java.lang.annotation.*;

/**
 * @author yanpf
 * @date 2019/6/3 17:31
 * @description
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface HandlerType {

    String value() default "";
}
