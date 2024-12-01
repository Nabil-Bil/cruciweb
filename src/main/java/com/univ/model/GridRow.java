package com.univ.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "grid_rows")
public class GridRow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "`index`", nullable = false, updatable = false)
    @PositiveOrZero
    private int index;

    @Column(nullable = false, updatable = false, length = 50)
    private String value;

    @JoinColumn(name = "game_id", updatable = false, nullable = false, foreignKey = @ForeignKey(name = "fk_grid_row_game"), referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Game.class)
    private Game game;

    public GridRow() {
    }

    public GridRow(int index, String value, Game game) {
        this.index = index;
        this.value = value;
        this.game = game;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GridRow row = (GridRow) o;
        return Objects.equals(id, row.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
