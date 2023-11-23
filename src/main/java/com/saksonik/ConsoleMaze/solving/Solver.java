package com.saksonik.ConsoleMaze.solving;

import com.saksonik.ConsoleMaze.model.Cell;
import com.saksonik.ConsoleMaze.model.Maze;
import java.util.List;

public interface Solver {
    List<Cell> solve(Maze maze, Cell entrance, Cell exit);
}
