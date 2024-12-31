package com.univ.service;

import com.univ.model.entity.User;
import com.univ.validator.UserValidator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Optional<User> getUserById(UUID id) throws Exception;

    UserValidator createUser(User user, String passwordConfirmation) throws Exception;

    User updateUser(User user) throws Exception;

    List<User> getUserList() throws Exception;

    void deleteUser(UUID id) throws Exception;

}