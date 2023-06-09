package com.jee.demo.common;

public enum ResultEnum {
    SUCCESS(200,"成功"),
    ERROR(300,"未知错误");

    private Integer code;
    private String msg;
    ResultEnum(Integer code, String msg) {
        this.code=code;
        this.msg=msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
