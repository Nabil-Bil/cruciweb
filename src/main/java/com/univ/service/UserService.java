package com.univ.service;

import com.univ.model.User;
import com.univ.repository.UserRepository;
import com.univ.util.BCrypt;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UserService {

  private UserRepository userRepository;

  public UserService() {
    this.userRepository = new UserRepository();
  }

  public Optional<User> getUserById(UUID id) {
    return userRepository.findById(id);
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public Optional<User> getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Transactional
  public User createUser(User user) {
    String userPassword = user.getPassword();
    String hashedPassword = BCrypt.hashPassword(userPassword);
    User cloneUser = User.clone(user);
    cloneUser.setPassword(hashedPassword);
    return userRepository.save(cloneUser);
  }

  @Transactional
  public User updateUser(User user) {
    if (user.getId() == null || !userRepository.findById(user.getId()).isPresent()) {
      throw new IllegalArgumentException("User not found");
    }
    return userRepository.save(user);
  }

  @Transactional
  public void deleteUser(UUID id) {
    userRepository.deleteById(id);
  }
}