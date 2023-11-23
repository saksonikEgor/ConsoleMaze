package com.saksonik.ConsoleMaze.porject2.generation;

import com.saksonik.ConsoleMaze.generation.bfs.BFSGenerator;
import com.saksonik.ConsoleMaze.model.Cell;
import com.saksonik.ConsoleMaze.model.Maze;
import com.saksonik.ConsoleMaze.properties.ApplicationProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BFSGeneratorTest {
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
    @DisplayName("Генерация валидных квадратных лабиринтов алгоритмом bfs")
    void bfsSquareGeneration() {
        assertEquals(
                new Maze(generateGridByString("""
                        ██  ██████
                        ██  ██  ██
                        ██  ██  ██
                        ██      ██
                        ██████  ██""", 5, 5)),
                new BFSGenerator(new Random(1)).generate(5, 5)
        );

        assertEquals(
                new Maze(generateGridByString("""
                        ██  ████████████████████████████████████
                        ██                  ██      ██      ████
                        ██  ██████  ██████████  ██  ██  ██  ████
                        ██      ██      ██      ██  ██  ██  ████
                        ██  ██  ██████  ██  ██████████  ██  ████
                        ██  ██      ██  ██              ██  ████
                        ██  ██████  ██  ██████████████████  ████
                        ██      ██  ██      ██  ██          ████
                        ██████  ██████  ██  ██  ██  ████████████
                        ██  ██  ██      ██  ██  ██      ██  ████
                        ██  ██  ██  ██  ██  ██  ██████  ██  ████
                        ██  ██  ██  ██  ██  ██              ████
                        ██  ██  ██  ██████  ██  ████████████████
                        ██      ██  ██      ██  ██          ████
                        ██████  ██  ██  ██████  ██████████  ████
                        ██  ██  ██  ██  ██      ██      ██  ████
                        ██  ██  ██  ██████  ██████  ██  ██  ████
                        ██      ██                  ██      ████
                        ██████████████████████████████████  ████
                        ██████████████████████████████████  ████""", 20, 20)),
                new BFSGenerator(new Random(5)).generate(20, 20)
        );

        assertEquals(
                new Maze(generateGridByString("""
                        ██  ██████████████████████████████████████████████████████████████████
                        ██  ██          ██      ██                              ██      ██  ██
                        ██  ██████████  ██  ██████  ██████████████████  ██  ██  ██  ██  ██  ██
                        ██          ██      ██      ██                  ██  ██  ██  ██      ██
                        ██  ██████  ██  ██████  ██████  ██████████████  ██  ██████  ██████████
                        ██      ██                  ██      ██          ██          ██      ██
                        ██  ██  ██████████████████████████  ██  ██████████████  ██████  ██  ██
                        ██  ██  ██                  ██  ██  ██  ██          ██  ██      ██  ██
                        ██  ██  ██  ██████████  ██  ██  ██  ██  ██████████  ██  ██  ██████  ██
                        ██  ██      ██      ██  ██      ██  ██  ██      ██  ██  ██      ██  ██
                        ██  ██████████████  ██  ██████████  ██  ██  ██  ██  ██  ██████████  ██
                        ██  ██  ██          ██      ██      ██      ██  ██  ██  ██          ██
                        ██  ██  ██  ██████████  ██  ██  ██  ██████████  ██  ██████  ██████████
                        ██      ██              ██  ██  ██  ██      ██      ██      ██      ██
                        ██████████████  ██  ██████  ██  ██  ██  ██  ██████████  ██████  ██  ██
                        ██          ██  ██  ██      ██  ██      ██  ██      ██      ██  ██  ██
                        ██  ██████  ██████  ██  ██  ██  ██████████  ██  ██  ██████  ██  ██████
                        ██  ██  ██          ██  ██  ██          ██  ██  ██          ██      ██
                        ██  ██  ██  ██████████  ██  ██████████  ██  ██████  ██████████  ██  ██
                        ██      ██  ██          ██  ██          ██      ██              ██  ██
                        ██████████████  ██  ██████  ██  ██████████████  ██████  ██████████████
                        ██      ██      ██      ██  ██      ██      ██                      ██
                        ██  ██  ██  ██████████  ██  ██████████  ██  ██████████████████████  ██
                        ██  ██      ██          ██      ██      ██              ██          ██
                        ██  ██████████  ██████████████████  ██████████████████  ██████████████
                        ██      ██                          ██          ██      ██      ██  ██
                        ██████  ██████████  ██████████████████████████  ██  ██  ██  ██  ██  ██
                        ██  ██          ██          ██              ██  ██  ██  ██  ██      ██
                        ██  ██████  ██  ██████████  ██  ██████  ██  ██  ██  ██  ██  ██████  ██
                        ██      ██  ██          ██  ██  ██      ██  ██  ██  ██  ██      ██  ██
                        ██████  ██████████  ██  ██  ██  ██  ██████████  ██  ██  ██████████  ██
                        ██  ██          ██  ██  ██      ██      ██      ██  ██  ██          ██
                        ██  ██████████  ██  ██  ██████████████  ██  ██████  ██████  ██████████
                        ██                  ██              ██      ██                      ██
                        ██████████████████████████████████████████████████████████████████  ██""", 35, 35)),
                new BFSGenerator(new Random(5)).generate(35, 35)
        );
    }

    @Test
    @DisplayName("Генерация валидных прямоугольных лабиринтов алгоритмом bfs")
    void bfsRectangleGeneration() {
        assertEquals(
                new Maze(generateGridByString("""
                        ██  ██████
                        ██      ██
                        ██████  ██
                        ██████  ██""", 4, 5)),
                new BFSGenerator(new Random(5)).generate(4, 5)
        );

        assertEquals(
                new Maze(generateGridByString("""
                        ██  ████
                        ██  ████
                        ██  ████
                        ██  ████
                        ██  ████""", 5, 4)),
                new BFSGenerator(new Random(5)).generate(5, 4)
        );

        assertEquals(
                new Maze(generateGridByString("""
                        ██  ████████████████████████████████████
                        ██  ██  ██          ██              ████
                        ██  ██  ██  ██  ██████  ██████████  ████
                        ██  ██      ██  ██      ██      ██  ████
                        ██  ██████████  ██  ██████  ██  ████████
                        ██  ██              ██      ██      ████
                        ██  ██  ██████████████  ██████████  ████
                        ██                      ██          ████
                        ██████████████████████████████████  ████
                        ██████████████████████████████████  ████""", 10, 20)),
                new BFSGenerator(new Random(5)).generate(10, 20)
        );

        assertEquals(
                new Maze(generateGridByString("""
                                ██  ██████████████████████████████████████████████████████████████████████████████████████
                                ██  ██      ██      ██                          ██      ██          ██      ██      ██  ██
                                ██  ██████  ██████  ██  ██  ██████████████████  ██  ██  ██  ██████  ██████  ██████  ██  ██
                                ██                  ██  ██          ██          ██  ██  ██  ██  ██      ██  ██      ██  ██
                                ██  ██████████████████  ██████████  ██████████████  ██████  ██  ██████  ██  ██  ██████  ██
                                ██  ██              ██  ██      ██      ██              ██  ██  ██      ██  ██  ██  ██  ██
                                ██  ██  ██████████  ██  ██████  ██  ██  ██  ██████████  ██  ██  ██  ██████  ██  ██  ██  ██
                                ██  ██      ██  ██              ██  ██          ██      ██      ██  ██      ██          ██
                                ██  ██████  ██  ██████████████  ██  ██████████  ██  ██████████████  ██  ██████████████  ██
                                ██          ██      ██      ██  ██      ██      ██                      ██      ██  ██  ██
                                ██  ██  ██  ██████  ██  ██  ██████████  ██  ██  ██████████  ██████████████  ██  ██  ██  ██
                                ██  ██  ██  ██      ██  ██  ██      ██  ██  ██  ██      ██              ██  ██          ██
                                ██  ██  ██████  ██████  ██  ██  ██  ██  ██  ██  ██  ██  ██████████████  ██  ██████████  ██
                                ██  ██      ██  ██  ██  ██  ██  ██      ██  ██  ██  ██      ██          ██  ██      ██  ██
                                ██  ██████  ██  ██  ██  ██████  ██  ██  ██  ██████  ██████  ██  ██████████  ██  ██  ██  ██
                                ██      ██  ██  ██  ██          ██  ██  ██      ██      ██  ██      ██      ██  ██  ██  ██
                                ██  ██  ██████  ██  ██████████████  ██  ██████  ██████  ██  ██████  ██  ██████  ██  ██  ██
                                ██  ██      ██  ██      ██          ██      ██          ██      ██  ██  ██      ██      ██
                                ██████████  ██  ██████  ██████████████████████████████████  ██  ██  ██████  ██████████████
                                ██          ██          ██          ██          ██          ██  ██      ██  ██          ██
                                ██  ██████  ██████████  ██  ██████  ██  ██████████  ██████  ██  ██████  ██  ██  ██████  ██
                                ██  ██      ██  ██      ██      ██  ██      ██      ██      ██      ██      ██  ██      ██
                                ██  ██████████  ██  ██████████████  ██████  ██  ██████████████████  ██  ██████████  ██████
                                ██                                  ██          ██                  ██                  ██
                                ██████████████████████████████████████████████████████████████████████████████████████  ██""",
                        25, 45
                )),
                new BFSGenerator(new Random(5)).generate(25, 45)
        );
    }

    @Test
    @DisplayName("Генерация невалидных лабиринтов алгоритмом bfs")
    void bfsInvalidGeneration() {
        assertThatThrownBy(() -> new BFSGenerator(new Random()).generate(2, 3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ApplicationProperties.MAZE_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);

        assertThatThrownBy(() -> new BFSGenerator(new Random()).generate(-2, 20))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ApplicationProperties.MAZE_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);

        assertThatThrownBy(() -> new BFSGenerator(new Random()).generate(12, 2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ApplicationProperties.MAZE_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE);
    }
}
