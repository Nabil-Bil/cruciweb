package com.univ.service;

import com.univ.model.entity.User;
import com.univ.validator.AuthValidator;
import jakarta.servlet.http.HttpSession;

public interface AuthService {
    public AuthValidator login(User user, HttpSession session) throws Exception;

    public void logout(HttpSession session);

}