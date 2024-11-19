package com.univ.enums;

public enum Role {
  ADMIN("Administrator"),
  USER("Authenticated User");

  private final String description;

  Role(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}