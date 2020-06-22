package com.fh.shop.api.exception;

import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;

public class ExceptionHandler {
    public ServerResponse handlerGlobalException(GlobalException e) {
        ResponseEnum responseEnum = e.getResponseEnum();

        return ServerResponse.error(responseEnum);
    }

}
