package com.univ.service;

import com.univ.enums.GameDifficulty;
import com.univ.model.embeddables.Dimension;
import com.univ.model.entity.Grid;
import com.univ.validator.GridValidator;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface GridService {
    GridValidator create(String name, GameDifficulty difficulty, Dimension dimension, String gridJson, List<String> rowClues, List<String> columnClues, HttpSession session) throws Exception;

    void deleteGrid(UUID id) throws Exception;

    Map<String, Object> getGridList(int page, int pageSize, String sortParam) throws Exception;

    Map<String, Object> getGridList(int page, int pageSize, String sortParam, HttpSession session) throws Exception;

    Optional<Grid> getGridById(UUID id) throws Exception;

}
