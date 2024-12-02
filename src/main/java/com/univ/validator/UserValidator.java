package com.univ.validator;

import com.univ.model.User;

import java.util.Optional;

public class UserValidator extends BaseValidator {
    private final User user;
    private final Optional<User> optionalUser;
    private final String USERNAME_FIELD = "username";
    private final String PASSWORD_FIELD = "password";
    private final String PASSWORD_CONFIRMATION_FIELD = "password_confirmation";

    private UserValidator(User user, Optional<User> optionalUser) {
        this.optionalUser = optionalUser;
        this.user = user;
    }

    public static UserValidator of(User user, Optional<User> optionalUser) {
        return new UserValidator(user, optionalUser);
    }

    public UserValidator validateUsername() {
        String username = this.user.getUsername();
        if (!validateNotNull(username) || !validateNotBlank(username)) {
            addError(
                    USERNAME_FIELD, "Nom d'utilisateur ne peut être vide");
            return this;
        }
        if (!validateMinLength(username, 3)) {
            addError(USERNAME_FIELD, "Nom d'utilisateur doit avoir minimum 3 caractères");
            return this;
        }
        if (!validateMaxLength(username, 50)) {
            addError(
                    USERNAME_FIELD, "Nom d'utilisateur ne pas doit pas dépasser 50 caractères");
            return this;
        }
        if (optionalUser.isPresent()) {
            addError(USERNAME_FIELD, "Ce nom d'utilisateur existe déja!");
        }
        return this;
    }

    public UserValidator validatePassword() {
        String password = this.user.getPassword();
        if (!validateNotNull(password) || !validateNotBlank(password)) {
            addError(PASSWORD_FIELD, "Mot de passe ne peut être vide");
            return this;
        }
        if (!validateMinLength(password, 8)) {
            addError(PASSWORD_FIELD, "Mot de passe doit avoir minimum 8 caractères");
            return this;
        }
        if (!validateMaxLength(password, 255)) {
            addError(PASSWORD_FIELD, "Mot de passe ne pas doit pas dépasser 255 caractères");
            return this;
        }
        return this;
    }

    public UserValidator validatePassword(String passwordConfirmation) {
        String password = this.user.getPassword();
        this.validatePassword();
        if (!validateNotNull(passwordConfirmation) || !password.equals(passwordConfirmation)) {
            addError(PASSWORD_CONFIRMATION_FIELD, "Les mots de passe ne correspondent pas");
            return this;
        }
        return this;
    }

}
