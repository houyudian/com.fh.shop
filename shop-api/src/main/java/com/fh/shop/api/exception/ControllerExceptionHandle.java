package com.fh.shop.api.exception;

import com.fh.shop.api.common.ServerResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice//统一异常处理
public class ControllerExceptionHandle {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ServerResponse handleException(Exception e) {
        return ServerResponse.error();
    }

}
