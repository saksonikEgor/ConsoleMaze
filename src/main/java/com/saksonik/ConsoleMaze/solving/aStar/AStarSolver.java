package com.saksonik.ConsoleMaze.solving.aStar;

import com.saksonik.ConsoleMaze.model.Cell;
import com.saksonik.ConsoleMaze.model.Maze;
import com.saksonik.ConsoleMaze.properties.ApplicationProperties;
import com.saksonik.ConsoleMaze.solving.Solver;

import java.util.*;

import static java.util.Comparator.comparingInt;

public class AStarSolver implements Solver {
    private final PriorityQueue<Node> unvisited;
    private final Set<Node> visited;
    private int height;
    private int width;
    private Node[][] grid;
    private Node start;
    private Node end;

    public AStarSolver() {
        unvisited = new PriorityQueue<>(comparingInt(Node::getTotalCost));
        visited = new HashSet<>();
    }

    @Override
    public List<Cell> solve(Maze maze, Cell entrance, Cell exit) {
        Cell[][] mazeGrid = maze.getGrid();

        this.height = mazeGrid.length;
        this.width = mazeGrid[0].length;
        this.grid = new Node[height][width];

        this.start = new Node(entrance.row(), entrance.column(), false);
        this.end = new Node(exit.row(), exit.column(), false);

        createNodes(mazeGrid);
        return findPath();
    }

    private void createNodes(Cell[][] mazeGrid) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Node node = new Node(i, j, mazeGrid[i][j].isWall());
                node.calcHeuristicTo(end);

                this.grid[i][j] = node;
            }
        }
    }

    private List<Cell> findPath() {
        unvisited.add(start);

        while (!unvisited.isEmpty()) {
            Node cur = unvisited.poll();

            if (cur.equals(end)) {
                return reconstructPath(cur);
            }

            visited.add(cur);
            updateNeighbors(cur);
        }
        return Collections.emptyList();
    }

    private List<Cell> reconstructPath(Node cur) {
        List<Cell> path = new LinkedList<>();
        Node node = cur;
        path.add(toCell(node));

        while (node.getParent() != node) {
            Node parent = node.getParent();

            path.addFirst(toCell(parent));
            node = parent;
        }
        return path;
    }

    private boolean inBounds(int row, int column) {
        return row >= 0 && row < height
                && column >= 0 && column < width;
    }

    private Cell toCell(Node node) {
        return new Cell(node.getRow(), node.getColumn(), Cell.Type.ESCAPE);
    }

    private void updateNeighbors(Node cur) {
        for (int[] delta : ApplicationProperties.DELTAS) {
            int row = cur.getRow() + delta[0];
            int column = cur.getColumn() + delta[1];

            if (inBounds(row, column)) {
                Node node = grid[row][column];

                if (!node.isWall() && !visited.contains(node)) {
                    compareNodeAndCur(node, cur);
                }
            }
        }
    }

    private void compareNodeAndCur(Node node, Node cur) {
        if (unvisited.contains(node)) {
            if (node.hasBetterPath(cur)) {
                unvisited.remove(node);
            } else {
                return;
            }
        }
        node.updatePath(cur);
        unvisited.add(node);
    }

    static class Node {
        private static final int EDGE_COST = 1;
        private final int row;
        private final int column;
        private final boolean isWall;
        private Node parent;
        private int costFromStart;
        private int estimatedCostToTheEnd;
        private int totalCost;

        Node(int row, int column, boolean isWall) {
            this.row = row;
            this.column = column;
            this.isWall = isWall;
            parent = this;
        }

        int getRow() {
            return row;
        }

        int getColumn() {
            return column;
        }

        boolean isWall() {
            return isWall;
        }

        Node getParent() {
            return parent;
        }

        int getTotalCost() {
            return totalCost;
        }

        void calcHeuristicTo(Node node) {
            this.estimatedCostToTheEnd = Math.abs(node.row - this.row)
                    + Math.abs(node.column - this.column);
        }

        boolean hasBetterPath(Node node) {
            return node.costFromStart + EDGE_COST < this.costFromStart;
        }

        void updatePath(Node node) {
            this.parent = node;
            this.costFromStart = node.costFromStart + EDGE_COST;
            totalCost = costFromStart + estimatedCostToTheEnd;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Node node = (Node) o;
            return row == node.row
                    && column == node.column
                    && isWall == node.isWall;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, column, isWall);
        }
    }
}
