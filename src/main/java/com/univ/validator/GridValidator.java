package com.univ.validator;

import com.univ.enums.Direction;
import com.univ.model.embeddables.Dimension;
import com.univ.model.entity.Clue;
import com.univ.model.entity.Grid;

import java.util.concurrent.atomic.AtomicInteger;

public class GridValidator extends BaseValidator {
    public final static int MIN_SIZE = 6;
    public final static int MAX_SIZE = 26;
    private final String GRID = "grid";

    Grid grid;

    private GridValidator(Grid grid) {
        this.grid = grid;
    }

    public static GridValidator of(Grid grid) {
        return new GridValidator(grid);
    }

    public GridValidator validateDimensions() {
        Dimension gridDimensions = grid.getDimensions();
        if (gridDimensions.getHeight() <= MIN_SIZE || gridDimensions.getWidth() <= MIN_SIZE) {
            addError(GRID, String.format("la grille doit avoir une dimension minimale de %dx%d", MIN_SIZE, MIN_SIZE));
            return this;
        }
        if (gridDimensions.getHeight() > MAX_SIZE || gridDimensions.getWidth() > MAX_SIZE) {
            addError(GRID, String.format("la grille doit avoir une dimension maximale de %dx%d", MAX_SIZE, MAX_SIZE));
            return this;
        }
        if (gridDimensions.getHeight() != grid.getMatrixRepresentation().length) {
            addError(GRID, "la hauteur de la grille ne correspond pas aux dimensions de la matrice");
            return this;
        }
        for (char[] row : grid.getMatrixRepresentation()) {
            if (row.length != gridDimensions.getWidth()) {
                addError(GRID, "la largeur de la grille ne correspond pas aux dimensions de la matrice");
                return this;
            }
        }
        return this;
    }


    public GridValidator validateBlankCells(char cell) {
        for (int i = 0; i < grid.getMatrixRepresentation().length; i++) {
            for (int j = 0; j < grid.getMatrixRepresentation()[i].length; j++) {
                if (grid.getMatrixRepresentation()[i][j] == ' ') {
                    addError(GRID, "la grille ne peut pas contenir des cellules vides");
                    return this;
                }
            }
        }
        return this;
    }

    public GridValidator validateClues() {
        AtomicInteger colCluesCounter = new AtomicInteger();
        AtomicInteger rowCluesCounter = new AtomicInteger();
        this.grid.getClues().forEach((Clue clue) -> {
            if (clue.getDirection().equals(Direction.HORIZONTAL))
                rowCluesCounter.getAndIncrement();
            else
                colCluesCounter.getAndIncrement();
        });
        if (colCluesCounter.get() < grid.getDimensions().getHeight() || rowCluesCounter.get() < grid.getDimensions().getWidth()) {
            addError(GRID, "la grille doit avoir au moins 1 indice par ligne et par colonne");
            return this;
        }
        return this;
    }

    public GridValidator validateSegmentsAgainstClues() {
        for (int rowIndex = 0; rowIndex < grid.getDimensions().getHeight(); rowIndex++) {
            int segments = countSegments(grid.getMatrixRepresentation()[rowIndex]);
            int finalRowIndex = rowIndex;
            long rowCluesCount = this.grid.getClues().stream()
                    .filter(clue -> clue.getDirection() == Direction.HORIZONTAL && clue.getIndex() == finalRowIndex)
                    .count();
            if (validateSegmentsWithClues(segments, rowCluesCount)) {
                addError(GRID, String.format(
                        "Le nombre de segments dans la ligne %d (%d) ne correspond pas au nombre d'indices (%d)",
                        rowIndex + 1, segments, rowCluesCount));
            }
        }

        for (int colIndex = 0; colIndex < grid.getDimensions().getWidth(); colIndex++) {
            int segments = countSegments(getColumn(grid.getMatrixRepresentation(), colIndex));
            int finalColIndex = colIndex;
            long colCluesCount = this.grid.getClues().stream()
                    .filter(clue -> clue.getDirection() == Direction.VERTICAL && clue.getIndex() == finalColIndex)
                    .count();
            if (validateSegmentsWithClues(segments, colCluesCount)) {
                addError(GRID, String.format(
                        "Le nombre de segments dans la colonne %d (%d) ne correspond pas au nombre d'indices (%d)",
                        colIndex + 1, segments, colCluesCount));
            }
        }

        return this;
    }

    public GridValidator validateCluesUsage() {
        for (Clue clue : this.grid.getClues()) {
            int segmentCount;
            if (clue.getDirection() == Direction.HORIZONTAL) {
                segmentCount = countSegments(grid.getMatrixRepresentation()[clue.getIndex()]);
            } else {
                segmentCount = countSegments(getColumn(grid.getMatrixRepresentation(), clue.getIndex()));
            }
            if (segmentCount == 0) {
                addError(GRID, String.format("L'indice %s à la position %d est inutilisé",
                        clue.getDirection(), clue.getIndex() + 1));
            }
        }
        return this;
    }

    private int countSegments(char[] line) {
        int segmentCount = 0;
        int segmentLength = 0;

        for (char cell : line) {
            if (cell != '*') {
                segmentLength++;
            } else {
                if (segmentLength > 0) {
                    segmentCount++;
                    segmentLength = 0;
                }
            }
        }

        if (segmentLength > 0) {
            segmentCount++;
        }

        return segmentCount;
    }

    private boolean validateSegmentsWithClues(int segments, long clues) {
        return segments <= clues;
    }

    private char[] getColumn(char[][] matrix, int colIndex) {
        char[] column = new char[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            column[i] = matrix[i][colIndex];
        }
        return column;
    }

}
