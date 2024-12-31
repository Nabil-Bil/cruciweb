package com.univ.validator;

import java.util.List;

public class ValidationResult {
    private final List<ValidationError> errors;
    private final boolean valid;

    public ValidationResult(List<ValidationError> errors) {
        this.errors = errors;
        this.valid = errors.isEmpty();
    }


    public boolean isValid() {
        return valid;
    }

    public boolean isInvalid() {
        return !valid;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }
}