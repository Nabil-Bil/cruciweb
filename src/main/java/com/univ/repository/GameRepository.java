package com.univ.repository;

import com.univ.model.Game;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GameRepository {
    Game save(Game game) throws Exception;

    Optional<Game> findById(UUID id) throws Exception;

    List<Game> findAllByUserId(UUID userId) throws Exception;

    void deleteById(UUID id) throws Exception;

    Optional<Game> findByUserIdAndGridId(UUID userId, UUID gridId) throws Exception;
}
