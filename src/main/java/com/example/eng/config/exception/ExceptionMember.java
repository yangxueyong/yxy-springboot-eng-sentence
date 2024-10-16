package com.example.eng.config.exception;



/**
 * 异常成员
 *
 * @author 29520
 * @date 2021/04/13
 */
public enum ExceptionMember {
    SUCCESS("200", "操作成功"),
    ERROR("500", "业务异常"),
    USER_LEAVE_NO_LIMIT("10000","用户不存在限制数据"),
    USER_LEAVE_OVERRUN("10001","用户请假次数超限"),;
    private String code;
    private String msg;

    ExceptionMember(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static ExceptionMember getValueByCode(int code){
        ExceptionMember[] values = ExceptionMember.values();
        for(ExceptionMember exceptionMember : values){
            if(exceptionMember.getCode().equals(code)){
                return exceptionMember;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "ExceptionMember{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
