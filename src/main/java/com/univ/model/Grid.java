package com.univ.model;

import com.univ.enums.Direction;
import com.univ.enums.GameDifficulty;
import com.univ.model.embeddables.Dimension;
import com.univ.util.Position;
import jakarta.json.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
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

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_grid_user"), referencedColumnName = "id")
    private User createdBy;

    @OneToMany(mappedBy = "grid", cascade = CascadeType.ALL, targetEntity = Clue.class)
    private List<Clue> clues;

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

    public Grid(String name, GameDifficulty difficulty, Dimension dimensions, User createdBy, String gridRepresentation) throws IOException {
        this.name = name;
        this.difficulty = difficulty;
        this.dimensions = dimensions;
        this.createdBy = createdBy;
        this.gridRepresentation = gridRepresentation;
        this.matrixRepresentation = decodeGridFromJson(gridRepresentation);
        this.blackCells = getBlackCellsPositions();
        this.createdAt = new Date();
    }

    public static char[][] decodeGridFromJson(String json) throws IOException {
        JsonReader reader = Json.createReader(new StringReader(json));
        JsonArray rows = reader.readArray();
        char[][] matrix = new char[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            JsonArray row = rows.getJsonArray(i);
            matrix[i] = new char[row.size()];
            for (int j = 0; j < row.size(); j++) {
                matrix[i][j] = row.getString(j).charAt(0);
            }
        }
        return matrix;

    }

    public static String encodeGridToJson(char[][] matrix) throws IOException {
        try (StringWriter writer = new StringWriter(); JsonWriter jsonWriter = Json.createWriter(writer)) {
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            for (char[] row : matrix) {
                JsonArrayBuilder rowBuilder = Json.createArrayBuilder();
                for (char cell : row) {
                    rowBuilder.add(String.valueOf(cell));
                }
                arrayBuilder.add(rowBuilder.build());
            }
            JsonArray jsonArray = arrayBuilder.build();
            jsonWriter.writeArray(jsonArray);
            return writer.toString();
        }
    }

    @PostLoad
    private void initializeTransientFields() throws IOException {
        if (this.gridRepresentation != null) {
            this.matrixRepresentation = decodeGridFromJson(this.gridRepresentation);
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

    public void setGridRepresentation(String gridRepresentation) throws IOException {
        this.gridRepresentation = gridRepresentation;
        this.matrixRepresentation = decodeGridFromJson(gridRepresentation);
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

    public List<Clue> getClues() {
        return clues;
    }

    public void setClues(List<Clue> clues) {
        this.clues = clues;
    }

    public List<Clue> getHorizontalClues() {
        List<Clue> horizontalClues = new ArrayList<Clue>();
        for (Clue clue : this.clues) {
            if (clue.getDirection() == Direction.HORIZONTAL) {
                horizontalClues.add(clue);
            }
        }
        return horizontalClues;
    }

    public List<Clue> getVerticalClues() {
        List<Clue> verticalClues = new ArrayList<Clue>();
        for (Clue clue : this.clues) {
            if (clue.getDirection() == Direction.VERTICAL) {
                verticalClues.add(clue);
            }
        }
        return verticalClues;
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
