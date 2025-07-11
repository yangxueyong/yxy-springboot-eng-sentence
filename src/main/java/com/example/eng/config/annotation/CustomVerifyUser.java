package com.example.eng.config.annotation;

import com.example.eng.constant.MyConstant;

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

    // 如果不是什么类型就报错
    String notTypeError() default MyConstant.USER_TYPE_MEMBER;

    // 如果是什么类型就报错
    String yesTypeError() default "";

}
