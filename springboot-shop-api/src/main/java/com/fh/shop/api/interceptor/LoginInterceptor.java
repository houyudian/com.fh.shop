package com.fh.shop.api.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.common.RedisUtil;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.SystemConstant;
import com.fh.shop.api.exception.GlobalException;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.util.Check;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Base64;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       // String origin = request.getHeader("Origin");
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "x-auth,content-type,nonce,time,sign,token");
        /*获取方法*/
        String method1 = request.getMethod();
        if ("options".equalsIgnoreCase(method1)) {
            return false;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (!method.isAnnotationPresent(Check.class)) {
            return true;
        }
        String header = request.getHeader("x-auth");
        if (StringUtils.isEmpty(header)) {
            throw new GlobalException(ResponseEnum.HANDLER_IS_MISS);
        }

        String[] split = header.split("\\.");
        if (split.length != 2) {
            throw new GlobalException(ResponseEnum.HANDLER_CONTENT_IS_MISS);
        }
        String memberJsonBase64 = split[0];
        String signBase64 = split[1];
        String sign = MD5Util.sign(memberJsonBase64, SystemConstant.APPSECRET);
        String s = Base64.getEncoder().encodeToString(sign.getBytes());
        if (!signBase64.equals(s)) {
            throw new GlobalException(ResponseEnum.DATA_IS_CHANGE);
        }

        //获取会员信息
        String s1 = new String(Base64.getDecoder().decode(memberJsonBase64), "utf-8");
        MemberVo vo = JSONObject.parseObject(s1, MemberVo.class);
        String uuid = vo.getUuid();
        Long id = vo.getId();
        //验证是否过期
        boolean exist = RedisUtil.exist(KeyUtil.buildMemberKey(id, uuid));
        if (!exist) {
            throw new GlobalException(ResponseEnum.LOGIN_IS_TIMEOUT);
        }
        RedisUtil.expire(KeyUtil.buildMemberKey(id, uuid), KeyUtil.MEMBER_EXPIRE);
        request.setAttribute(SystemConstant.CURRENT_MEMBER, vo);
        return true;
    }

}
