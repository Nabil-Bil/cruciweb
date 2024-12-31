package com.univ.repository;

import com.univ.model.entity.User;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserRepositoryImpl extends BaseRepository<User> implements UserRepository {


    @Override
    public Optional<User> findById(UUID id) throws Exception {
        return super.findById(User.class, id);
    }

    @Override
    public List<User> findAll() throws Exception {
        return super.findAll(User.class);
    }

    @Override
    public void deleteById(UUID id) throws Exception {
        super.deleteById(User.class, id);
    }

    @Override
    public Optional<User> findByUsername(String username) throws Exception {
        try (EntityManager entityManager = createEntityManager()) {
            User user = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getResultList()
                    .stream()
                    .findFirst()
                    .orElse(null);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            throw new Exception("Error while finding user by username", e);
        }
    }

}
