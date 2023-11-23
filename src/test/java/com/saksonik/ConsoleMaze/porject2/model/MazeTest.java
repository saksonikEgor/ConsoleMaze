package com.saksonik.ConsoleMaze.porject2.model;

import com.saksonik.ConsoleMaze.model.Cell;
import com.saksonik.ConsoleMaze.model.Edge;
import com.saksonik.ConsoleMaze.model.Maze;
import com.saksonik.ConsoleMaze.properties.ApplicationProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MazeTest {
    private static String deleteEverySecondCharacter(String str) {
        StringBuilder sb = new StringBuilder(str);
        for (int i = 1; i < sb.length(); i++) {
            sb.deleteCharAt(i);
        }
        return sb.toString();
    }

    static Cell[][] generateGridByString(String maze, int height, int width) {
        String[] splits = maze.split("\n");
        char passageChar = ApplicationProperties.PASSAGE_STRING.charAt(0);
        Cell[][] grid = new Cell[height][width];

        for (int i = 0; i < splits.length; i++) {
            String row = deleteEverySecondCharacter(splits[i]);

            for (int j = 0; j < row.length(); j++) {
                if (row.charAt(j) == passageChar) {
                    grid[i][j] = new Cell(i, j, Cell.Type.PASSAGE);
                } else {
                    grid[i][j] = new Cell(i, j, Cell.Type.WALL);
                }
            }
        }
        return grid;
    }

    @Test
    @DisplayName("Наложение клеток на лабиринт")
    void putCell() {
        Maze actualMaze = new Maze(generateGridByString("""
                ██  ██████
                ██  ██  ██
                ██  ██  ██
                ██      ██
                ██████  ██""", 5, 5));
        actualMaze.putCells(List.of(
                new Cell(0, 1, Cell.Type.WALL), new Cell(2, 0, Cell.Type.PASSAGE),
                new Cell(2, 1, Cell.Type.WALL), new Cell(2, 3, Cell.Type.WALL),
                new Cell(4, 3, Cell.Type.WALL)
        ));
        assertArrayEquals(generateGridByString("""
                ██████████
                ██  ██  ██
                  ████████
                ██      ██
                ██████████""", 5, 5), actualMaze.getGrid());

        actualMaze = new Maze(generateGridByString("""
                ██  ██████
                ██      ██
                ██  ██  ██
                ██      ██
                ██  ██  ██""", 5, 5));
        actualMaze.putCells(Collections.emptyList());
        assertArrayEquals(generateGridByString("""
                ██  ██████
                ██      ██
                ██  ██  ██
                ██      ██
                ██  ██  ██""", 5, 5), actualMaze.getGrid());
    }

    @Test
    @DisplayName("Наложение проходов на лабиринт")
    void putSpanningTree() {
        Maze actualMaze = new Maze(generateGridByString("""
                ██  ██████
                ██  ██  ██
                ██████████
                ██  ██  ██
                ██████  ██""", 5, 5));
        actualMaze.putSpanningTree(List.of(new Edge(0, 1), new Edge(0, 2), new Edge(2, 3)), 2);
        assertArrayEquals(generateGridByString("""
                ██  ██████
                ██      ██
                ██  ██████
                ██      ██
                ██████  ██""", 5, 5), actualMaze.getGrid());

        actualMaze = new Maze(generateGridByString("""
                ██  ██████
                ██  ██  ██
                ██████████
                ██  ██  ██
                ██████████
                ██  ██  ██
                ██████████
                ██  ██  ██
                ██████  ██""", 9, 5));
        actualMaze.putSpanningTree(List.of(
                new Edge(0, 1), new Edge(1, 3), new Edge(3, 2),
                new Edge(2, 4), new Edge(4, 5), new Edge(4, 6), new Edge(6, 7)
        ), 2);
        assertArrayEquals(generateGridByString("""
                ██  ██████
                ██      ██
                ██████  ██
                ██      ██
                ██  ██████
                ██      ██
                ██  ██████
                ██      ██
                ██████  ██""", 9, 5), actualMaze.getGrid());

        actualMaze = new Maze(generateGridByString("""
                ██  ██████
                ██  ██  ██
                ██████████
                ██  ██  ██
                ██████  ██""", 5, 5));
        actualMaze.putSpanningTree(Collections.emptyList(), 2);
        assertArrayEquals(generateGridByString("""
                ██  ██████
                ██  ██  ██
                ██████████
                ██  ██  ██
                ██████  ██""", 5, 5), actualMaze.getGrid());
    }
}
