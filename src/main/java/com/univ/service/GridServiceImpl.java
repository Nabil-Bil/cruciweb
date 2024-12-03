package com.univ.service;

import com.univ.model.Grid;
import com.univ.model.Hint;
import com.univ.repository.GridRepository;
import com.univ.repository.GridRepositoryImpl;
import com.univ.validator.GridValidator;
import jakarta.servlet.http.HttpSession;

import java.util.*;

public class GridServiceImpl implements GridService {
    GridRepository gridRepository;
    HttpSession session;

    public GridServiceImpl() {
        this.gridRepository = new GridRepositoryImpl();
    }


    @Override
    public GridValidator createGrid(Grid grid, List<Hint> hints) throws Exception {
        return null;
    }

    @Override
    public void deleteGrid(UUID id) throws Exception {
        this.gridRepository.deleteById(id);
    }

    @Override
    public Map<String, Object> getGridList(int page, int pageSize) throws Exception {
        List<Grid> allGrids = gridRepository.findAll();
        if (allGrids == null || allGrids.isEmpty()) {
            return Map.of("gridList", new ArrayList<>(), "numberOfPages", 0);
        }
        int totalGrids = allGrids.size();

        int totalPages = (int) Math.ceil((double) totalGrids / pageSize);

        if (page < 1) {
            page = 1;
        } else if (page > totalPages) {
            page = totalPages;
        }

        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalGrids);
        Map<String, Object> map = Map.of("gridList", allGrids, "numberOfPages", totalPages);
        return map;
    }

    @Override
    public Optional<Grid> getGridById(UUID id) throws Exception {
        return null;
    }

}
