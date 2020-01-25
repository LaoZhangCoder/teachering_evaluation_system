package com.shendehaizi.config;

import com.shendehaizi.Exception.ServiceException;
import com.shendehaizi.enums.Code;
import com.shendehaizi.response.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GrobalExceptionHandle {
    @ExceptionHandler(value = ServiceException.class)
    public Response exceptionHandle(HttpServletRequest req, ServiceException e) {
        e.printStackTrace();
        Response<Object> response = new Response<>();
        response.setCode(Code.ERROR.getStatus());
        response.setError(e.getMessage());
        response.setSuccess(false);
        return response;
    }
}
