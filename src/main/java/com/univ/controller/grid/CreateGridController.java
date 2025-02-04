package com.univ.controller.grid;

import com.univ.enums.GameDifficulty;
import com.univ.model.embeddables.Dimension;
import com.univ.service.GridService;
import com.univ.service.GridServiceImpl;
import com.univ.util.Routes;
import com.univ.util.ViewResolver;
import com.univ.validator.ValidationResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(Routes.CREATE_GRID_ROUTE)
public class CreateGridController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ViewResolver.resolve(req, "grids/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            GridService gridService = new GridServiceImpl();
            String name = req.getParameter("name");
            String stringDifficulty = req.getParameter("difficulty");
            GameDifficulty difficulty = GameDifficulty.valueOf(stringDifficulty.toUpperCase());

            int width = Integer.parseInt(req.getParameter("width"));
            int height = Integer.parseInt(req.getParameter("height"));
            Dimension dimension = new Dimension(width, height);
            String gridJson = req.getParameter("gridMatrixData");

            List<String> rowClues = new ArrayList<String>();
            List<String> columnClues = new ArrayList<String>();
            for (int i = 0; i < height; i++) {
                String rowClue = req.getParameter("clue-row-" + i);
                rowClues.add(rowClue);
            }
            for (int i = 0; i < width; i++) {
                String columnClue = req.getParameter("clue-column-" + i);
                columnClues.add(columnClue);
            }
            ValidationResult gridValidator = gridService.create(name, difficulty, dimension, gridJson, rowClues, columnClues, req.getSession());
            if (gridValidator.isValid()) {
                resp.sendRedirect(req.getContextPath().concat(Routes.GRIDS_ROUTE).concat("?filter=my-grids&sort=creationDate"));
            } else {
                req.setAttribute("error", gridValidator.getErrors().get(0).getMessage());
                ViewResolver.resolve(req, "grids/create.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }
}
