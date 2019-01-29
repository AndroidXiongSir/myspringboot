package com.xiong.exam.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Classname LoginAuth
 * @Author xiong
 * @Date 2019/1/25 上午11:09
 * @Description TODO
 * @Version 1.0
 **/
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginAuth {
    boolean required() default true;
}
