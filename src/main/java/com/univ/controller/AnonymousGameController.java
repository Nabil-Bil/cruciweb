package com.univ.controller;

import com.univ.model.entity.Game;
import com.univ.model.entity.Grid;
import com.univ.service.GameService;
import com.univ.service.GameServiceImpl;
import com.univ.service.GridService;
import com.univ.service.GridServiceImpl;
import com.univ.util.Routes;
import com.univ.util.Utils;
import com.univ.util.ViewResolver;
import com.univ.validator.ValidationResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@WebServlet(Routes.ANONYMOUS_GAME_ROUTE)
public class AnonymousGameController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UUID gridId = Utils.getUUIDFromUrl(req);
        GridService gridService = new GridServiceImpl();
        try {
            Optional<Grid> grid = gridService.getGridById(gridId);
            if (Utils.validateRequest(gridId, resp, grid.isEmpty()))
                return;
            Game game = new Game(grid.get());
            req.setAttribute("game", game);
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
            GridService gridService = new GridServiceImpl();
            Optional<Grid> grid = gridService.getGridById(uuid);
            if (Utils.validateRequest(uuid, resp, grid.isEmpty())) {
                return;
            }

            Game game = new Game(grid.get());
            game.setGridRepresentation(gridJson);
            GameService gameService = new GameServiceImpl();
            ValidationResult validationResult = gameService.validateGame(game);
            if (validationResult.isValid()) {
                req.setAttribute("success", "Tu as gagné!");
            } else {
                req.setAttribute("error", validationResult.getErrors().get(0).getMessage());
            }
            req.setAttribute("game", game);
            ViewResolver.resolve(req, "games/game.jsp").forward(req, resp);


        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
