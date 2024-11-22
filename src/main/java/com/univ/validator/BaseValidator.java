package com.univ.validator;

public abstract class BaseValidator {

  protected boolean validateNotNull(Object value) {
    return value != null;
  }

  protected boolean validateNotBlank(String value) {
    return !value.isBlank();
  }

  protected boolean validateMinLength(String value, int minLength) {
    return value.trim().length() >= minLength;
  }

  protected boolean validateMaxLength(String value, int maxLength) {
    return value.trim().length() <= maxLength;
  }

}
