package com.univ.service;

import com.univ.model.Game;
import com.univ.repository.GameRepository;
import com.univ.repository.GameRepositoryImpl;
import com.univ.util.SessionManager;
import jakarta.servlet.http.HttpSession;

import java.util.*;

public class GameServiceImpl implements GameService {
    GameRepository gameRepository;

    public GameServiceImpl() {
        this.gameRepository = new GameRepositoryImpl();
    }

    @Override
    public Game create(Game game) throws Exception {
        return this.gameRepository.save(game);
    }

    @Override
    public void deleteGame(UUID id) throws Exception {
        this.gameRepository.deleteById(id);
    }

    @Override
    public Map<String, Object> getGamesList(int page, int pageSize, HttpSession session) throws Exception {
        SessionManager sessionManager = new SessionManager(session);
        UUID userId = (UUID) sessionManager.getLoggedInUserId();
        List<Game> userGames = gameRepository.findAllByUserId(userId);
        return paginateGames(userGames, page, pageSize);
    }

    @Override
    public Optional<Game> getGameById(UUID id) throws Exception {
        return this.gameRepository.findById(id);
    }

    @Override
    public Optional<Game> getGameByUserIdAndGridId(UUID userId, UUID gridId) throws Exception {
        return this.gameRepository.findByUserIdAndGridId(userId, gridId);
    }

    private Map<String, Object> paginateGames(List<Game> games, int page, int pageSize) {
        if (games == null || games.isEmpty()) {
            return Map.of("gameList", new ArrayList<>(), "numberOfPages", 0);
        }

        int totalGames = games.size();
        int totalPages = (int) Math.ceil((double) totalGames / pageSize);

        if (page < 1) {
            page = 1;
        } else if (page > totalPages) {
            page = totalPages;
        }

        int fromIndex = (page - 1) * pageSize;

        int toIndex = Math.min(fromIndex + pageSize, totalGames);
        List<Game> paginatedGames = games.subList(fromIndex, toIndex);

        return Map.of("gameList", paginatedGames, "numberOfPages", totalPages);
    }
}
