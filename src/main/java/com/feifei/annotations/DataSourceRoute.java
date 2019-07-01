package com.qding.bigdata.annotations;

import java.lang.annotation.*;

/**
 * @author yanpf
 * @date 2019/5/31 17:36
 * @description
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSourceRoute {

    String value() default "";
}
