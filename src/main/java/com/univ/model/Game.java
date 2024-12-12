package com.univ.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "games")
public class Game {
    @Column(nullable = false)
    Boolean isSolved;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, updatable = false, length = 1024, name = "grid_representation")
    private String gridRepresentation;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_game_user"), referencedColumnName = "id")
    private User playedBy;

    public Game() {
    }

    public Game(User playedBy) {
        this.isSolved = false;
        this.playedBy = playedBy;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Boolean getSolved() {
        return isSolved;
    }

    public void setSolved(Boolean solved) {
        isSolved = solved;
    }

    public String getGridRepresentation() {
        return gridRepresentation;
    }

    public void setGridRepresentation(String gridRepresentation) {
        this.gridRepresentation = gridRepresentation;
    }

    public User getPlayedBy() {
        return playedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(id, game.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
