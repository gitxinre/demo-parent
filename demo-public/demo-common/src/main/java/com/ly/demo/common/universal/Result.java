package com.ly.demo.common.universal;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 响应结果信息
 *
 * @author xinre
 */
@Getter
@Setter
public class Result implements Serializable {

    /**
     * 处理成功的编码
     */
    private static final String CODE_SUCCESS = "200";
    /**
     * 处理失败的编码
     */
    private static final String CODE_FAIL = "500";


    /**
     * 编码
     */
    private String code;
    /**
     * 消息
     */
    private String message;
    /**
     * 成功：true / 失败：false
     */
    private boolean success;
    /**
     * 结果数据
     */
    private Object resultData;


    private Result(boolean success) {
        this.success = success;
        if (success) {
            this.code = CODE_SUCCESS;
        } else {
            this.code = CODE_FAIL;
        }
    }

    private Result(boolean success, String code) {
        this.success = success;
        this.code = code;
    }

    public static Result success() {
        return new Result(true);
    }

    public static Result success(Object resultData) {
        Result rr = new Result(true);
        rr.setResultData(resultData);
        return rr;
    }

    /*public static Result success(String message) {
        Result rr = new Result(true);
        rr.setMessage(message);
        return rr;
    }*/

    /*public static Result success(String code, String message) {
        Result rr = new Result(true, code);
        rr.setMessage(message);
        return rr;
    }*/

    public static Result success(Object resultData, String message) {
        Result rr = new Result(true);
        rr.setResultData(resultData);
        rr.setMessage(message);
        return rr;
    }

    public static Result success(Object resultData, String code, String message) {
        Result rr = new Result(true, code);
        rr.setResultData(resultData);
        rr.setMessage(message);
        return rr;
    }

    public static Result fail() {
        return new Result(false);
    }

    public static Result fail(String message) {
        Result rr = new Result(false);
        rr.setMessage(message);
        return rr;
    }

    /*public static Result fail(Object resultData) {
        Result rr = new Result(false);
        rr.setResultData(resultData);
        return rr;
    }*/

    public static Result fail(String code, String message) {
        Result rr = new Result(false, code);
        rr.setMessage(message);
        return rr;
    }

    /*public static Result fail(Object resultData, String message) {
        Result rr = new Result(false);
        rr.setResultData(resultData);
        rr.setMessage(message);
        return rr;
    }*/

    public static Result fail(String code, String message, Object resultData) {
        Result rr = new Result(false, code);
        rr.setResultData(resultData);
        rr.setMessage(message);
        return rr;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> r = new HashMap<String, Object>();
        r.put("code", this.getCode());
        r.put("message", this.getMessage());
        r.put("success", this.isSuccess());
        r.put("resultData", this.getResultData());
        return r;
    }


}
