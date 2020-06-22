package com.fh.shop.api.common;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义的ThreadLocal封装类
 */
public class WebContext {

    private static ThreadLocal<HttpServletRequest> threadLocal = new ThreadLocal<>();

    public static void set(HttpServletRequest request) {
        threadLocal.set(request);
    }

    public static HttpServletRequest get() {
        return threadLocal.get();
    }

    public static void dele() {
        threadLocal.remove();
    }
}
