package com.univ.model;

import com.univ.enums.GameDifficulty;
import com.univ.model.embeddables.Dimension;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "grids")
public class Grid {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, length = 50, updatable = false)
    @NotBlank
    private String name;

    @OneToMany(mappedBy = "grid", cascade = CascadeType.ALL, targetEntity = Cell.class)
    private List<Cell> cells;

    @Column(nullable = false, length = 50, updatable = false)
    @Enumerated(EnumType.STRING)
    @NotBlank
    private GameDifficulty difficulty;

    @Embedded
    private Dimension dimensions;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_grid_user"), referencedColumnName = "id")
    private User createdBy;

    @OneToMany(mappedBy = "grid", cascade = CascadeType.ALL, targetEntity = Hint.class)
    private List<Hint> hints;

    @Column(nullable = false, name = "created_at", updatable = false)
    private Date createdAt;

    @Column(nullable = true, name = "updated_at", updatable = true)
    private Date updatedAt;


    public Grid() {
    }

    public Grid(String name, GameDifficulty difficulty, Dimension dimensions, User createdBy) {
        this.name = name;
        this.difficulty = difficulty;
        this.dimensions = dimensions;
        this.createdBy = createdBy;
        this.createdAt = new Date();
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    public GameDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(GameDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Dimension getDimensions() {
        return dimensions;
    }

    public void setDimensions(Dimension dimensions) {
        this.dimensions = dimensions;
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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public List<Hint> getHints() {
        return hints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grid grid = (Grid) o;
        return Objects.equals(id, grid.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
