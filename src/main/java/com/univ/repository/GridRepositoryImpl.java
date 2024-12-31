package com.univ.repository;

import com.univ.model.entity.Grid;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GridRepositoryImpl extends BaseRepository<Grid> implements GridRepository {
    @Override
    public Optional<Grid> findById(UUID id) throws Exception {
        return super.findById(Grid.class, id);
    }

    @Override
    public List<Grid> findAll() throws Exception {
        return super.findAll(Grid.class);
    }

    @Override
    public void deleteById(UUID id) throws Exception {
        super.deleteById(Grid.class, id);
    }

    @Override
    public List<Grid> findByCreatedBy(UUID userId) throws Exception {
        try (EntityManager entityManager = createEntityManager()) {
            return entityManager.createQuery("SELECT g FROM Grid g WHERE g.createdBy.id = :userId", Grid.class)
                    .setParameter("userId", userId)
                    .getResultList();

        } catch (Exception e) {
            throw new Exception("Error while finding grid by user id", e);
        }
    }
}
