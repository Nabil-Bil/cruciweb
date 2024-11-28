package com.univ.util;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class CsrfTokenUtil {

  public static final String CSRF_SESSION_ATTRIBUTE = "csrfToken";

  public static String generateCsrfToken(HttpServletRequest request) {
    String csrfToken = UUID.randomUUID().toString();

    HttpSession session = request.getSession(true);
    Set<String> csrfTokens = getCsrfTokensFromSession(session);

    csrfTokens.add(csrfToken);
    session.setAttribute(CSRF_SESSION_ATTRIBUTE, csrfTokens);

    return csrfToken;
  }

  @SuppressWarnings("unchecked")
  private static Set<String> getCsrfTokensFromSession(HttpSession session) {
    Set<String> csrfTokens = (Set<String>) session.getAttribute(CSRF_SESSION_ATTRIBUTE);
    if (csrfTokens == null) {
      csrfTokens = new HashSet<>();
    }
    return csrfTokens;
  }

  public static boolean validateCsrfToken(HttpServletRequest request, String csrfToken) {
    HttpSession session = request.getSession(false);
    if (session == null || csrfToken == null) {
      return false;
    }

    Set<String> csrfTokens = getCsrfTokensFromSession(session);

    if (csrfTokens.contains(csrfToken)) {
      csrfTokens.remove(csrfToken);
      session.setAttribute(CSRF_SESSION_ATTRIBUTE, csrfTokens);
      return true;
    }

    return false;
  }
}