package com.univ.filter;

import com.univ.util.CsrfTokenUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CsrfProtectionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (request instanceof HttpServletRequest req && response instanceof HttpServletResponse resp) {
            String requestURI = req.getRequestURI();

            if (requestURI.contains("resources")) {
                chain.doFilter(request, response);
                return;
            }
            String method = req.getMethod().toUpperCase();
            if (method.equals("POST") || method.equals("PUT") || method.equals("DELETE") || method.equals("PATCH")) {
                String csrfToken = req.getParameter(CsrfTokenUtil.CSRF_SESSION_ATTRIBUTE);
                if (!CsrfTokenUtil.validateCsrfToken(req, csrfToken)) {
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid CSRF Token");
                    return;
                }
            }

            String csrfToken = CsrfTokenUtil.generateCsrfToken(req);
            req.setAttribute(CsrfTokenUtil.CSRF_SESSION_ATTRIBUTE, csrfToken);

            chain.doFilter(request, response);
        }
    }
}
