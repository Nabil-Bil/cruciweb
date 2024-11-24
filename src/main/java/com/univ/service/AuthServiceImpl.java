package com.univ.service;

import com.univ.model.User;
import com.univ.repository.UserRepository;
import com.univ.repository.UserRepositoryImpl;
import com.univ.util.BCryptUtil;
import com.univ.util.SessionManager;

import jakarta.servlet.http.HttpSession;

public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;

  public AuthServiceImpl() {
    this.userRepository = new UserRepositoryImpl();
  }

  @Override
  public void login(String username, String password, HttpSession session) throws Exception {
    SessionManager sessionManager = new SessionManager(session);
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("User not found"));
    Boolean isValidPassword = BCryptUtil.verifyPassword(password, user.getPassword());
    if (!isValidPassword) {
      throw new RuntimeException("Invalid password");
    }
    sessionManager.setLoggedinUser(user.getId(), user.getRole());
  }

  @Override
  public void logout(HttpSession session) {
    if (session != null) {
      session.invalidate();
    }
  }

}