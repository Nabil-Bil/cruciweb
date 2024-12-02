package com.univ.model.embeddables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Dimension {
    @Column(nullable = false, updatable = false)
    private int width;
    @Column(nullable = false, updatable = false)
    private int height;

    public Dimension() {
    }

    public Dimension(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return String.format("%dX%d", width, height);
    }
}
