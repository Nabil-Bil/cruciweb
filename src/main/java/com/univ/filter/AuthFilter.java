package com.univ.filter;

import com.univ.util.Routes;
import com.univ.util.SessionManager;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest req && response instanceof HttpServletResponse resp) {
            HttpSession session = req.getSession(false);
            SessionManager sessionManager = new SessionManager(session);
            String loginURI = req.getContextPath().concat(Routes.LOGIN_ROUTE);
            String registerURI = req.getContextPath().concat(Routes.REGISTER_ROUTE);
            String gridsURI = req.getContextPath().concat(Routes.GRIDS_ROUTE);
            boolean isLoggedIn = sessionManager.isLoggedIn();
            boolean authRequest = req.getRequestURI().equals(loginURI) || req.getRequestURI().equals(registerURI);
            if (isLoggedIn) {
                if (authRequest) {
                    if (sessionManager.isAdmin()) {
                        resp.sendRedirect(req.getContextPath().concat(Routes.USERS_ROUTE));
                        return;
                    }
                    resp.sendRedirect(gridsURI);
                } else {
                    chain.doFilter(request, response);
                }
            } else {
                if (!authRequest) {
                    resp.sendRedirect(loginURI);
                } else {
                    chain.doFilter(request, response);
                }
            }
        }
    }

}
