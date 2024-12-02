package com.univ.model;

import com.univ.enums.GameDifficulty;
import com.univ.model.embeddables.Dimension;
import com.univ.util.Position;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.*;

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

    @Column(nullable = false, updatable = false, length = 1024, name = "grid_representation")
    private String gridRepresentation;

    @Column(nullable = false, length = 50, updatable = false)
    @Enumerated(EnumType.STRING)
    @NotBlank
    private GameDifficulty difficulty;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "width", column = @Column(nullable = false)),
            @AttributeOverride(name = "height", column = @Column(nullable = false))
    })
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

    @Transient
    private char[][] matrixRepresentation;

    @Transient
    private List<Position> blackCells;

    public Grid() {
    }

    public Grid(String name, GameDifficulty difficulty, Dimension dimensions, User createdBy) {
        this.name = name;
        this.difficulty = difficulty;
        this.dimensions = dimensions;
        this.createdBy = createdBy;
        this.createdAt = new Date();
    }

    public Grid(String name, GameDifficulty difficulty, Dimension dimensions, User createdBy, String gridRepresentation) {
        this.name = name;
        this.difficulty = difficulty;
        this.dimensions = dimensions;
        this.createdBy = createdBy;
        this.gridRepresentation = gridRepresentation;
        this.matrixRepresentation = convertTextToMatrix(gridRepresentation);
        this.blackCells = getBlackCellsPositions();
        this.createdAt = new Date();
    }

    public static char[][] convertTextToMatrix(String gridText) {
        String[] rows = gridText.split("\n");
        char[][] matrix = new char[rows.length][];

        for (int i = 0; i < rows.length; i++) {
            matrix[i] = rows[i].toCharArray();
        }

        return matrix;
    }

    public static String convertMatrixToText(char[][] matrix) {
        StringBuilder gridText = new StringBuilder();

        for (char[] row : matrix) {
            for (char cell : row) {
                gridText.append(cell).append(" ");
            }
            gridText.setLength(gridText.length() - 1);
            gridText.append("\n");
        }

        return gridText.toString().trim();
    }

    @PostLoad
    private void initializeTransientFields() {
        if (this.gridRepresentation != null) {
            this.matrixRepresentation = convertTextToMatrix(this.gridRepresentation);
            this.blackCells = getBlackCellsPositions();
        }
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

    public String getGridRepresentation() {
        return gridRepresentation;
    }

    public void setGridRepresentation(String gridRepresentation) {
        this.gridRepresentation = gridRepresentation;
        this.matrixRepresentation = convertTextToMatrix(gridRepresentation);
        this.blackCells = getBlackCellsPositions();
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

    public char[][] getMatrixRepresentation() {
        return matrixRepresentation;
    }

    public List<Position> getBlackCells() {
        return blackCells;
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

    private List<Position> getBlackCellsPositions() {

        List<Position> blackCellsPositions = new ArrayList<Position>();
        for (int row = 0; row < this.matrixRepresentation.length; row++) {
            for (int col = 0; col < this.matrixRepresentation[row].length; col++) {
                if (this.matrixRepresentation[row][col] == '*') {
                    blackCellsPositions.add(new Position(row, col));
                }
            }
        }
        return blackCellsPositions;
    }
}
