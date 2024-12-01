package com.univ.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerProvider {
    private static final String persistenceUnitName = "default";
    private final EntityManagerFactory entityManagerFactory;

    private EntityManagerProvider() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory(
                persistenceUnitName);
    }

    public static EntityManagerProvider instance() {
        return Holder.INSTANCE;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    private static class Holder {
        public static EntityManagerProvider INSTANCE = new EntityManagerProvider();
    }

}