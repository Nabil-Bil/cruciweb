package com.univ.service;

import com.univ.model.Grid;
import com.univ.model.Hint;
import com.univ.repository.GridRepository;
import com.univ.repository.GridRepositoryImpl;
import com.univ.validator.GridValidator;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public List<Grid> getGridList() throws Exception {
        return gridRepository.findAll();
    }

    @Override
    public Optional<Grid> getGridById(UUID id) throws Exception {
        return null;
    }
}
