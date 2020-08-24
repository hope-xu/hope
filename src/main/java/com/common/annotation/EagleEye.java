package com.common.annotation;

import java.lang.annotation.*;

/**
 * 日志记录注解
 */
@Retention(RetentionPolicy.RUNTIME)//生命周期
@Target(ElementType.METHOD)//用于修饰方法
@Documented
public @interface EagleEye {

    /**
     * 接口描述
     */
    String desc() default "";

}
