package com.fh.shop.api.common;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(
        filterName = "webFilter",
        urlPatterns = {"/*"})
public class Filter implements javax.servlet.Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            WebContext.set((HttpServletRequest) request);
            chain.doFilter(request, response);
        } finally {
            WebContext.dele();
        }

    }

    public void destroy() {

    }
}
