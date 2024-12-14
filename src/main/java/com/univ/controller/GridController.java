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
import java.util.Optional;
import java.util.UUID;

@WebServlet(Routes.GRID_ROUTE)
public class GridController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GridService gridService = new GridServiceImpl();
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && pathInfo.startsWith("/")) {
            pathInfo = pathInfo.substring(1);
        }

        try {
            if (pathInfo == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            UUID gridId = UUID.fromString(pathInfo);
            System.out.println(gridId);
            Optional<Grid> grid = gridService.getGridById(gridId);
            System.out.println(grid.toString());

            if (grid.isEmpty()) {
                System.out.println("grid is empty");
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);

            } else {
                System.out.println("grid is empty");
                req.setAttribute("grid", grid.get());
                ViewResolver.resolve(req, "grids/grid.jsp").forward(req, resp);
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);

        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
