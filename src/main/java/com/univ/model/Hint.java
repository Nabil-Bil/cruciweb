package com.univ.model;

import com.univ.enums.Direction;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "hints")
public class Hint {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 512, updatable = false)
    @NotBlank
    private String value;

    @Column(nullable = false, length = 20, updatable = false)
    @Enumerated(EnumType.STRING)
    @NotBlank
    private Direction direction;

    @Column(name = "`index`", nullable = false)
    @PositiveOrZero
    private int index;

    @JoinColumn(name = "grid_id", updatable = false, nullable = false, foreignKey = @ForeignKey(name = "fk_hint_grid"), referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Grid.class)
    private Grid grid;

    public Hint() {
    }

    public Hint(String value, Direction direction, int index, Grid grid) {
        this.value = value;
        this.direction = direction;
        this.index = index;
        this.grid = grid;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hint hint = (Hint) o;
        return Objects.equals(id, hint.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
