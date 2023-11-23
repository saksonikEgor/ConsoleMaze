package com.saksonik.ConsoleMaze.generation;

import com.saksonik.ConsoleMaze.model.Maze;

public interface Generator {
    Maze generate(int height, int width);
}
