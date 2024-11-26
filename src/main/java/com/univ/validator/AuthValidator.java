package com.univ.validator;

import java.util.Optional;

import com.univ.model.User;
import com.univ.util.BCryptUtil;

public class AuthValidator extends BaseValidator {
  private final User user;
  private final Optional<User> optionalUser;
  private final String USERNAME_FIELD = "username";
  private final String PASSWORD_FIELD = "password";

  private AuthValidator(User user, Optional<User> optionalUser) {
    this.user = user;
    this.optionalUser = optionalUser;
  }

  public static AuthValidator forUser(User user, Optional<User> optionalUser) {
    return new AuthValidator(user, optionalUser);
  }

  public AuthValidator validateUsername() {
    if (!optionalUser.isPresent()) {
      addError(USERNAME_FIELD, "Cet utilisateur n'existe pas");
      return this;
    }
    return this;
  }

  public AuthValidator validatePassword() {
    if (optionalUser.isPresent()) {
      Boolean isValidPassword = BCryptUtil.verifyPassword(user.getPassword(), optionalUser.get().getPassword());
      if (!isValidPassword) {
        addError(PASSWORD_FIELD, "Mot de passe incorrect!");
      }
    }
    return this;
  }

}
