package com.univ.validator;

import com.univ.enums.Direction;
import com.univ.model.Grid;
import com.univ.model.Hint;
import com.univ.model.embeddables.Dimension;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GridValidator extends BaseValidator {
    public final static int MIN_SIZE = 6;
    public final static int MAX_SIZE = 26;
    private final String GRID = "grid";

    Grid grid;
    List<Hint> hints;

    private GridValidator(Grid grid, List<Hint> hints) {
        this.grid = grid;
        this.hints = hints;
    }

    public static GridValidator of(Grid grid, List<Hint> hints) {
        return new GridValidator(grid, hints);
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

    public GridValidator validateHints() {
        AtomicInteger colHintsCounter = new AtomicInteger();
        AtomicInteger rowHintsCounter = new AtomicInteger();
        hints.forEach((Hint hint) -> {
            if (hint.getDirection().equals(Direction.HORIZONTAL))
                rowHintsCounter.getAndIncrement();
            else
                colHintsCounter.getAndIncrement();
        });
        if (colHintsCounter.get() < grid.getDimensions().getHeight() || rowHintsCounter.get() < grid.getDimensions().getWidth()) {
            addError(GRID, "la grille doit avoir au moins 1 indice par ligne et par colonne");
            return this;
        }
        return this;
    }

    public GridValidator validateSegmentsAgainstHints() {
        for (int rowIndex = 0; rowIndex < grid.getDimensions().getHeight(); rowIndex++) {
            int segments = countSegments(grid.getMatrixRepresentation()[rowIndex]);
            int finalRowIndex = rowIndex;
            long rowHintsCount = hints.stream()
                    .filter(hint -> hint.getDirection() == Direction.HORIZONTAL && hint.getIndex() == finalRowIndex)
                    .count();
            if (validateSegmentsWithHints(segments, rowHintsCount)) {
                addError(GRID, String.format(
                        "Le nombre de segments dans la ligne %d (%d) ne correspond pas au nombre d'indices (%d)",
                        rowIndex + 1, segments, rowHintsCount));
            }
        }

        for (int colIndex = 0; colIndex < grid.getDimensions().getWidth(); colIndex++) {
            int segments = countSegments(getColumn(grid.getMatrixRepresentation(), colIndex));
            int finalColIndex = colIndex;
            long colHintsCount = hints.stream()
                    .filter(hint -> hint.getDirection() == Direction.VERTICAL && hint.getIndex() == finalColIndex)
                    .count();
            if (validateSegmentsWithHints(segments, colHintsCount)) {
                addError(GRID, String.format(
                        "Le nombre de segments dans la colonne %d (%d) ne correspond pas au nombre d'indices (%d)",
                        colIndex + 1, segments, colHintsCount));
            }
        }

        return this;
    }

    public GridValidator validateHintsUsage() {
        for (Hint hint : hints) {
            int segmentCount;
            if (hint.getDirection() == Direction.HORIZONTAL) {
                segmentCount = countSegments(grid.getMatrixRepresentation()[hint.getIndex()]);
            } else {
                segmentCount = countSegments(getColumn(grid.getMatrixRepresentation(), hint.getIndex()));
            }
            if (segmentCount == 0) {
                addError(GRID, String.format("L'indice %s à la position %d est inutilisé",
                        hint.getDirection(), hint.getIndex() + 1));
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

    private boolean validateSegmentsWithHints(int segments, long hints) {
        return segments <= hints;
    }

    private char[] getColumn(char[][] matrix, int colIndex) {
        char[] column = new char[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            column[i] = matrix[i][colIndex];
        }
        return column;
    }

}
