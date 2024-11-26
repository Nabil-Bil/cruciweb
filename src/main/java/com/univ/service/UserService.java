package com.univ.service;

import java.util.Optional;
import java.util.UUID;

import com.univ.model.User;
import com.univ.validator.UserValidator;

public interface UserService {
  public Optional<User> getUserById(UUID id) throws Exception;

  public UserValidator createUser(User user, String passwordConfirmation) throws Exception;

  public User updateUser(User user) throws Exception;

  public void deleteUser(UUID id) throws Exception;

}