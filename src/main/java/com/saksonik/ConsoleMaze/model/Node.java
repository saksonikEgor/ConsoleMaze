package com.saksonik.ConsoleMaze.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Node {
    private final int row;
    private final int column;
    private final boolean isWall;
    private Node parent;

    public Node(int row, int column, boolean isWall) {
        this.row = row;
        this.column = column;
        this.isWall = isWall;
        parent = this;
    }

    public static Node[][] createGridOfNodes(Cell[][] mazeGrid) {
        Node[][] gridOfNodes = new Node[mazeGrid.length][mazeGrid[0].length];

        for (int i = 0; i < gridOfNodes.length; i++) {
            for (int j = 0; j < gridOfNodes[0].length; j++) {
                gridOfNodes[i][j] = new Node(i, j, mazeGrid[i][j].isWall());
            }
        }
        return gridOfNodes;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isWall() {
        return isWall;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Cell toCell() {
        return new Cell(row, column, Cell.Type.ESCAPE);
    }

    public List<Cell> reconstructPath() {
        List<Cell> path = new LinkedList<>();
        Node node = this;
        path.add(node.toCell());

        while (node.getParent() != node) {
            Node parentNode = node.getParent();

            path.addFirst(parentNode.toCell());
            node = parentNode;
        }
        return path;
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
