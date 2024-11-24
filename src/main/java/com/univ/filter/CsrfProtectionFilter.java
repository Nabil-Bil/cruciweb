package com.univ.filter;

import java.io.IOException;

import com.univ.util.CsrfTokenUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CsrfProtectionFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
      HttpServletRequest req = (HttpServletRequest) request;
      HttpServletResponse resp = (HttpServletResponse) response;
      if (req.getMethod().equalsIgnoreCase("GET")) {
        String csrfToken = CsrfTokenUtil.generateCsrfToken(req);
        req.setAttribute(CsrfTokenUtil.CSRF_SESSION_ATTRIBUTE, csrfToken);
      } else {
        String csrfToken = req.getParameter(CsrfTokenUtil.CSRF_SESSION_ATTRIBUTE);

        if (!CsrfTokenUtil.validateCsrfToken(req, csrfToken)) {
          resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid CSRF Token");
          return;
        }
      }
      chain.doFilter(request, response);
    }
  }
}
