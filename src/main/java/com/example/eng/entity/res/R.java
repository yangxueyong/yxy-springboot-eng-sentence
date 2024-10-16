package com.example.eng.entity.res;

import com.example.eng.config.exception.ExceptionMember;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.Optional;

@Data
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema( name = "状态码" )
    private String code;

    @Schema(name = "是否成功")
    private boolean success;

    @Schema(name = "返回消息")
    private String msg;

    @Schema(name="承载数据")
    private T data;

    private R(ExceptionMember resultCode) {
        this(resultCode, null, resultCode.getMsg());
    }

    private R(ExceptionMember resultCode, String msg) {
        this(resultCode, null, msg);
    }

    private R(ExceptionMember resultCode, T data) {
        this(resultCode, data, resultCode.getMsg());
    }

    private R(ExceptionMember resultCode, T data, String msg) {
        this(resultCode.getCode(), data, msg);
    }

    private R(String code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.success = ExceptionMember.SUCCESS.getCode().equals(code);
    }

    public static boolean isSuccess(R<?> result) {
        return (Boolean) Optional.ofNullable(result).map((x) -> {
            return ObjectUtils.nullSafeEquals(ExceptionMember.SUCCESS.getCode(), x.code);
        }).orElse(Boolean.FALSE);
    }

    public static boolean isNotSuccess(R<?> result) {
        return !isSuccess(result);
    }

    public static <T> R<T> data(T data) {
        return data(data, "操作成功");
    }

    public static <T> R<T> data(T data, String msg) {
        return data("200", data, msg);
    }

    public static <T> R<T> data(String code, T data, String msg) {
        return new R(code, data, data == null ? "暂无承载数据" : msg);
    }

    public static <T> R<T> success(String msg) {
        return new R(ExceptionMember.SUCCESS, msg);
    }

    public static <T> R<T> success(ExceptionMember resultCode) {
        return new R(resultCode);
    }

    public static <T> R<T> success(ExceptionMember resultCode, String msg) {
        return new R(resultCode, msg);
    }

    public static <T> R<T> fail(String msg) {
        return new R(ExceptionMember.ERROR, msg);
    }

    public static <T> R<T> fail(Object data) {
        return new R(ExceptionMember.ERROR.getCode(),data, "操作失败");
    }

    public static <T> R<T> fail(String code, String msg) {
        return new R(code, (Object) null, msg);
    }

    public static <T> R<T> fail(ExceptionMember resultCode) {
        return new R(resultCode);
    }

    public static <T> R<T> fail(ExceptionMember resultCode, String msg) {
        return new R(resultCode, msg);
    }

    public static <T> R<T> status(boolean flag) {
        return flag ? success("操作成功") : fail("操作失败");
    }


    @Override
    public String toString() {
        return "R(code=" + this.getCode() + ", success=" + this.isSuccess() + ", data=" + this.getData() + ", msg=" + this.getMsg() + ")";
    }

    public R() {
    }
}
