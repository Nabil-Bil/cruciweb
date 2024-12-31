package com.univ.controller;

import com.univ.model.entity.Game;
import com.univ.service.GameService;
import com.univ.service.GameServiceImpl;
import com.univ.util.Routes;
import com.univ.util.SessionManager;
import com.univ.util.Utils;
import com.univ.util.ViewResolver;
import com.univ.validator.GameValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@WebServlet(Routes.GAME_ROUTE)
public class GameController extends HttpServlet {


    private boolean validateRequest(UUID gameId, SessionManager sessionManager, Optional<Game> game, HttpServletResponse resp) throws Exception {
        if (Utils.validateRequest(gameId, resp, game.isEmpty())) return true;
        if (!game.get().getPlayedBy().getId().equals(sessionManager.getLoggedInUserId())) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return true;
        }
        return false;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionManager sessionManager = new SessionManager(req.getSession());
        GameService gameService = new GameServiceImpl();
        try {
            UUID gameId = Utils.getUUIDFromUrl(req);
            Optional<Game> game = gameService.getGameById(gameId);
            if (validateRequest(gameId, sessionManager, game, resp)) {
                return;
            }
            req.setAttribute("game", game.get());
            if (game.get().isSolved()) {
                req.setAttribute("success", "Tu as gagné!");
            }
            ViewResolver.resolve(req, "games/game.jsp").forward(req, resp);

        } catch (IllegalArgumentException illegalArgumentException) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);

        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String gridJson = req.getParameter("gridMatrixData");
            UUID uuid = Utils.getUUIDFromUrl(req);
            GameService gameService = new GameServiceImpl();
            Optional<Game> optionalGame = gameService.getGameById(uuid);
            if (Utils.validateRequest(uuid, resp, optionalGame.isEmpty())) {
                return;
            }
            Game game = optionalGame.get();
            game.setGridRepresentation(gridJson);
            GameValidator gameValidator = gameService.saveAndValidateGame(game);
            if (gameValidator.isValid()) {
                req.setAttribute("success", "Tu as gagné!");
            } else {
                req.setAttribute("error", gameValidator.getValidationErrors().get(0).getMessage());
            }
            req.setAttribute("game", game);
            ViewResolver.resolve(req, "games/game.jsp").forward(req, resp);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GameService gameService = new GameServiceImpl();
        try {
            UUID gameId = Utils.getUUIDFromUrl(req);
            Optional<Game> game = gameService.getGameById(gameId);
            if (validateRequest(gameId, new SessionManager(req.getSession()), game, resp)) {
                return;
            }
            gameService.deleteGame(gameId);
            resp.sendRedirect(req.getContextPath() + Routes.GAMES_ROUTE);
        } catch (IllegalArgumentException illegalArgumentException) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);

        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
