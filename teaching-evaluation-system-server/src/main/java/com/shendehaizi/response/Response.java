package com.shendehaizi.response;

import java.io.Serializable;

public class Response<T> implements Serializable {
    private static final long serialVersionUID = -750644833749014618L;
    private boolean success;
    private T result;
    private int code;
    private String error;
    private Long count;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getResult() {
        return result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setResult(T result) {
        this.success = true;
        this.code=200;
        this.result = result;
    }
    public void setError(String error) {
        this.success = false;
        this.error = error;
    }
    public void setError(int code, String error) {
        this.success = false;
        this.code = code;
        this.error = error;
    }


}
