package com.univ.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ContentTypeFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (request instanceof HttpServletRequest httpRequest && response instanceof HttpServletResponse httpResponse) {

            String requestURI = httpRequest.getRequestURI();

            if (requestURI.contains("resources")) {
                chain.doFilter(request, response);
                return;
            }

            if ("GET".equalsIgnoreCase(httpRequest.getMethod())) {
                httpResponse.setContentType("text/html; charset=UTF-8");
            }
            if (requestURI.endsWith("/") && requestURI.length() > 1) {
                requestURI = requestURI.substring(0, requestURI.length() - 1);
                httpResponse.sendRedirect(requestURI);
                return;
            }
        }

        chain.doFilter(request, response);
    }

}