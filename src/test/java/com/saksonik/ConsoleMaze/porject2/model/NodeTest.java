package com.saksonik.ConsoleMaze.porject2.model;

import com.saksonik.ConsoleMaze.model.Cell;
import com.saksonik.ConsoleMaze.model.Node;
import com.saksonik.ConsoleMaze.properties.ApplicationProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NodeTest {
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

    static Node[][] generateNodeGridByString(String maze, int height, int width) {
        String[] splits = maze.split("\n");
        char passageChar = ApplicationProperties.PASSAGE_STRING.charAt(0);
        Node[][] grid = new Node[height][width];

        for (int i = 0; i < splits.length; i++) {
            String row = deleteEverySecondCharacter(splits[i]);

            for (int j = 0; j < row.length(); j++) {
                if (row.charAt(j) == passageChar) {
                    grid[i][j] = new Node(i, j, false);
                } else {
                    grid[i][j] = new Node(i, j, true);
                }
            }
        }
        return grid;
    }

    @Test
    @DisplayName("Создание Node[][] по Cell[][]")
    void createGridOfNodes() {
        assertArrayEquals(
                generateNodeGridByString("""
                        ██  ██████████████████████████████████████████████████████████████████
                        ██  ██              ██                          ██              ██  ██
                        ██  ██████████  ██  ██████  ██████████████████  ██  ██████  ██  ██  ██
                        ██          ██  ██          ██                  ██      ██  ██      ██
                        ██████████  ██  ██████████████  ██████████████████████████  ██████  ██
                        ██      ██      ██          ██      ██                      ██  ██  ██
                        ██  ██████████████████████  ██████  ██  ██████████████████████  ██  ██
                        ██  ██                  ██      ██  ██  ██              ██      ██  ██
                        ██  ██  ██████████  ██  ██████  ██  ██  ██████  ██████  ██  ██  ██  ██
                        ██  ██          ██  ██  ██      ██  ██      ██      ██  ██  ██      ██
                        ██  ██  ██████████  ██  ██  ██  ██  ██  ██  ██████  ██████  ██████████
                        ██  ██  ██          ██      ██  ██  ██  ██      ██      ██          ██
                        ██  ██  ██  ██████████████████████  ██████████  ██  ██  ██████████  ██
                        ██      ██                      ██  ██      ██  ██  ██          ██  ██
                        ██  ██████████████████████████  ██  ██  ██  ██  ██████  ██████  ██  ██
                        ██  ██                      ██  ██      ██  ██      ██      ██  ██  ██
                        ██  ██████████████  ██████  ██  ██████████  ██  ██  ██  ██  ██████  ██
                        ██  ██          ██      ██  ██          ██  ██  ██  ██  ██      ██  ██
                        ██  ██  ██████  ██████  ██  ██████████  ██  ██████  ██████  ██  ██  ██
                        ██      ██  ██          ██  ██      ██  ██      ██      ██  ██      ██
                        ██████████  ██████████████  ██████  ██  ██████  ██████  ██  ██████████
                        ██          ██      ██      ██      ██      ██          ██          ██
                        ██████  ██  ██  ██████  ██████  ██████  ██  ██████████████████████  ██
                        ██      ██  ██                  ██      ██      ██                  ██
                        ██  ██████████  ██████████████████  ██████████  ██  ██████████████████
                        ██      ██              ██          ██          ██  ██  ██          ██
                        ██  ██  ██  ██████████████  ██████████  ██████████  ██  ██  ██  ██████
                        ██  ██  ██      ██          ██          ██      ██  ██  ██  ██      ██
                        ██  ██  ██████  ██  ██████████  ██████████████  ██  ██  ██  ██████  ██
                        ██  ██          ██  ██      ██                  ██  ██          ██  ██
                        ██  ██████████████  ██████  ██  ██████████████████  ██  ██████████  ██
                        ██              ██      ██      ██              ██  ██  ██          ██
                        ██████████████  ██████  ██████████  ██████████  ██  ██████  ██████  ██
                        ██                  ██                      ██              ██      ██
                        ██████████████████████████████████████████████████████████████████  ██""", 35, 35),
                Node.createGridOfNodes(generateGridByString("""
                        ██  ██████████████████████████████████████████████████████████████████
                        ██  ██              ██                          ██              ██  ██
                        ██  ██████████  ██  ██████  ██████████████████  ██  ██████  ██  ██  ██
                        ██          ██  ██          ██                  ██      ██  ██      ██
                        ██████████  ██  ██████████████  ██████████████████████████  ██████  ██
                        ██      ██      ██          ██      ██                      ██  ██  ██
                        ██  ██████████████████████  ██████  ██  ██████████████████████  ██  ██
                        ██  ██                  ██      ██  ██  ██              ██      ██  ██
                        ██  ██  ██████████  ██  ██████  ██  ██  ██████  ██████  ██  ██  ██  ██
                        ██  ██          ██  ██  ██      ██  ██      ██      ██  ██  ██      ██
                        ██  ██  ██████████  ██  ██  ██  ██  ██  ██  ██████  ██████  ██████████
                        ██  ██  ██          ██      ██  ██  ██  ██      ██      ██          ██
                        ██  ██  ██  ██████████████████████  ██████████  ██  ██  ██████████  ██
                        ██      ██                      ██  ██      ██  ██  ██          ██  ██
                        ██  ██████████████████████████  ██  ██  ██  ██  ██████  ██████  ██  ██
                        ██  ██                      ██  ██      ██  ██      ██      ██  ██  ██
                        ██  ██████████████  ██████  ██  ██████████  ██  ██  ██  ██  ██████  ██
                        ██  ██          ██      ██  ██          ██  ██  ██  ██  ██      ██  ██
                        ██  ██  ██████  ██████  ██  ██████████  ██  ██████  ██████  ██  ██  ██
                        ██      ██  ██          ██  ██      ██  ██      ██      ██  ██      ██
                        ██████████  ██████████████  ██████  ██  ██████  ██████  ██  ██████████
                        ██          ██      ██      ██      ██      ██          ██          ██
                        ██████  ██  ██  ██████  ██████  ██████  ██  ██████████████████████  ██
                        ██      ██  ██                  ██      ██      ██                  ██
                        ██  ██████████  ██████████████████  ██████████  ██  ██████████████████
                        ██      ██              ██          ██          ██  ██  ██          ██
                        ██  ██  ██  ██████████████  ██████████  ██████████  ██  ██  ██  ██████
                        ██  ██  ██      ██          ██          ██      ██  ██  ██  ██      ██
                        ██  ██  ██████  ██  ██████████  ██████████████  ██  ██  ██  ██████  ██
                        ██  ██          ██  ██      ██                  ██  ██          ██  ██
                        ██  ██████████████  ██████  ██  ██████████████████  ██  ██████████  ██
                        ██              ██      ██      ██              ██  ██  ██          ██
                        ██████████████  ██████  ██████████  ██████████  ██  ██████  ██████  ██
                        ██                  ██                      ██              ██      ██
                        ██████████████████████████████████████████████████████████████████  ██""", 35, 35))
        );

        assertArrayEquals(
                generateNodeGridByString("""
                        ██  ████████████████████████████████████
                        ██  ██      ██              ██      ████
                        ██  ██  ██  ██  ██████  ██  ██  ██  ████
                        ██  ██  ██  ██  ██      ██  ██  ██  ████
                        ██  ██  ██  ██  ██  ██████████  ██  ████
                        ██  ██  ██  ██  ██              ██  ████
                        ██  ██████  ██  ██████████████████  ████
                        ██      ██  ██  ██      ██          ████
                        ██████  ██  ██  ██████  ██  ██████  ████
                        ██  ██  ██      ██      ██      ██  ████
                        ██  ██  ██  ██████  ██████████  ██  ████
                        ██  ██  ██  ██      ██          ██  ████
                        ██  ██  ██  ██████  ██  ██████████  ████
                        ██      ██          ██  ██      ██  ████
                        ██  ██████████  ██████  ██████  ██  ████
                        ██  ██      ██  ██      ██      ██  ████
                        ██  ██  ██  ██████  ██████  ██████  ████
                        ██      ██          ██              ████
                        ██████████████████████████████████  ████
                        ██████████████████████████████████  ████""", 20, 20),
                Node.createGridOfNodes(generateGridByString("""
                        ██  ████████████████████████████████████
                        ██  ██      ██              ██      ████
                        ██  ██  ██  ██  ██████  ██  ██  ██  ████
                        ██  ██  ██  ██  ██      ██  ██  ██  ████
                        ██  ██  ██  ██  ██  ██████████  ██  ████
                        ██  ██  ██  ██  ██              ██  ████
                        ██  ██████  ██  ██████████████████  ████
                        ██      ██  ██  ██      ██          ████
                        ██████  ██  ██  ██████  ██  ██████  ████
                        ██  ██  ██      ██      ██      ██  ████
                        ██  ██  ██  ██████  ██████████  ██  ████
                        ██  ██  ██  ██      ██          ██  ████
                        ██  ██  ██  ██████  ██  ██████████  ████
                        ██      ██          ██  ██      ██  ████
                        ██  ██████████  ██████  ██████  ██  ████
                        ██  ██      ██  ██      ██      ██  ████
                        ██  ██  ██  ██████  ██████  ██████  ████
                        ██      ██          ██              ████
                        ██████████████████████████████████  ████
                        ██████████████████████████████████  ████""", 20, 20))
        );
    }

    @Test
    @DisplayName("Построение пути")
    void reconstructPath() {
        Node[][] nodeGrid = Node.createGridOfNodes(generateGridByString("""
                ██▓▓████████████████████████████████████
                ██▓▓██  ██          ██              ████
                ██▓▓██  ██  ██  ██████  ██████████  ████
                ██▓▓██      ██  ██      ██▓▓▓▓▓▓██  ████
                ██▓▓██████████  ██  ██████▓▓██▓▓████████
                ██▓▓██              ██▓▓▓▓▓▓██▓▓▓▓▓▓████
                ██▓▓██  ██████████████▓▓██████████▓▓████
                ██▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓██        ▓▓████
                ██████████████████████████████████▓▓████
                ██████████████████████████████████  ████""", 10, 20));
        nodeGrid[1][1].setParent(nodeGrid[0][1]);
        nodeGrid[2][1].setParent(nodeGrid[1][1]);
        nodeGrid[3][1].setParent(nodeGrid[2][1]);
        nodeGrid[4][1].setParent(nodeGrid[3][1]);
        nodeGrid[5][1].setParent(nodeGrid[4][1]);
        nodeGrid[6][1].setParent(nodeGrid[5][1]);
        nodeGrid[7][1].setParent(nodeGrid[6][1]);
        nodeGrid[7][2].setParent(nodeGrid[7][1]);
        nodeGrid[7][3].setParent(nodeGrid[7][2]);
        nodeGrid[7][4].setParent(nodeGrid[7][3]);
        nodeGrid[7][5].setParent(nodeGrid[7][4]);
        nodeGrid[7][6].setParent(nodeGrid[7][5]);
        nodeGrid[7][7].setParent(nodeGrid[7][6]);
        nodeGrid[7][8].setParent(nodeGrid[7][7]);
        nodeGrid[7][9].setParent(nodeGrid[7][8]);
        nodeGrid[7][10].setParent(nodeGrid[7][9]);
        nodeGrid[7][11].setParent(nodeGrid[7][10]);
        nodeGrid[6][11].setParent(nodeGrid[7][11]);
        nodeGrid[5][11].setParent(nodeGrid[6][11]);
        nodeGrid[5][12].setParent(nodeGrid[5][11]);
        nodeGrid[5][13].setParent(nodeGrid[5][12]);
        nodeGrid[4][13].setParent(nodeGrid[5][13]);
        nodeGrid[3][13].setParent(nodeGrid[4][13]);
        nodeGrid[3][14].setParent(nodeGrid[3][13]);
        nodeGrid[3][15].setParent(nodeGrid[3][14]);
        nodeGrid[4][15].setParent(nodeGrid[3][15]);
        nodeGrid[5][15].setParent(nodeGrid[4][15]);
        nodeGrid[5][16].setParent(nodeGrid[5][15]);
        nodeGrid[5][17].setParent(nodeGrid[5][16]);
        nodeGrid[6][17].setParent(nodeGrid[5][17]);
        nodeGrid[7][17].setParent(nodeGrid[6][17]);
        nodeGrid[8][17].setParent(nodeGrid[7][17]);

        assertEquals(
                List.of(
                        new Cell(0, 1, Cell.Type.ESCAPE), new Cell(1, 1, Cell.Type.ESCAPE),
                        new Cell(2, 1, Cell.Type.ESCAPE), new Cell(3, 1, Cell.Type.ESCAPE),
                        new Cell(4, 1, Cell.Type.ESCAPE), new Cell(5, 1, Cell.Type.ESCAPE),
                        new Cell(6, 1, Cell.Type.ESCAPE), new Cell(7, 1, Cell.Type.ESCAPE),
                        new Cell(7, 2, Cell.Type.ESCAPE), new Cell(7, 3, Cell.Type.ESCAPE),
                        new Cell(7, 4, Cell.Type.ESCAPE), new Cell(7, 5, Cell.Type.ESCAPE),
                        new Cell(7, 6, Cell.Type.ESCAPE), new Cell(7, 7, Cell.Type.ESCAPE),
                        new Cell(7, 8, Cell.Type.ESCAPE), new Cell(7, 9, Cell.Type.ESCAPE),
                        new Cell(7, 10, Cell.Type.ESCAPE), new Cell(7, 11, Cell.Type.ESCAPE),
                        new Cell(6, 11, Cell.Type.ESCAPE), new Cell(5, 11, Cell.Type.ESCAPE),
                        new Cell(5, 12, Cell.Type.ESCAPE), new Cell(5, 13, Cell.Type.ESCAPE),
                        new Cell(4, 13, Cell.Type.ESCAPE), new Cell(3, 13, Cell.Type.ESCAPE),
                        new Cell(3, 14, Cell.Type.ESCAPE), new Cell(3, 15, Cell.Type.ESCAPE),
                        new Cell(4, 15, Cell.Type.ESCAPE), new Cell(5, 15, Cell.Type.ESCAPE),
                        new Cell(5, 16, Cell.Type.ESCAPE), new Cell(5, 17, Cell.Type.ESCAPE),
                        new Cell(6, 17, Cell.Type.ESCAPE), new Cell(7, 17, Cell.Type.ESCAPE),
                        new Cell(8, 17, Cell.Type.ESCAPE)
                ),
                nodeGrid[8][17].reconstructPath()
        );
    }
}
