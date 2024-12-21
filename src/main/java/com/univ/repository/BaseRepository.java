package com.univ.repository;

import com.univ.util.EntityManagerProvider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class BaseRepository<T> {
    private EntityManager entityManager;

    protected EntityManager createEntityManager() {
        return EntityManagerProvider.instance().getEntityManagerFactory().createEntityManager();
    }

    public T executeInTransaction(TransactionAction<T> action) throws Exception {
        this.entityManager = this.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            T result = action.execute();
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new Exception("Transaction failed", e);
        } finally {
            entityManager.close();
        }
    }

    public T save(T entity) throws Exception {
        return executeInTransaction(() -> {
            entityManager.persist(entity);
            return entity;
        });

    }

    public T update(T entity) throws Exception {
        return executeInTransaction(() -> {
            return entityManager.merge(entity);
        });
    }

    public Optional<T> findById(Class<T> clazz, UUID id) throws Exception {
        try {
            this.entityManager = this.createEntityManager();
            if (id == null)
                return Optional.empty();
            Optional<T> entity = Optional.ofNullable(entityManager.find(clazz, id));
            entityManager.close();
            return entity;
        } catch (Exception e) {
            throw new Exception("Entity not found", e);
        }
    }


    public List<T> findAll(Class<T> clazz) throws Exception {
        try {
            this.entityManager = this.createEntityManager();
            List<T> entities = entityManager.createQuery("SELECT e FROM " + clazz.getSimpleName() + " e", clazz)
                    .getResultList();
            entityManager.close();
            return entities;
        } catch (Exception e) {
            throw new Exception("Entities not found", e);
        }

    }

    public void deleteById(Class<T> clazz, UUID id) throws Exception {
        executeInTransaction(() -> {
            T entity = entityManager.find(clazz, id);
            if (entity != null) {
                entityManager.remove(entity);
            }
            return entity;
        });
    }
}