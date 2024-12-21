package com.univ.controller;

import com.univ.model.Game;
import com.univ.service.GameService;
import com.univ.service.GameServiceImpl;
import com.univ.util.Routes;
import com.univ.util.SessionManager;
import com.univ.util.ViewResolver;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(Routes.GAMES_ROUTE)
public class GameListController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String pageParam = req.getParameter("page");
            int page = 1;
            if (pageParam != null && !pageParam.isEmpty()) {
                try {
                    page = Integer.parseInt(pageParam);
                } catch (NumberFormatException ignored) {
                }
            }
            GameService gameService = new GameServiceImpl();
            int PAGE_SIZE = 15;
            HttpSession session = req.getSession();
            SessionManager sessionManager = new SessionManager(session);
            Map<String, Object> objectMap = gameService.getGamesList(page, PAGE_SIZE, session);
            Object gameListObject = objectMap.get("gameList");
            List<Game> gameList = new ArrayList<Game>();
            if (gameListObject instanceof List) {
                if (!((List<Game>) gameListObject).isEmpty()) {
                    gameList = (List<Game>) gameListObject;
                }
            }
            int numberOfPages = (int) objectMap.get("numberOfPages");
            req.setAttribute("gameList", gameList);
            req.setAttribute("page", page);
            req.setAttribute("numberOfPages", numberOfPages);
            ViewResolver.resolve(req, "games/gameList.jsp").forward(req, resp);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
