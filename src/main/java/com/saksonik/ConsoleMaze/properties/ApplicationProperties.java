package com.saksonik.ConsoleMaze.properties;

import com.saksonik.ConsoleMaze.generation.Generator;
import com.saksonik.ConsoleMaze.generation.bfs.BFSGenerator;
import com.saksonik.ConsoleMaze.generation.dfs.DFSGenerator;
import com.saksonik.ConsoleMaze.generation.kruskal.KruskalGenerator;
import com.saksonik.ConsoleMaze.solving.Solver;
import com.saksonik.ConsoleMaze.solving.aStar.AStarSolver;
import com.saksonik.ConsoleMaze.solving.bfs.BFSSolver;
import com.saksonik.ConsoleMaze.solving.dfs.DFSSolver;
import java.util.Random;

public class ApplicationProperties {
    public static final String WALL_STRING = "██";
    public static final String PASSAGE_STRING = "  ";
    public static final String PATH_STRING = "▓▓";
    public static final String SELECT_A_MAZE_GENERATION_ALGORITHM_MESSAGE =
            "=== Choose a maze generation algorithm ===";
    public static final String SELECT_A_MAZE_SOLVING_ALGORITHM_MESSAGE = "=== Choose a maze solving algorithm ===";
    public static final String ENTER_COMMAND_TO_EXIT_MESSAGE = "0. Exit";
    public static final String SUGGESTION_TO_SELECT_A_MAZE_SIZE_MESSAGE =
            "=== Enter the size of the new maze in the [size] or [height width] format "
                    + "for example: 8 12 ===";
    public static final String FAREWELL_MESSAGE = "Bye!";
    public static final String MAZE_ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE = "Both the height and the width "
            + "of the maze must be at least 3";
    public static final String INVALID_MAZE_SIZE_MESSAGE = "Cannot generate a maze. Invalid size. Please try again";
    public static final String INVALID_NUMBER_INPUT_MESSAGE = "Incorrect option. Please try again";
    public static final int[][] DELTAS = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};
    public static final int SIZE_LOWER_BOUND = 3;

    private ApplicationProperties() {
    }

    public enum GenerationAlgorithm {
        Kruskal,
        DFS,
        BFS;

        public Generator getGenerator(Random random) {
            return switch (this) {
                case Kruskal -> new KruskalGenerator(random);
                case DFS -> new DFSGenerator(random);
                case BFS -> new BFSGenerator(random);
            };
        }
    }

    public enum SolvingAlgorithm {
        AStar,
        DFS,
        BFS;

        public Solver getSolver() {
            return switch (this) {
                case AStar -> new AStarSolver();
                case DFS -> new DFSSolver();
                case BFS -> new BFSSolver();
            };
        }
    }

}
