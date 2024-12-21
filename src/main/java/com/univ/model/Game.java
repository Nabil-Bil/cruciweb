package com.univ.model;

import jakarta.persistence.*;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "games", uniqueConstraints = @UniqueConstraint(columnNames = {"grid_id", "user_id"}))
public class Game {
    @Column(nullable = false, updatable = false, name = "CREATED_AT")
    private final Date createdAt = new Date();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, updatable = false, length = 1024, name = "GRID_REPRESENTATION")
    private String gridRepresentation;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "USER_ID", nullable = false, updatable = false)
    private User playedBy;
    @ManyToOne(targetEntity = Grid.class)
    @JoinColumn(name = "GRID_ID", nullable = false, updatable = false)
    private Grid grid;

    @Column(nullable = true, name = "UPDATED_AT", updatable = true)
    private Date updatedAt = new Date();

    @Transient
    private char[][] matrixRepresentation;

    public Game() {
    }

    public Game(User playedBy) {
        this.playedBy = playedBy;
    }

    public Game(Grid grid) throws IOException {
        this.grid = grid;
        this.matrixRepresentation = grid.generateEmptyGrid();
        this.gridRepresentation = Grid.encodeGridToJson(this.matrixRepresentation);
    }


    public Game(User playedBy, Grid grid) throws IOException {
        this.playedBy = playedBy;
        this.grid = grid;
        this.matrixRepresentation = grid.generateEmptyGrid();
        this.gridRepresentation = Grid.encodeGridToJson(this.matrixRepresentation);
    }

    @PostLoad
    private void initializeTransientFields() throws IOException {
        if (this.gridRepresentation != null) {
            this.matrixRepresentation = Grid.decodeGridFromJson(this.gridRepresentation);
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getGridRepresentation() {
        return gridRepresentation;
    }

    public void setGridRepresentation(String gridRepresentation) throws IOException {

        this.gridRepresentation = gridRepresentation;
        this.matrixRepresentation = Grid.decodeGridFromJson(this.gridRepresentation);
    }

    public User getPlayedBy() {
        return playedBy;
    }

    public char[][] getMatrixRepresentation() {
        return this.matrixRepresentation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(id, game.id);
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
