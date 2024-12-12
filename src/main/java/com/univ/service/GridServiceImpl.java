package com.univ.service;

import com.univ.enums.Direction;
import com.univ.enums.GameDifficulty;
import com.univ.model.Clue;
import com.univ.model.Grid;
import com.univ.model.User;
import com.univ.model.embeddables.Dimension;
import com.univ.repository.GridRepository;
import com.univ.repository.GridRepositoryImpl;
import com.univ.util.SessionManager;
import com.univ.validator.GridValidator;
import jakarta.servlet.http.HttpSession;

import java.util.*;
import java.util.stream.IntStream;

public class GridServiceImpl implements GridService {
    GridRepository gridRepository;
    HttpSession session;

    public GridServiceImpl() {
        this.gridRepository = new GridRepositoryImpl();
    }


    @Override
    public GridValidator create(String name, GameDifficulty difficulty, Dimension dimension, String gridJson, List<String> rowClues, List<String> columnClues, HttpSession session) throws Exception {
        SessionManager sessionManager = new SessionManager(session);
        UserService userService = new UserServiceImpl();
        Optional<User> user = userService.getUserById((UUID) sessionManager.getLoggedInUserId());
        Grid grid = new Grid(name, difficulty, dimension, user.get(), gridJson);
        List<Clue> clues = new ArrayList<Clue>();
        IntStream.range(0, rowClues.size()).forEach(i -> {
            Clue clue = new Clue(rowClues.get(i), Direction.HORIZONTAL, i, grid);
            clues.add(clue);
        });
        IntStream.range(0, columnClues.size()).forEach(i -> {
            Clue clue = new Clue(columnClues.get(i), Direction.VERTICAL, i, grid);
            clues.add(clue);
        });
        grid.setClues(clues);
        GridValidator gridValidator = GridValidator.of(grid);

        if (gridValidator.isValid()) {
            Grid savedGrid = gridRepository.save(grid);
        }
        return gridValidator;


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
