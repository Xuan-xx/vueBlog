package com.xuan.vueblog.common.lang;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {

    private int code;
    private String msg;
    private Object data;

    public static Result success(Object data) {
        return success(STATE.SUCCESS, "success", data);
    }

    public static Result success(int code, String msg, Object data) {

        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);

        return result;
    }

    public static Result fail(String msg) {
        return fail(STATE.FAIL, msg, null);
    }


    public static Result fail(int code, String msg) {
        return fail(code, msg, null);
    }

    public static Result fail(int code, String msg, Object data) {

        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);

        return result;
    }
}
