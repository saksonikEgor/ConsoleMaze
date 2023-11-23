package com.saksonik.ConsoleMaze.solving.dfs;

import com.saksonik.ConsoleMaze.model.Cell;
import com.saksonik.ConsoleMaze.model.Maze;
import com.saksonik.ConsoleMaze.model.Node;
import com.saksonik.ConsoleMaze.properties.ApplicationProperties;
import com.saksonik.ConsoleMaze.solving.Solver;

import java.util.*;

public class DFSSolver implements Solver {
    private final Deque<Node> stack;
    private final Set<Node> visited;
    private int height;
    private int width;
    private Node[][] grid;
    private Node start;
    private Node end;

    public DFSSolver() {
        stack = new ArrayDeque<>();
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
        stack.push(cur);

        while (!stack.isEmpty()) {
            if (cur.equals(end)) {
                return cur.reconstructPath();
            }

            visited.add(cur);
            Optional<Node> neighborOpt = getFirstNeighbor(cur);

            if (neighborOpt.isPresent()) {
                Node neighbor = neighborOpt.get();
                neighbor.setParent(cur);
                cur = neighbor;

                stack.push(cur);
            } else {
                stack.poll();
                cur = stack.peek();
            }
        }
        return Collections.emptyList();
    }

    private Optional<Node> getFirstNeighbor(Node cur) {
        for (int[] delta : ApplicationProperties.DELTAS) {
            int row = cur.getRow() + delta[0];
            int column = cur.getColumn() + delta[1];

            if (inBounds(row, column)) {
                Node node = grid[row][column];

                if (!node.isWall() && !visited.contains(node)) {
                    return Optional.of(node);
                }
            }
        }
        return Optional.empty();
    }

    private boolean inBounds(int row, int column) {
        return row >= 0 && row < height
                && column >= 0 && column < width;
    }
}
