package com.univ.repository;

import com.univ.model.User;
import com.univ.util.EntityManagerProvider;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserRepository {

  public Optional<User> findById(UUID id) {
    EntityManager entityManager = EntityManagerProvider.getEntityManager();
    try {
      User user = entityManager.find(User.class, id);
      return Optional.ofNullable(user);
    } finally {
      entityManager.close(); // Ensure resources are released
    }
  }

  public List<User> findAll() {
    EntityManager entityManager = EntityManagerProvider.getEntityManager();
    try {
      return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    } finally {
      entityManager.close();
    }
  }

  public Optional<User> findByEmail(String email) {
    EntityManager entityManager = EntityManagerProvider.getEntityManager();
    try {
      List<User> users = entityManager.createQuery(
          "SELECT u FROM User u WHERE u.email = :email", User.class)
          .setParameter("email", email)
          .getResultList();
      return users.stream().findFirst();
    } finally {
      entityManager.close();
    }
  }

  public User save(User user) {
    EntityManager entityManager = EntityManagerProvider.getEntityManager();
    try {
      entityManager.getTransaction().begin();
      if (user.getId() == null) {
        entityManager.persist(user);
      } else {
        user = entityManager.merge(user);
      }
      entityManager.getTransaction().commit();
      return user;
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
      throw e;
    } finally {
      entityManager.close();
    }
  }

  public void deleteById(UUID id) {
    EntityManager entityManager = EntityManagerProvider.getEntityManager();
    try {
      entityManager.getTransaction().begin();
      User user = entityManager.find(User.class, id);
      if (user != null) {
        entityManager.remove(user);
      }
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
      throw e;
    } finally {
      entityManager.close();
    }
  }
}