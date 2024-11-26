package com.univ.controller;

import java.io.IOException;

import com.univ.model.User;
import com.univ.service.AuthService;
import com.univ.service.AuthServiceImpl;

import com.univ.util.ViewResolver;
import com.univ.validator.AuthValidator;
import com.univ.validator.ValidationError;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.univ.util.Routes;

@WebServlet(Routes.LOGIN_ROUTE)
public class LoginController extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    ViewResolver.resolve(req, "auth/login.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String username = (String) req.getParameter("username");
    String password = (String) req.getParameter("password");

    User user = new User(username, password);

    try {
      AuthService authService = new AuthServiceImpl();
      HttpSession httpSession = req.getSession(true);
      AuthValidator authValidator = authService.login(user, httpSession);
      if (authValidator.isNotValid()) {
        ValidationError validationError = authValidator.getValidationErrors().get(0);
        req.setAttribute(validationError.getErrorField(),
            validationError.getMessage());
        ViewResolver.resolve(req, "auth/login.jsp").forward(req, resp);
      } else {
        resp.sendRedirect("dashboard");
      }
    } catch (Exception e) {
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

    }
  }
}
