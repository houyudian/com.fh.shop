package com.fh.shop.api.exception;

import com.fh.shop.api.common.ServerResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthExcept extends Exception {

    @ExceptionHandler(Exception.class)
    public ServerResponse authExcet(Exception e){
        return  ServerResponse.success(e.getMessage());
    }
}
