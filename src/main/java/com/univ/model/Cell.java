package com.univ.model;

import com.univ.model.embeddables.Position;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "cells")
public class Cell {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, updatable = false)
    @Embedded
    private Position position;

    @Column(length = 1)
    private char value;

    @JoinColumn(name = "grid_id", updatable = false, nullable = false, foreignKey = @ForeignKey(name = "fk_cell_grid"), referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Grid.class)
    private Grid grid;

    public Cell() {
    }

    public Cell(int row, int column, char value) {
        this.position = new Position(row, column);
        this.value = value;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(int row, int column) {
        this.position = new Position(row, column);
    }

    public int getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return Objects.equals(id, cell.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
