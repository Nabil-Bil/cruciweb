package com.univ.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.univ.model.User;
import com.univ.util.EntityManagerProvider;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;

public class UserRepositoryImpl implements UserRepository {
  private EntityManagerFactory entityManagerFactory;

  public UserRepositoryImpl() {
    this.entityManagerFactory = EntityManagerProvider.instance().getEntityManagerFactory();
  }

  @Override
  public User save(User user) throws Exception {
    EntityManager entityManager = this.entityManagerFactory.createEntityManager();
    try {
      EntityTransaction entityTransaction = entityManager.getTransaction();
      entityTransaction.begin();
      if (user.getId() == null) {
        entityManager.persist(user);
      } else {
        user = entityManager.merge(user);
      }
      entityTransaction.commit();
      return user;
    } catch (Exception e) {
      if (entityManager.getTransaction().isActive())
        entityManager.getTransaction().rollback();
      throw e;
    } finally {
      entityManager.close();
    }
  }

  @Override
  public void deleteById(UUID id) throws Exception {
    EntityManager eManager = this.entityManagerFactory.createEntityManager();
    try {
      eManager.getTransaction().begin();
      User user = eManager.find(User.class, id);
      if (user != null) {
        eManager.remove(eManager);
        eManager.getTransaction().commit();
        return;
      }
      throw new EntityNotFoundException("Utilisateur non trouvé");

    } catch (Exception e) {
      if (eManager.getTransaction().isActive())
        eManager.getTransaction().rollback();
    } finally {
      eManager.close();
    }
  }

  @Override
  public Optional<User> findById(UUID id) throws Exception {
    EntityManager eManager = this.entityManagerFactory.createEntityManager();
    try {
      User user = eManager.find(User.class, id);
      return Optional.ofNullable(user);
    } catch (Exception e) {
      throw e;
    } finally {
      eManager.close();
    }

  }

  @Override
  public Optional<User> findByUsername(String username) throws Exception {
    EntityManager eManager = this.entityManagerFactory.createEntityManager();
    try {
      User user = eManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
          .setParameter("username", username)
          .getResultList()
          .stream()
          .findFirst()
          .orElse(null);
      return Optional.ofNullable(user);
    } catch (Exception e) {
      throw e;
    } finally {
      eManager.close();
    }
  }

  @Override
  public List<User> findAll() throws Exception {
    EntityManager eManager = this.entityManagerFactory.createEntityManager();
    try {
      List<User> users = eManager.createQuery("SELECT u FROM User u", User.class).getResultList();
      return users;
    } catch (Exception e) {
      throw e;
    } finally {
      eManager.close();
    }
  }

}
