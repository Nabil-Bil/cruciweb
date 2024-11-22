package com.univ.validator;

import java.util.ArrayList;
import java.util.List;

import com.univ.model.User;

public class UserValidator extends BaseValidator {
  private final User user;
  private ArrayList<ValidationError> validationErrors;

  public static UserValidator forUser(User user) {
    return new UserValidator(user);
  }

  private UserValidator(User user) {
    this.user = user;
    this.validationErrors = new ArrayList<ValidationError>();
  }

  public UserValidator validateUsername() {
    String username = this.user.getUsername();
    if (!validateNotNull(username) || !validateNotBlank(username)) {
      validationErrors.add(new ValidationError("username", "Nom d'utilisateur ne peut être vide"));
      return this;
    }
    if (!validateMinLength(username, 3)) {
      validationErrors.add(new ValidationError("username", "Nom d'utilisateur doit avoir minimum 3 caractères"));
      return this;
    }
    if (!validateMaxLength(username, 50)) {
      validationErrors.add(new ValidationError("username", "Nom d'utilisateur ne pas doit pas dépasser 50 caractères"));
      return this;
    }
    return this;
  }

  public UserValidator validatePassword(String passwordConfirmation) {
    String password = this.user.getPassword();
    if (!validateNotNull(password) || !validateNotBlank(password)) {
      validationErrors.add(new ValidationError("password", "Mot de passe ne peut être vide"));
      return this;
    }
    if (!validateMinLength(password, 8)) {
      validationErrors.add(new ValidationError("password", "Mot de passe doit avoir minimum 8 caractères"));
      return this;
    }
    if (!validateMaxLength(password, 255)) {
      validationErrors.add(new ValidationError("password", "Mot de passe ne pas doit pas dépasser 255 caractères"));
      return this;
    }
    if (!validateNotNull(passwordConfirmation) || !password.equals(passwordConfirmation)) {
      validationErrors.add(new ValidationError("password_confirmation", "Les mots de passe ne correspondent pas"));
      return this;
    }
    return this;
  }

  public boolean isValid() {
    return validationErrors.isEmpty();
  }

  public List<ValidationError> getValidationErrors() {
    return validationErrors;
  }

}
