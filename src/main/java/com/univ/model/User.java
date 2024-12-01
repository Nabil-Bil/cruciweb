package com.univ.model;

import com.univ.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    @NotBlank
    private String username;

    @Column(nullable = false, length = 255)
    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @Column(nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false, name = "created_at", updatable = false)
    private Date createdAt;

    @Column(nullable = true, name = "updated_at", updatable = true)
    private Date updatedAt;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, targetEntity = Grid.class)
    private List<Grid> grids;

    @OneToMany(mappedBy = "playedBy", cascade = CascadeType.ALL, targetEntity = Game.class)
    private List<Game> games;

    public User() {
        this.createdAt = new Date();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.createdAt = new Date();
    }

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.createdAt = new Date();
    }

    public static User copyOf(User user) {
        return new User(user.getUsername(), user.getPassword(), user.getRole());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public List<Grid> getGrids() {
        return grids;
    }

    public List<Game> getGames() {
        return games;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}