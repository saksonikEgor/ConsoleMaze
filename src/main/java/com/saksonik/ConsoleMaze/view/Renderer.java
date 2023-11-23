package com.saksonik.ConsoleMaze.view;

import com.saksonik.ConsoleMaze.model.Cell;
import com.saksonik.ConsoleMaze.model.Maze;
import java.util.List;

public interface Renderer {
    String render(Maze maze);

    String render(Maze maze, List<Cell> path);
}
