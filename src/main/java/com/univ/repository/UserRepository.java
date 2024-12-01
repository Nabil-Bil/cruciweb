package com.univ.repository;

import com.univ.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(UUID id);

    List<User> findAll();

    void deleteById(UUID id);

    Optional<User> findByUsername(String username);
}