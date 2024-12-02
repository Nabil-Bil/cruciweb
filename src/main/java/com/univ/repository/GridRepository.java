package com.univ.repository;

import com.univ.model.Grid;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GridRepository {
    Grid save(Grid user) throws Exception;

    Optional<Grid> findById(UUID id) throws Exception;

    List<Grid> findAll() throws Exception;

    void deleteById(UUID id) throws Exception;
}
