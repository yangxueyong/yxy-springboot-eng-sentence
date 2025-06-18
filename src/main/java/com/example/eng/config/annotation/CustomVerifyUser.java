package com.example.eng.config.annotation;

import java.lang.annotation.*;


/**
 * 锁 注解
 *
 * @author yangxueyong
 * @date 2021/06/01
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomVerifyUser {

    String msg() default "没有其他内容了---";

}
