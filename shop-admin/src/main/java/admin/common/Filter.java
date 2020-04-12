package admin.common;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class Filter implements javax.servlet.Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
try {
    WebContext.set((HttpServletRequest) request);
    chain.doFilter(request, response);
}finally {
    WebContext.dele();
}


    }

    @Override
    public void destroy() {

    }
}
