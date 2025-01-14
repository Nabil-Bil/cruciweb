package com.univ.service;

import com.univ.model.entity.Game;
import com.univ.validator.ValidationResult;
import jakarta.servlet.http.HttpSession;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface GameService {
    Game create(Game game) throws Exception;

    void deleteGame(UUID id) throws Exception;

    Map<String, Object> getGamesList(int page, int pageSize, HttpSession session) throws Exception;

    Optional<Game> getGameById(UUID id) throws Exception;

    Optional<Game> getGameByUserIdAndGridId(UUID userId, UUID gridId) throws Exception;

    ValidationResult validateGame(Game game) throws Exception;

    ValidationResult saveAndValidateGame(Game game) throws Exception;
}
