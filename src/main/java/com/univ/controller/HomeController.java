package com.univ.controller;

import java.io.IOException;

import com.univ.util.ViewResolver;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.univ.util.Routes;

@WebServlet(Routes.HOME_ROUTE)
public class HomeController extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String requestUri = req.getRequestURI().replaceAll("/$", "");
    String expectedUri = req.getContextPath();
    if (!requestUri.equals(expectedUri)) {
      resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    } else {
      ViewResolver.resolve(req, "home.jsp").forward(req, resp);
    }
  }
}
