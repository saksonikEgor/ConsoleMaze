package com.saksonik.ConsoleMaze.generation.dfs;

import com.saksonik.ConsoleMaze.generation.Generator;
import com.saksonik.ConsoleMaze.model.AlternatingCell;
import com.saksonik.ConsoleMaze.model.Edge;
import com.saksonik.ConsoleMaze.model.Maze;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class DFSGenerator implements Generator {
    private final Random random;
    private final Deque<AlternatingCell> stack;

    public DFSGenerator(Random random) {
        this.random = random;
        stack = new ArrayDeque<>();
    }

    @Override
    public Maze generate(int height, int width) {
        Maze maze = new Maze(height, width);

        int dfsHeight = (height - 1) / 2;
        int dfsWidth = (width - 1) / 2;

        List<AlternatingCell> alternatingCells = AlternatingCell.initAlternatingCells(dfsWidth, dfsHeight);
        AlternatingCell.shuffleNeighborsOfAlternatingCells(alternatingCells, random);

        maze.putSpanningTree(buildRandomSpanningTree(alternatingCells.getFirst()), dfsWidth);
        return maze;
    }

    private List<Edge> buildRandomSpanningTree(AlternatingCell firstCell) {
        List<Edge> edges = new ArrayList<>();
        AlternatingCell cur = firstCell;

        stack.add(cur);
        cur.makeVisited();

        while (!stack.isEmpty()) {
            Optional<AlternatingCell> neighborOpt = cur.getFirstUnvisitedNeighbor();

            if (neighborOpt.isPresent()) {
                AlternatingCell neighbor = neighborOpt.get();
                neighbor.makeVisited();

                edges.add(new Edge(cur.getCellId(), neighbor.getCellId()));

                cur = neighbor;
                stack.push(cur);
            } else {
                cur = stack.poll();
            }
        }
        return edges;
    }
}
