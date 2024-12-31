package com.univ.validator;

import com.univ.model.entity.Game;

public class GameValidator extends BaseValidator {
    public static final String GAME_VALIDATION_CODE = "game_validation_code";
    Game game;

    private GameValidator(Game game) {
        this.game = game;
    }

    public static GameValidator of(Game game) {
        return new GameValidator(game);
    }

    public GameValidator validateGameState() {
        if (game == null) {
            addError(GAME_VALIDATION_CODE, "La partie n'existe pas");
            return this;
        }
        if (game.getGrid().getGridRepresentation().toUpperCase().compareTo(game.getGridRepresentation().toUpperCase()) != 0) {
            addError(GAME_VALIDATION_CODE, "Solution incorrecte");
            return this;
        }
        return this;
    }
}
