package com.univ.validator;

public class ValidationError {
    private String field;
    private String message;

    public ValidationError(String field, String message) {
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
}