package com.fh.shop.api.aspect;

import com.fh.shop.api.common.RedisUtil;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.WebContext;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class SecureAspect {
    private static final int EXPIRE_TIME = 30;
    private static final String APP_SECRET = "huahau";

    @Around("@annotation(com.fh.shop.api.annotation.Secure)")
    public Object doSecure(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = WebContext.get();
        String nonce = request.getHeader("nonce");
        String time = request.getHeader("time");
        String sign = request.getHeader("sign");
        if (StringUtils.isEmpty(nonce) || StringUtils.isEmpty(time) || StringUtils.isEmpty(sign)) {
            return ServerResponse.error(ResponseEnum.SECURE_HEADER_IS_MISS);
        }
        /*验签*/
        String newSign = MD5Util.sign1(nonce + time, APP_SECRET);
        if (!sign.equals(newSign)) {
            return ServerResponse.error(ResponseEnum.SECURE_DATA_IS_CHANGE);
        }
        /*过期时间后的重放攻击*/
        long currentTimeMillis = System.currentTimeMillis();
        Long aLong = Long.valueOf(time);
        if (currentTimeMillis - aLong > EXPIRE_TIME * 1000) {
            return ServerResponse.error(ResponseEnum.SECURE_REQUEST_TIMEOUT);
        }
        /*过期时间内的重放攻击*/
        String key = KeyUtil.buildSecureKey(nonce);
//        boolean exist = RedisUtil.exist(key);
//        if (exist) {
//            return ServerResponse.error(ResponseEnum.SECURE_HEADER_IS_MISS);
//        } else {
//            RedisUtil.setEx(key, "", EXPIRE_TIME);
//        }
        String res = RedisUtil.setNxEx(key, "", EXPIRE_TIME);
        if (!"ok".equalsIgnoreCase(res)) {
            return ServerResponse.error(ResponseEnum.SECURE_REQUEST_IS_SEND);
        }

        return pjp.proceed();
    }

}
