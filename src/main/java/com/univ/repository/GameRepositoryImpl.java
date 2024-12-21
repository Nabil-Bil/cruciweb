package com.univ.repository;

import com.univ.model.Game;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GameRepositoryImpl extends BaseRepository<Game> implements GameRepository {
    @Override
    public Game save(Game game) throws Exception {
        return super.save(game);
    }

    @Override
    public List<Game> findAllByUserId(UUID userId) throws Exception {
        try (EntityManager entityManager = createEntityManager()) {
            return entityManager.createQuery("SELECT g FROM Game g WHERE g.playedBy.id = :userId", Game.class)
                    .setParameter("userId", userId)
                    .getResultList();

        } catch (Exception e) {
            throw new Exception("Error while finding game by user id", e);
        }
    }

    @Override
    public void deleteById(UUID id) throws Exception {
        super.deleteById(Game.class, id);
    }

    @Override
    public Optional<Game> findByUserIdAndGridId(UUID userId, UUID gridId) throws Exception {
        try (EntityManager entityManager = createEntityManager()) {
            return Optional.ofNullable(entityManager.createQuery("SELECT g from Game g WHERE g.playedBy.id=:userId AND g.grid.id=:gridId", Game.class)
                    .setParameter("userId", userId)
                    .setParameter("gridId", gridId)
                    .getSingleResultOrNull());
        }
    }

    @Override
    public Optional<Game> findById(UUID id) throws Exception {
        return super.findById(Game.class, id);
    }

}
