package com.univ.service;

import com.univ.model.entity.User;
import com.univ.validator.ValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public interface AuthService {
    public ValidationResult login(User user, HttpServletRequest request) throws Exception;

    public void logout(HttpSession session);

}