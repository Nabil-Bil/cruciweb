package com.univ.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerProvider {
  private static final String persistanceUnitName = "default";
  private EntityManagerFactory entityManagerFactory;

  private EntityManagerProvider() {
    this.entityManagerFactory = Persistence.createEntityManagerFactory(
        persistanceUnitName);
  }

  private class Holder {
    public static EntityManagerProvider INSTANCE = new EntityManagerProvider();
  }

  public static EntityManagerProvider instance() {
    return Holder.INSTANCE;
  }

  public EntityManagerFactory getEntityManagerFactory() {
    return entityManagerFactory;
  }

}