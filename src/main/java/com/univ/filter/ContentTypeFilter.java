package com.univ.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ContentTypeFilter implements Filter {

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
      HttpServletRequest httpRequest = (HttpServletRequest) request;
      HttpServletResponse httpResponse = (HttpServletResponse) response;

      String requestURI = httpRequest.getRequestURI();

      if (requestURI.contains("resources")) {
        chain.doFilter(request, response);
        return;
      }

      if ("GET".equalsIgnoreCase(httpRequest.getMethod())) {
        httpResponse.setContentType("text/html; charset=UTF-8");
      }
    }

    chain.doFilter(request, response);
  }

}