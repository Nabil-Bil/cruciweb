package com.univ.controller;

import com.univ.model.Game;
import com.univ.model.Grid;
import com.univ.model.User;
import com.univ.service.*;
import com.univ.util.Routes;
import com.univ.util.SessionManager;
import com.univ.util.Utils;
import com.univ.util.ViewResolver;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@WebServlet(Routes.GRID_ROUTE)
public class GridController extends HttpServlet {
    private boolean validateRequest(UUID gridId, SessionManager sessionManager, Optional<Grid> grid, HttpServletResponse resp) throws Exception {
        if (Utils.validateRequest(gridId, resp, grid.isEmpty())) return true;
        if (!sessionManager.isAdmin() && !grid.get().getCreatedBy().getId().equals(sessionManager.getLoggedInUserId())) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return true;
        }
        return false;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GridService gridService = new GridServiceImpl();

        try {
            UUID gridId = Utils.getUUIDFromUrl(req);
            Optional<Grid> grid = gridService.getGridById(gridId);

            if (Utils.validateRequest(gridId, resp, grid.isEmpty())) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            } else {
                req.setAttribute("grid", grid.get());
                ViewResolver.resolve(req, "grids/grid.jsp").forward(req, resp);
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);

        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SessionManager sessionManager = new SessionManager(req.getSession());
        if (!sessionManager.isLoggedIn()) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        GridService gridService = new GridServiceImpl();
        UUID gridId = Utils.getUUIDFromUrl(req);
        try {
            Optional<Grid> grid = gridService.getGridById(gridId);
            if (Utils.validateRequest(gridId, resp, grid.isEmpty())) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            GameService gameService = new GameServiceImpl();
            UserService userService = new UserServiceImpl();
            Optional<User> user = userService.getUserById((UUID) sessionManager.getLoggedInUserId());
            if (user.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            Optional<Game> optionalGame = gameService.getGameByUserIdAndGridId(user.get().getId(), grid.get().getId());
            Game game;
            if (optionalGame.isEmpty()) {
                game = new Game(user.get(), grid.get());
                game = gameService.create(game);
            } else {
                game = optionalGame.get();
            }
            resp.sendRedirect(req.getContextPath().concat("/game/".concat(game.getId().toString())));
            
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GridService gridService = new GridServiceImpl();
        try {
            UUID gridId = Utils.getUUIDFromUrl(req);
            Optional<Grid> grid = gridService.getGridById(gridId);
            if (validateRequest(gridId, new SessionManager(req.getSession()), grid, resp)) {
                return;
            }
            gridService.deleteGrid(gridId);
            resp.sendRedirect(req.getContextPath() + Routes.GRIDS_ROUTE);
        } catch (IllegalArgumentException illegalArgumentException) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);

        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
