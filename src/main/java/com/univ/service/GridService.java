package com.univ.service;

import com.univ.model.Grid;
import com.univ.model.Hint;
import com.univ.validator.GridValidator;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface GridService {
    GridValidator createGrid(Grid grid, List<Hint> hints) throws Exception;

    void deleteGrid(UUID id) throws Exception;

    Map<String, Object> getGridList(int page, int pageSize) throws Exception;

    Optional<Grid> getGridById(UUID id) throws Exception;

}
