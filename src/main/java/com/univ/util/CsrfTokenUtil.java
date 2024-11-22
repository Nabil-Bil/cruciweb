package com.univ.util;

import java.util.UUID;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class CsrfTokenUtil {

  public static final String CSRF_SESSION_ATTRIBUTE = "csrfToken";

  public static String generateCsrfToken(HttpServletRequest request) {
    String csrfToken = UUID.randomUUID().toString();

    HttpSession session = request.getSession(true);
    session.setAttribute(CSRF_SESSION_ATTRIBUTE, csrfToken);

    return csrfToken;
  }

  public static String getCsrfTokenFromSession(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session == null) {
      return null;
    }
    return (String) session.getAttribute(CSRF_SESSION_ATTRIBUTE);
  }

  public static boolean validateCsrfToken(HttpServletRequest request, String csrfToken) {
    String storedToken = getCsrfTokenFromSession(request);
    return storedToken != null && storedToken.equals(csrfToken);
  }
}