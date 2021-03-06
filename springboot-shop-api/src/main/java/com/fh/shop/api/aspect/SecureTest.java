package com.fh.shop.api.aspect;

import com.fh.shop.api.common.RedisUtil;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.WebContext;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class SecureTest {

    private static final int EXPIRE_TIME = 3;
    private static final String APP_SECURE = "HIUAH";

    public Object doSevure(ProceedingJoinPoint pjp) throws Throwable {

        HttpServletRequest request = WebContext.get();

        String nonce = request.getHeader("nonce");
        String time = request.getHeader("time");
        String sign = request.getHeader("sign");
        if (StringUtils.isEmpty(nonce) || StringUtils.isEmpty(time) || StringUtils.isEmpty(sign)) {
            return ServerResponse.error(ResponseEnum.SECURE_HEADER_IS_MISS);

        }

        String s = MD5Util.sign1(nonce + time, APP_SECURE);
        if (!sign.equalsIgnoreCase(s)) {
            return ServerResponse.error(ResponseEnum.SECURE_DATA_IS_CHANGE);
        }

        long currentTimeMillis = System.currentTimeMillis();
        Long aLong = Long.valueOf(time);

        if (currentTimeMillis - aLong > EXPIRE_TIME * 1000) {
            return ServerResponse.error(ResponseEnum.SECURE_REQUEST_TIMEOUT);

        }
        String key = KeyUtil.buildSecureKey(nonce);

        String ss = RedisUtil.setNxEx(key, "", EXPIRE_TIME);
        if (!"ok".equalsIgnoreCase(ss)) {
            return ServerResponse.error(ResponseEnum.SECURE_REQUEST_IS_SEND);
        }
        return pjp.proceed();
    }

}
