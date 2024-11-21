package com.univ.service;

import java.util.Optional;
import java.util.UUID;

import com.univ.model.User;

public interface UserService {
  public Optional<User> getUserById(UUID id) throws Exception;

  public User createUser(User user) throws Exception;

  public User updateUser(User user) throws Exception;

  public void deleteUser(UUID id) throws Exception;

}