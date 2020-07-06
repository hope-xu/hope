package com.utils.logutil;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)//生命周期
@Target(ElementType.METHOD)//用于修饰方法
@Documented
public @interface EagleEye {

    /**
     * 接口描述
     */
    String desc() default "";

}
