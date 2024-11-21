package com.univ.controller;

import java.io.IOException;

import com.univ.enums.Role;
import com.univ.model.User;
import com.univ.service.UserService;
import com.univ.service.UserServiceImpl;
import com.univ.util.ViewResolver;

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
    ViewResolver.resolve(req, "auth/register.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    UserService userService = new UserServiceImpl();
    String username = (String) req.getParameter("username");
    String password = (String) req.getParameter("password");
    User user = new User(username, password, Role.USER);
    try {
      User createdUser = userService.createUser(user);
      resp.getWriter().println(createdUser.toString());
    } catch (Exception e) {
      ViewResolver.resolve(req, "errors/error.jsp");
    }

  }
}
