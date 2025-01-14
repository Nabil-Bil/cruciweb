package com.univ.controller.user;

import com.univ.model.entity.User;
import com.univ.service.AuthService;
import com.univ.service.AuthServiceImpl;
import com.univ.util.Routes;
import com.univ.util.SessionManager;
import com.univ.util.ViewResolver;
import com.univ.validator.ValidationError;
import com.univ.validator.ValidationResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

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
            ValidationResult validationResult = authService.login(user, req);
            if (validationResult.isInvalid()) {
                ValidationError validationError = validationResult.getErrors().get(0);
                req.setAttribute(validationError.getErrorField(),
                        validationError.getMessage());
                req.setAttribute("username", username);
                ViewResolver.resolve(req, "auth/login.jsp").forward(req, resp);
            } else {
                SessionManager sessionManager = new SessionManager(req.getSession());
                if (sessionManager.isAdmin()) {
                    resp.sendRedirect(req.getContextPath() + Routes.USERS_ROUTE);
                } else {
                    resp.sendRedirect(req.getContextPath() + Routes.GRIDS_ROUTE);
                }
            }
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        }
    }

}
