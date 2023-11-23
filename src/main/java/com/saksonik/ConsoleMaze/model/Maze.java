package com.saksonik.ConsoleMaze.model;

import com.saksonik.ConsoleMaze.properties.ApplicationProperties;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import static com.saksonik.ConsoleMaze.model.Cell.Type.PASSAGE;
import static com.saksonik.ConsoleMaze.model.Cell.Type.WALL;
import static java.util.stream.Collectors.toList;

public class Maze {
    private final int height;
    private final int width;
    private final Cell[][] grid;
    private Cell entrance;
    private Cell exit;

    public Maze(int height, int width) {
        if (height < ApplicationProperties.SIZE_LOWER_BOUND || width < ApplicationProperties.SIZE_LOWER_BOUND) {
            throw new IllegalArgumentException(ApplicationProperties.MAZE_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
        }

        this.height = height;
        this.width = width;
        grid = new Cell[height][width];

        fillGrid();
    }

    public Maze(Cell[][] grid) {
        this.grid = grid;
        this.height = grid.length;
        this.width = grid[0].length;

        makeEntranceAndExit();
    }

    private void fillGrid() {
        fillAlternately();
        fillGaps();
        makeEntranceAndExit();
    }

    private void putCell(int row, int column, Cell.Type type) {
        grid[row][column] = new Cell(row, column, type);
    }

    private void fillAlternately() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if ((i & 1) == 0 || (j & 1) == 0) {
                    putCell(i, j, WALL);
                } else {
                    putCell(i, j, PASSAGE);
                }
            }
        }
    }

    private void fillGaps() {
        if (height % 2 == 0) {
            wallLastRow();
        }
        if (width % 2 == 0) {
            wallLastColumn();
        }
    }

    private void wallLastColumn() {
        for (int i = 0; i < height; i++) {
            putCell(i, width - 1, WALL);
        }
    }

    private void wallLastRow() {
        for (int i = 0; i < width; i++) {
            putCell(height - 1, i, WALL);
        }
    }

    private int getExitColumn() {
        return width - ApplicationProperties.SIZE_LOWER_BOUND + width % 2;
    }

    private void makeEntranceAndExit() {
        putCell(0, 1, PASSAGE);
        setEntrance(grid[0][1]);

        putCell(height - 1, getExitColumn(), PASSAGE);
        setExit(grid[height - 1][getExitColumn()]);

        if (height % 2 == 0) {
            putCell(height - 2, getExitColumn(), PASSAGE);
            setExit(grid[height - 2][getExitColumn()]);
        }
    }

    public Cell getEntrance() {
        return entrance;
    }

    private void setEntrance(Cell entrance) {
        this.entrance = entrance;
    }

    public Cell getExit() {
        return exit;
    }

    private void setExit(Cell exit) {
        this.exit = exit;
    }

    public void putCells(List<Cell> passages) {
        passages.forEach(cell -> grid[cell.row()][cell.column()] = cell);
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public void putSpanningTree(List<Edge> spanningTree, int width) {
        putCells(createPassages(spanningTree, width));
    }

    private List<Cell> createPassages(List<Edge> spanningTree, int width) {
        return spanningTree
                .stream()
                .map(edge -> getPassage(fromIndex(edge.firstCell(), width), fromIndex(edge.secondCell(), width)))
                .collect(toList());
    }

    private Cell fromIndex(int index, int width) {
        return new Cell(index / width, index % width, PASSAGE);
    }

    private Cell getPassage(Cell first, Cell second) {
        return new Cell(
                first.row() + second.row() + 1,
                first.column() + second.column() + 1,
                PASSAGE
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Maze maze = (Maze) o;
        return height == maze.height && width == maze.width && Arrays.deepEquals(grid, maze.grid)
                && Objects.equals(entrance, maze.entrance) && Objects.equals(exit, maze.exit);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(height, width, entrance, exit);
        result = 31 * result + Arrays.deepHashCode(grid);
        return result;
    }
}
