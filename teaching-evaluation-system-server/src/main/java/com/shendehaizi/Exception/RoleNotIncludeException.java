package com.shendehaizi.Exception;

public class RoleNotIncludeException  extends  RuntimeException{
    public RoleNotIncludeException() {
        super();
    }

    public RoleNotIncludeException(String message) {
        super(message);
    }

    public RoleNotIncludeException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleNotIncludeException(Throwable cause) {
        super(cause);
    }

    protected RoleNotIncludeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
