package com.example.eng.config.exception;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;


/**
 * 自定义异常
 * @author yxy
 * @date 2024/10/16
 */
@Data
public class CustomException extends RuntimeException {
    private ExceptionMember exceptionMember;

    private String code;
    private String exceptionMsg;

    /**
     * 构造方法
     * @param exceptionMember
     */
    public CustomException(ExceptionMember exceptionMember) {
        this(exceptionMember, null);
    }


    /**
     * 构造方法
     * @param exceptionMember
     * @param args 参数
     */
    public CustomException(ExceptionMember exceptionMember, String ... args) {
        this(exceptionMember.getCode(), exceptionMember.getMsg(), args);
        this.exceptionMember = exceptionMember;
    }


    /**
     * 构造方法
     * @param exceptionMsg
     */
    public CustomException(String exceptionMsg) {
        this(exceptionMsg, null);
    }


    /**
     * 构造方法
     * @param exceptionMsg
     * @param args  参数
     */
    public CustomException(String exceptionMsg, String ... args) {
        this("500", exceptionMsg, args);
    }

    private CustomException(String code, String exceptionMsg, String ... args) {
        this.code = code;
        this.exceptionMsg = exceptionMsg;

        if (!StrUtil.isEmptyIfStr(exceptionMsg) && args != null && args.length > 0) {
            this.exceptionMsg = StrUtil.format(this.exceptionMsg, args);
        }
    }

    @Override
    public String toString() {
        return "{\"code\":\"" + code + "\",\"exceptionMsg\":\"" + exceptionMsg + "\"}";
    }

    @Override
    public String getMessage() {
        return toString();
    }
}
