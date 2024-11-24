package com.univ.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.univ.service.AuthService;
import com.univ.service.AuthServiceImpl;
import com.univ.util.Routes;

@WebServlet(Routes.LOGOUT_ROUTE)
public class LogoutController extends HttpServlet {
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    AuthService authService = new AuthServiceImpl();
    HttpSession session = req.getSession();
    authService.logout(session);
    String homeURI = req.getContextPath().concat(Routes.HOME_ROUTE);
    resp.sendRedirect(homeURI);
  }
}
