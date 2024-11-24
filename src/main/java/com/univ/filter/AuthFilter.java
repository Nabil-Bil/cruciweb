package com.univ.filter;

import java.io.IOException;

import com.univ.util.Routes;
import com.univ.util.SessionManager;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AuthFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
      HttpServletRequest req = (HttpServletRequest) request;
      HttpServletResponse resp = (HttpServletResponse) response;
      HttpSession session = req.getSession(false);
      SessionManager sessionManager = new SessionManager(session);
      String loginURI = req.getContextPath().concat(Routes.LOGIN_ROUTE);
      String registerURI = req.getContextPath().concat(Routes.REGISTER_ROUTE);
      String dashboardURI = req.getContextPath().concat(Routes.DASHBOARD_ROUTE);
      boolean isLoggedIn = sessionManager.isLoggedIn();
      boolean authRequest = req.getRequestURI().equals(loginURI) || req.getRequestURI().equals(registerURI);
      if (isLoggedIn) {
        if (authRequest) {
          resp.sendRedirect(dashboardURI);
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
