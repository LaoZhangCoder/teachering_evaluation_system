package com.shendehaizi.response;

import java.io.Serializable;

public class Response<T> implements Serializable {
    private static final long serialVersionUID = -750644833749014618L;
    private boolean success;
    private T result;
    private String code;
    private String error;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setResult(T result) {
        this.success = true;
        this.result = result;
    }
    public void setError(String error) {
        this.success = false;
        this.error = error;
    }
    public void setError(String code, String error) {
        this.success = false;
        this.code = code;
        this.error = error;
    }


}
