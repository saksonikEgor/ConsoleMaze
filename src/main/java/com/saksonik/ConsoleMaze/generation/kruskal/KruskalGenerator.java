package com.saksonik.ConsoleMaze.generation.kruskal;

import com.saksonik.ConsoleMaze.generation.Generator;
import com.saksonik.ConsoleMaze.model.Edge;
import com.saksonik.ConsoleMaze.model.Maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

public class KruskalGenerator implements Generator {
    private final Random random;
    private int height;
    private int width;

    public KruskalGenerator(Random random) {
        this.random = random;
    }

    @Override
    public Maze generate(int height, int width) {
        Maze maze = new Maze(height, width);

        this.height = (height - 1) / 2;
        this.width = (width - 1) / 2;

        List<Edge> edges = createEdges();
        Collections.shuffle(edges, random);

        maze.putSpanningTree(buildRandomSpanningTree(edges), this.width);
        return maze;
    }

    private List<Edge> createEdges() {
        List<Edge> edges = new ArrayList<>();

        for (int column = 1; column < width; column++) {
            edges.add(new Edge(
                    toIndex(0, column),
                    toIndex(0, column - 1)
            ));
        }

        for (int row = 1; row < height; row++) {
            edges.add(new Edge(
                    toIndex(row, 0),
                    toIndex(row - 1, 0)
            ));
        }

        for (int row = 1; row < height; row++) {
            for (int column = 1; column < width; column++) {
                edges.add(new Edge(
                        toIndex(row, column),
                        toIndex(row, column - 1)
                ));
                edges.add(new Edge(
                        toIndex(row, column),
                        toIndex(row - 1, column)
                ));
            }
        }
        return edges;
    }

    private int toIndex(int row, int column) {
        return row * width + column;
    }

    private List<Edge> buildRandomSpanningTree(List<Edge> edges) {
        DisjointSet disjointSets = new DisjointSet(width * height);

        return edges
                .stream()
                .filter(edge -> connects(edge, disjointSets))
                .collect(toList());
    }

    private boolean connects(Edge edge, DisjointSet disjointSet) {
        return disjointSet.union(edge.firstCell(), edge.secondCell());
    }

    static class DisjointSet {
        private final int[] parent;
        private final int[] rank;

        DisjointSet(int size) {
            parent = new int[size];
            rank = new int[size];
            range(0, size).forEach(this::makeSet);
        }

        private void makeSet(int i) {
            parent[i] = i;
            rank[i] = 0;
        }

        public int find(int i) {
            if (i != parent[i]) {
                parent[i] = find(parent[i]);
            }
            return parent[i];
        }

        public boolean union(int i, int j) {
            int iRoot = find(i);
            int jRoot = find(j);

            if (iRoot == jRoot) {
                return false;
            }

            if (rank[iRoot] < rank[jRoot]) {
                parent[iRoot] = jRoot;
            } else {
                parent[jRoot] = iRoot;
                if (rank[iRoot] == rank[jRoot]) {
                    rank[iRoot]++;
                }
            }
            return true;
        }
    }
}
