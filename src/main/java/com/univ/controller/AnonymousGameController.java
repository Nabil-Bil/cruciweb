package com.univ.controller;

import com.univ.model.Game;
import com.univ.model.Grid;
import com.univ.service.GridService;
import com.univ.service.GridServiceImpl;
import com.univ.util.Routes;
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
}
