package com.univ.validator;

public class ValidationResult {
    private String field;
    private String message;

    public ValidationResult(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getErrorField() {
        return field.concat("_error");
    }

    public String getMessage() {
        return message;
    }
    
    public boolean isValid() {
        return validationResults.isEmpty();
    }

    public boolean isNotValid() {
        return !this.isValid();
    }
}