package com.univ.exceptions;

public class NotFoundException extends Exception {
  public NotFoundException(String entity) {
    super(String.format("%s Not Found", entity));
  }
}
