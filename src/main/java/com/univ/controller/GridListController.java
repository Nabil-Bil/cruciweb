package com.univ.controller;

import com.univ.model.Grid;
import com.univ.service.GridService;
import com.univ.service.GridServiceImpl;
import com.univ.util.Routes;
import com.univ.util.ViewResolver;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@WebServlet(Routes.GRID_ROUTE)
public class GridListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String filter = req.getParameter("filter");
            String pageParam = req.getParameter("page");
            int page = 1;
            if (pageParam != null && !pageParam.isEmpty()) {
                try {
                    page = Integer.parseInt(pageParam);
                } catch (NumberFormatException ignored) {
                }
            }
            GridService gridService = new GridServiceImpl();
            int PAGE_SIZE = 15;
            Map<String, Object> objectMap = gridService.getGridList(page, PAGE_SIZE);
            List<Grid> gridList = (List<Grid>) objectMap.get("gridList");
            int numberOfPages = (int) objectMap.get("numberOfPages");

            if ("creationDate".equals(filter)) {
                gridList.sort(Comparator.comparing(Grid::getCreatedAt));
            } else if ("difficultyAsc".equals(filter)) {
                gridList.sort(Comparator.comparing(grid -> grid.getDifficulty().ordinal()));
            } else if ("difficultyDesc".equals(filter)) {
                gridList.sort(Comparator.comparing((Grid grid) -> grid.getDifficulty().ordinal()).reversed());
            }
            req.setAttribute("gridList", gridList);
            req.setAttribute("page", page);
            req.setAttribute("numberOfPages", numberOfPages);
            ViewResolver.resolve(req, "grids/gridList.jsp").forward(req, resp);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }
}
