package com.univ.repository;

import com.univ.model.User;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserRepositoryImpl extends BaseRepository<User> implements UserRepository {


    @Override
    public Optional<User> findById(UUID id) {
        return super.findById(User.class, id);
    }

    @Override
    public List<User> findAll() {
        return super.findAll(User.class);
    }

    @Override
    public void deleteById(UUID id) {
        super.deleteById(User.class, id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try (EntityManager entityManager = createEntityManager()) {
            User user = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getResultList()
                    .stream()
                    .findFirst()
                    .orElse(null);
            return Optional.ofNullable(user);
        }
    }

}
