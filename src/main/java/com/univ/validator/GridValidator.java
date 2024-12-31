package com.univ.validator;

import com.univ.enums.Direction;
import com.univ.model.embeddables.Dimension;
import com.univ.model.entity.Clue;
import com.univ.model.entity.Grid;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class GridValidator extends BaseValidator {
    public final static int MIN_SIZE = 6;
    public final static int MAX_SIZE = 26;
    private final String GRID_VALIDATION_CODE = "grid_validation_code";

    Grid grid;

    private GridValidator(Grid grid) {
        this.grid = grid;
    }

    public static GridValidator of(Grid grid) {
        return new GridValidator(grid);
    }

    public GridValidator validateGridData() {
        if (grid.getName() == null || grid.getName().isBlank()) {
            addError(GRID_VALIDATION_CODE, "le nom de la grille ne peut pas être vide");
            return this;
        }
        if (grid.getDifficulty() == null) {
            addError(GRID_VALIDATION_CODE, "la difficulté de la grille ne peut pas être vide");
            return this;
        }
        if (grid.getDimensions() == null) {
            addError(GRID_VALIDATION_CODE, "les dimensions de la grille ne peuvent pas être vides");
            return this;
        }
        return this;
    }

    public GridValidator validateDimensions() {
        Dimension gridDimensions = grid.getDimensions();
        if (gridDimensions.getHeight() < MIN_SIZE || gridDimensions.getWidth() < MIN_SIZE) {
            addError(GRID_VALIDATION_CODE, String.format("la grille doit avoir une dimension minimale de %dx%d", MIN_SIZE, MIN_SIZE));
            return this;
        }
        if (gridDimensions.getHeight() > MAX_SIZE || gridDimensions.getWidth() > MAX_SIZE) {
            addError(GRID_VALIDATION_CODE, String.format("la grille doit avoir une dimension maximale de %dx%d", MAX_SIZE, MAX_SIZE));
            return this;
        }
        if (gridDimensions.getHeight() != grid.getMatrixRepresentation().length) {
            addError(GRID_VALIDATION_CODE, "la hauteur de la grille ne correspond pas aux dimensions de la matrice");
            return this;
        }
        for (char[] row : grid.getMatrixRepresentation()) {
            if (row.length != gridDimensions.getWidth()) {
                addError(GRID_VALIDATION_CODE, "la largeur de la grille ne correspond pas aux dimensions de la matrice");
                return this;
            }
        }
        return this;
    }


    public GridValidator validateBlankCells() {
        for (int i = 0; i < grid.getMatrixRepresentation().length; i++) {
            for (int j = 0; j < grid.getMatrixRepresentation()[i].length; j++) {
                if (grid.getMatrixRepresentation()[i][j] == ' ') {
                    addError(GRID_VALIDATION_CODE, "la grille ne peut pas contenir des cellules vides");
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
            if (clue.getValue().isBlank()) {
                addError(GRID_VALIDATION_CODE, "les indices ne peuvent pas être vides");
                return;
            }
            if (clue.getDirection().equals(Direction.HORIZONTAL))
                rowCluesCounter.getAndIncrement();
            else
                colCluesCounter.getAndIncrement();
        });
        if (colCluesCounter.get() < grid.getDimensions().getWidth() || rowCluesCounter.get() < grid.getDimensions().getHeight()) {
            addError(GRID_VALIDATION_CODE, "la grille doit avoir au moins 1 indice par ligne et par colonne");
            return this;
        }
        return this;
    }

    public GridValidator validateAlphabet() {
        for (char[] row : grid.getMatrixRepresentation()) {
            for (char cell : row) {
                if (!Character.isAlphabetic(cell) && cell != '*') {
                    addError(GRID_VALIDATION_CODE, "la grille ne peut contenir que des lettres ou des cases noir");
                    return this;
                }
            }
        }
        return this;
    }

    public GridValidator validateClueAlignment() {
        char[][] matrix = grid.getMatrixRepresentation();

        for (int row = 0; row < matrix.length; row++) {
            char[] line = matrix[row];
            int finalRow = row;
            int clueCount = grid.getClues().stream()
                    .filter(clue -> clue.getDirection() == Direction.HORIZONTAL && clue.getIndex() == finalRow)
                    .mapToInt(this::countWordsInClue)
                    .sum();

            int wordCount = countWords(line);

            if (clueCount != wordCount) {
                addError(GRID_VALIDATION_CODE, String.format(
                        "Le nombre d'indices (%d) pour la ligne %d ne correspond pas au nombre de mots (%d).",
                        clueCount, row + 1, wordCount));
                return this;
            }
        }

        for (int col = 0; col < matrix[0].length; col++) {
            char[] column = getColumn(matrix, col);
            int finalCol = col;
            int clueCount = grid.getClues().stream()
                    .filter(clue -> clue.getDirection() == Direction.VERTICAL && clue.getIndex() == finalCol)
                    .mapToInt(this::countWordsInClue)
                    .sum();

            int wordCount = countWords(column);

            if (clueCount != wordCount) {
                addError(GRID_VALIDATION_CODE, String.format(
                        "Le nombre d'indices (%d) pour la colonne %d ne correspond pas au nombre de mots (%d).",
                        clueCount, col + 1, wordCount));
                return this;
            }
        }

        return this;
    }

    private int countWords(char[] line) {
        int wordCount = 0;
        StringBuilder currentWord = new StringBuilder();

        for (char c : line) {
            if (Character.isAlphabetic(c)) {
                currentWord.append(c);
            } else if (c == '*' || Character.isWhitespace(c)) {
                if (currentWord.length() > 1) {
                    wordCount++;
                }
                currentWord.setLength(0);
            }
        }

        if (currentWord.length() > 1) {
            wordCount++;
        }

        return wordCount;
    }

    private char[] getColumn(char[][] matrix, int col) {
        char[] column = new char[matrix.length];
        for (int row = 0; row < matrix.length; row++) {
            column[row] = matrix[row][col];
        }
        return column;
    }

    private int countWordsInClue(Clue clue) {
        String clueValue = clue.getValue();

        if (clueValue.isBlank()) return 0;

        clueValue = clueValue.replaceAll("^-+", "").replaceAll("-+$", "");

        String[] words = clueValue.split("-");
        return (int) Arrays.stream(words)
                .filter(word -> !word.isBlank())
                .count();
    }

}
