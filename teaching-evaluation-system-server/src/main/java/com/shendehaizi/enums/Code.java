package com.shendehaizi.enums;

public enum Code {
    SUCCESS(200,"处理成功"),
    ERROR(50000,"处理失败");
    private Integer status;
    private String message;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    Code(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
