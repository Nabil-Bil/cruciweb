package com.univ.service;

import jakarta.servlet.http.HttpSession;

public interface AuthService {
  public void login(String username, String password, HttpSession session) throws Exception;

  public void logout(HttpSession session);

}