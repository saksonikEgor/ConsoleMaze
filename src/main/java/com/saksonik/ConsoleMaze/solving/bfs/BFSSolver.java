package com.saksonik.ConsoleMaze.solving.bfs;

import com.saksonik.ConsoleMaze.model.Cell;
import com.saksonik.ConsoleMaze.model.Maze;
import com.saksonik.ConsoleMaze.model.Node;
import com.saksonik.ConsoleMaze.properties.ApplicationProperties;
import com.saksonik.ConsoleMaze.solving.Solver;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class BFSSolver implements Solver {
    private final Queue<Node> queue;
    private final Set<Node> visited;
    private int height;
    private int width;
    private Node[][] grid;
    private Node start;
    private Node end;

    public BFSSolver() {
        queue = new ArrayDeque<>();
        visited = new HashSet<>();
    }

    @Override
    public List<Cell> solve(Maze maze, Cell entrance, Cell exit) {
        Cell[][] mazeGrid = maze.getGrid();

        this.height = mazeGrid.length;
        this.width = mazeGrid[0].length;
        grid = Node.createGridOfNodes(mazeGrid);

        this.start = new Node(entrance.row(), entrance.column(), false);
        this.end = new Node(exit.row(), exit.column(), false);

        return findPath();
    }

    private List<Cell> findPath() {
        Node cur = start;
        queue.add(cur);
        visited.add(cur);

        while (!queue.isEmpty()) {
            cur = queue.poll();

            if (cur.equals(end)) {
                return cur.reconstructPath();
            }

            for (Node neighbor : getAllNeighbors(cur)) {
                neighbor.setParent(cur);

                queue.add(neighbor);
                visited.add(neighbor);
            }
        }
        return Collections.emptyList();
    }

    private List<Node> getAllNeighbors(Node cur) {
        List<Node> neighbors = new ArrayList<>();

        for (int[] delta : ApplicationProperties.DELTAS) {
            int row = cur.getRow() + delta[0];
            int column = cur.getColumn() + delta[1];

            if (inBounds(row, column)) {
                Node node = grid[row][column];

                if (!node.isWall() && !visited.contains(node)) {
                    neighbors.add(node);
                }
            }
        }
        return neighbors;
    }

    private boolean inBounds(int row, int column) {
        return row >= 0 && row < height
                && column >= 0 && column < width;
    }
}
