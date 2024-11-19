package com.univ.util;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

public class ViewResolver {
  private static final String BASE_VIEW_PATH = "/WEB-INF/views/";

  public static RequestDispatcher resolve(HttpServletRequest request, String viewName) {
    return request.getRequestDispatcher(BASE_VIEW_PATH + viewName);
  }
}