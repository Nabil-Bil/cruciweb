package com.univ.service;

import com.univ.enums.Direction;
import com.univ.enums.GameDifficulty;
import com.univ.model.embeddables.Dimension;
import com.univ.model.entity.Clue;
import com.univ.model.entity.Grid;
import com.univ.model.entity.User;
import com.univ.repository.GridRepository;
import com.univ.repository.GridRepositoryImpl;
import com.univ.util.SessionManager;
import com.univ.validator.GridValidator;
import com.univ.validator.ValidationResult;
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
    public ValidationResult create(String name, GameDifficulty difficulty, Dimension dimension, String gridJson, List<String> rowClues, List<String> columnClues, HttpSession session) throws Exception {
        SessionManager sessionManager = new SessionManager(session);
        UserService userService = new UserServiceImpl();
        Optional<User> user = userService.getUserById((UUID) sessionManager.getLoggedInUserId());
        Grid grid = new Grid(name.trim(), difficulty, dimension, user.get(), gridJson.trim().toUpperCase());
        List<Clue> clues = new ArrayList<Clue>();
        IntStream.range(0, rowClues.size()).forEach(i -> {
            Clue clue = new Clue(rowClues.get(i).trim(), Direction.HORIZONTAL, i, grid);
            clues.add(clue);
        });
        IntStream.range(0, columnClues.size()).forEach(i -> {
            Clue clue = new Clue(columnClues.get(i).trim(), Direction.VERTICAL, i, grid);
            clues.add(clue);
        });
        grid.setClues(clues);
        GridValidator gridValidator = GridValidator.of(grid);

        ValidationResult validationResult = gridValidator.validateGridData().validateDimensions().validateBlankCells().validateAlphabet().validateClues().validateClueAlignment().build();

        if (validationResult.isValid()) {
            gridRepository.save(grid);
        }
        return validationResult;

    }

    @Override
    public void deleteGrid(UUID id) throws Exception {
        this.gridRepository.deleteById(id);
    }

    public Map<String, Object> getGridList(int page, int pageSize, String sortParam) throws Exception {
        List<Grid> allGrids = gridRepository.findAll();
        return paginateGrids(allGrids, page, pageSize, sortParam);
    }

    public Map<String, Object> getGridList(int page, int pageSize, String sortParam, HttpSession session) throws Exception {
        SessionManager sessionManager = new SessionManager(session);
        UUID userId = (UUID) sessionManager.getLoggedInUserId();
        List<Grid> userGrids = gridRepository.findByCreatedBy(userId);
        return paginateGrids(userGrids, page, pageSize, sortParam);
    }

    private Map<String, Object> paginateGrids(List<Grid> grids, int page, int pageSize, String sortParam) {
        if (grids == null || grids.isEmpty()) {
            return Map.of("gridList", new ArrayList<>(), "numberOfPages", 0);
        }

        int totalGrids = grids.size();
        int totalPages = (int) Math.ceil((double) totalGrids / pageSize);

        if (page < 1) {
            page = 1;
        } else if (page > totalPages) {
            page = totalPages;
        }

        int fromIndex = (page - 1) * pageSize;

        int toIndex = Math.min(fromIndex + pageSize, totalGrids);
        if ("creationDate".equals(sortParam)) {
            grids.sort(Comparator.comparing(Grid::getCreatedAt).reversed());
        } else if ("difficultyAsc".equals(sortParam)) {
            grids.sort(Comparator.comparing(grid -> grid.getDifficulty().ordinal()));
        } else if ("difficultyDesc".equals(sortParam)) {
            grids.sort(Comparator.comparing((Grid grid) -> grid.getDifficulty().ordinal()).reversed());
        }
        List<Grid> paginatedGrids = grids.subList(fromIndex, toIndex);

        return Map.of("gridList", paginatedGrids, "numberOfPages", totalPages);
    }

    @Override
    public Optional<Grid> getGridById(UUID id) throws Exception {
        return gridRepository.findById(id);
    }

}
