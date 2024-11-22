package com.univ.controller;

import java.io.IOException;

import com.univ.enums.Role;
import com.univ.model.User;
import com.univ.service.UserService;
import com.univ.service.UserServiceImpl;
import com.univ.util.CsrfTokenUtil;
import com.univ.util.ViewResolver;
import com.univ.validator.UserValidator;
import com.univ.validator.ValidationError;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("text/html");
    String csrfToken = CsrfTokenUtil.generateCsrfToken(req);
    req.setAttribute(CsrfTokenUtil.CSRF_SESSION_ATTRIBUTE, csrfToken);
    ViewResolver.resolve(req, "auth/register.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String csrfToken = req.getParameter(CsrfTokenUtil.CSRF_SESSION_ATTRIBUTE);

    if (!CsrfTokenUtil.validateCsrfToken(req, csrfToken)) {
      resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid CSRF Token");
      return;
    }
    UserService userService = new UserServiceImpl();
    String username = (String) req.getParameter("username");
    String password = (String) req.getParameter("password");
    String passwordConfirmation = (String) req.getParameter("confirm_password");

    User user = new User(username, password, Role.USER);
    UserValidator userValidator = UserValidator.forUser(user).validateUsername().validatePassword(passwordConfirmation);
    if (!userValidator.isValid()) {
      ValidationError validationError = userValidator.getValidationErrors().get(0);
      req.setAttribute(validationError.getErrorField(),
          validationError.getMessage());
      ViewResolver.resolve(req, "auth/register.jsp").forward(req, resp);
      return;
    }
    try {
      User createdUser = userService.createUser(user);
      resp.getWriter().println(createdUser.toString());
    } catch (Exception e) {
      resp.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
      return;
    }

  }
}
