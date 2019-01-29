package com.xiong.exam.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.TYPE})//可以运行在方法和类上
@Retention(RetentionPolicy.RUNTIME)//运行时有效
public @interface Signature {
}
