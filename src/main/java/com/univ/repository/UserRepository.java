package com.univ.repository;

import com.univ.model.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    User save(User user) throws Exception;

    Optional<User> findById(UUID id) throws Exception;

    List<User> findAll() throws Exception;

    void deleteById(UUID id) throws Exception;

    Optional<User> findByUsername(String username) throws Exception;
}