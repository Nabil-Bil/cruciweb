package com.univ.service;

import com.univ.model.User;
import com.univ.repository.UserRepository;
import com.univ.repository.UserRepositoryImpl;
import com.univ.util.BCrypt;

import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserServiceImpl implements UserService {

  private UserRepository userRepository;

  public UserServiceImpl() {
    this.userRepository = new UserRepositoryImpl();
  }

  public Optional<User> getUserById(UUID id) throws Exception {
    return userRepository.findById(id);
  }

  public List<User> getAllUsers() throws Exception {
    return userRepository.findAll();
  }

  public User createUser(User user) throws Exception {
    String userPassword = user.getPassword();
    String hashedPassword = BCrypt.hashPassword(userPassword);
    User cloneUser = User.copyOf(user);
    cloneUser.setPassword(hashedPassword);
    return userRepository.save(cloneUser);
  }

  public User updateUser(User user) throws Exception {
    if (user.getId() == null || userRepository.findById(user.getId()).isEmpty()) {
      throw new NotFoundException(User.class.getName());
    }
    return userRepository.save(user);
  }

  public void deleteUser(UUID id) throws Exception {
    userRepository.deleteById(id);
  }
}