package com.univ.model;

import java.util.UUID;

import com.univ.enums.Role;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column(nullable = false, unique = true, length = 50)
  @NotBlank
  @Email
  private String email;

  @Column(nullable = false, length = 255)
  @NotBlank
  @Size(min = 8, message = "Password must be at least 8 characters")
  private String password;

  @Column(nullable = false, length = 15)
  @Enumerated(EnumType.STRING)
  private Role role;

  public User() {
  }

  public User(String email, String password, Role role) {
    this.email = email;
    this.password = password;
    this.role = role;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public static User clone(User user) {
    return new User(user.getEmail(), user.getPassword(), user.getRole());
  }

}