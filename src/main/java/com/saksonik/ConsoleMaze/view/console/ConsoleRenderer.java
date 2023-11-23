package com.saksonik.ConsoleMaze.view.console;

import com.saksonik.ConsoleMaze.model.Cell;
import com.saksonik.ConsoleMaze.model.Maze;
import com.saksonik.ConsoleMaze.properties.ApplicationProperties;
import com.saksonik.ConsoleMaze.view.Renderer;

import java.util.List;

public class ConsoleRenderer implements Renderer {
    @Override
    public String render(Maze maze) {
        return mazeToString(maze, false);
    }

    @Override
    public String render(Maze maze, List<Cell> path) {
        maze.putCells(path);
        return mazeToString(maze, true);
    }

    private String mazeToString(Maze maze, boolean showEscape) {
        StringBuilder sb = new StringBuilder();

        for (Cell[] row : maze.getGrid()) {
            for (Cell cell : row) {
                if (cell.isWall()) {
                    sb.append(ApplicationProperties.WALL_STRING);
                } else if (showEscape && cell.isEscape()) {
                    sb.append(ApplicationProperties.PATH_STRING);
                } else {
                    sb.append(ApplicationProperties.PASSAGE_STRING);
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
