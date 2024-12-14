package com.univ.enums;

public enum GameDifficulty {
    EASY("Facile"),
    MEDIUM("Moyenne"),
    HARD("Difficile");

    private final String displayName;

    GameDifficulty(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
