package com.univ.controller;

import java.io.IOException;
import java.util.List;

import com.univ.model.User;
import com.univ.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/main")
public class MainController extends HttpServlet {

  @Override
  public void init() throws ServletException {
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    UserService userService = new UserService();
    List<User> users = userService.getAllUsers();
    StringBuilder stringBuilder = new StringBuilder();
    for (User user : users) {
      stringBuilder.append(user.getEmail());
    }
    resp.getWriter().write(stringBuilder.toString());
  }
}