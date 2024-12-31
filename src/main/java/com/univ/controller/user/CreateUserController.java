package com.univ.controller.user;

import com.univ.enums.Role;
import com.univ.model.entity.User;
import com.univ.service.UserService;
import com.univ.service.UserServiceImpl;
import com.univ.util.Routes;
import com.univ.util.ViewResolver;
import com.univ.validator.ValidationError;
import com.univ.validator.ValidationResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(Routes.CREATE_USER_ROUTE)
public class CreateUserController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ViewResolver.resolve(req, "users/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = new UserServiceImpl();
        String username = (String) req.getParameter("username");
        String password = (String) req.getParameter("password");
        String passwordConfirmation = (String) req.getParameter("confirm_password");

        User user = new User(username, password, Role.USER);
        try {
            ValidationResult validationResult = userService.createUser(user, passwordConfirmation);
            if (validationResult.isInvalid()) {
                ValidationError validationError = validationResult.getErrors().get(0);
                req.setAttribute(validationError.getErrorField(),
                        validationError.getMessage());
                ViewResolver.resolve(req, "users/create.jsp").forward(req, resp);
            } else {
                String usersURI = req.getContextPath().concat(Routes.USERS_ROUTE);
                resp.sendRedirect(usersURI);
            }
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
