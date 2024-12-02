package com.univ.repository;

import com.univ.model.Grid;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GridRepositoryImpl extends BaseRepository<Grid> implements GridRepository {
    @Override
    public Optional<Grid> findById(UUID id) throws Exception {
        return super.findById(Grid.class, id);
    }

    @Override
    public List<Grid> findAll() throws Exception {
        return super.findAll(Grid.class);
    }

    @Override
    public void deleteById(UUID id) throws Exception {
        super.deleteById(Grid.class, id);
    }
}
