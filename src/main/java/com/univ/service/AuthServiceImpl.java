package com.univ.service;

import com.univ.model.User;
import com.univ.repository.UserRepository;
import com.univ.repository.UserRepositoryImpl;
import com.univ.util.SessionManager;
import com.univ.validator.AuthValidator;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl() {
        this.userRepository = new UserRepositoryImpl();
    }

    @Override
    public AuthValidator login(User user, HttpSession session) throws Exception {
        SessionManager sessionManager = new SessionManager(session);
        Optional<User> optionalUser = this.userRepository.findByUsername(user.getUsername());
        AuthValidator validator = AuthValidator.of(user, optionalUser);
        validator.validateUsername().validatePassword();
        if (validator.isValid())
            optionalUser.ifPresent(value -> sessionManager.setLoggedUser(value.getId(), value.getRole()));

        return validator;
    }

    @Override
    public void logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
    }

}