package com.univ.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerProvider {
  private static final String persistanceUnitName = "default";
  private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(
      persistanceUnitName);

  public static EntityManager getEntityManager() {
    return entityManagerFactory.createEntityManager();
  }

  public static void close() {
    if (entityManagerFactory.isOpen()) {
      entityManagerFactory.close();
    }
  }
}