package com.univ.controller.grid;

import com.univ.model.entity.Grid;
import com.univ.service.GridService;
import com.univ.service.GridServiceImpl;
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
import java.util.List;
import java.util.Map;

@WebServlet(Routes.GRIDS_ROUTE)
public class GridListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String sortParam = req.getParameter("sort");
            String filterParam = req.getParameter("filter");
            String pageParam = req.getParameter("page");
            String search = req.getParameter("search");
            int page = 1;
            if (pageParam != null && !pageParam.isEmpty()) {
                try {
                    page = Integer.parseInt(pageParam);
                } catch (NumberFormatException ignored) {
                }
            }
            GridService gridService = new GridServiceImpl();
            int PAGE_SIZE = 15;
            HttpSession session = req.getSession();
            SessionManager sessionManager = new SessionManager(session);
            Map<String, Object> objectMap;
            if (filterParam != null && filterParam.equals("my-grids") && sessionManager.isLoggedIn()) {
                objectMap = gridService.getGridList(page, PAGE_SIZE, sortParam, session);
            } else {
                objectMap = gridService.getGridList(page, PAGE_SIZE, sortParam);
            }
            List<Grid> gridList = (List<Grid>) objectMap.get("gridList");
            int numberOfPages = (int) objectMap.get("numberOfPages");
            req.setAttribute("gridList", gridList);
            req.setAttribute("page", page);
            req.setAttribute("numberOfPages", numberOfPages);
            ViewResolver.resolve(req, "grids/gridList.jsp").forward(req, resp);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

}
