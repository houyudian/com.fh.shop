package com.fh.shop.api.interceptor;

import com.fh.shop.api.annotation.ApiIdempotent;
import com.fh.shop.api.common.RedisUtil;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.exception.GlobalException;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class IdempotentInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (!method.isAnnotationPresent(ApiIdempotent.class)) {
            return true;
        }

        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            throw new GlobalException(ResponseEnum.IDEMPOTENT_TOKEN_MISS);

        }
        boolean exist = RedisUtil.exist(token);
        if (!exist) {
            throw new GlobalException(ResponseEnum.IDEMPOTENT_REQUEST_REPET);
        }
        Long delete = RedisUtil.delete(token);
        if (delete == 0) {
            throw new GlobalException(ResponseEnum.IDEMPOTENT_REQUEST_REPET);
        }
        return true;

    }

}
