package com.saksonik.ConsoleMaze.generation.bfs;

import com.saksonik.ConsoleMaze.generation.Generator;
import com.saksonik.ConsoleMaze.model.AlternatingCell;
import com.saksonik.ConsoleMaze.model.Edge;
import com.saksonik.ConsoleMaze.model.Maze;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Random;

public class BFSGenerator implements Generator {
    private final Random random;
    private final Queue<AlternatingCell> queue;

    public BFSGenerator(Random random) {
        this.random = random;
        queue = new ArrayDeque<>();
    }

    @Override
    public Maze generate(int height, int width) {
        Maze maze = new Maze(height, width);

        int bfsHeight = (height - 1) / 2;
        int bfsWidth = (width - 1) / 2;

        List<AlternatingCell> alternatingCells = AlternatingCell.initAlternatingCells(bfsWidth, bfsHeight);
        AlternatingCell.shuffleNeighborsOfAlternatingCells(alternatingCells, random);

        maze.putSpanningTree(buildRandomSpanningTree(alternatingCells.getFirst()), bfsWidth);
        return maze;
    }

    private List<Edge> buildRandomSpanningTree(AlternatingCell firstCell) {
        List<Edge> edges = new ArrayList<>();
        AlternatingCell cur = firstCell;

        queue.add(cur);
        cur.makeVisited();

        while (!queue.isEmpty()) {
            Optional<AlternatingCell> neighborOpt = cur.getFirstUnvisitedNeighbor();

            if (neighborOpt.isPresent()) {
                AlternatingCell neighbor = neighborOpt.get();
                neighbor.makeVisited();

                edges.add(new Edge(cur.getCellId(), neighbor.getCellId()));

                cur = neighbor;
                queue.add(cur);
            } else {
                cur = queue.poll();
            }
        }
        return edges;
    }
}
