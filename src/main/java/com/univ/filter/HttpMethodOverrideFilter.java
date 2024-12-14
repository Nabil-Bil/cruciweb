package com.univ.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.IOException;

public class HttpMethodOverrideFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (request instanceof HttpServletRequest httpRequest) {

            if ("POST".equalsIgnoreCase(httpRequest.getMethod()) && httpRequest.getParameter("_method") != null) {
                String methodOverride = httpRequest.getParameter("_method").toUpperCase();
                chain.doFilter(new HttpMethodOverrideRequestWrapper(httpRequest, methodOverride), response);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private static class HttpMethodOverrideRequestWrapper extends HttpServletRequestWrapper {
        private final String method;

        public HttpMethodOverrideRequestWrapper(HttpServletRequest request, String method) {
            super(request);
            this.method = method;
        }

        @Override
        public String getMethod() {
            return this.method;
        }
    }
}