package com.saksonik.ConsoleMaze.model;

public record Cell(int row, int column, Type type) {
    public boolean isWall() {
        return type == Type.WALL;
    }

    public boolean isEscape() {
        return type == Type.ESCAPE;
    }

    public enum Type {
        WALL,
        PASSAGE,
        ESCAPE
    }
}
