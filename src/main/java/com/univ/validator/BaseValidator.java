package com.univ.validator;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseValidator {
    protected ArrayList<ValidationError> validationErrors = new ArrayList<ValidationError>();

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
    

    public boolean addError(String field, String message) {
        return this.validationErrors.add(new ValidationError(field, message));
    }

    public ValidationResult build() {
        return new ValidationResult(validationErrors);
    }

    public List<ValidationError> getValidationErrors() {
        return validationErrors;
    }
}
