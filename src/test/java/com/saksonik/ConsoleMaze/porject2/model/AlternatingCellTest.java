package com.saksonik.ConsoleMaze.porject2.model;

import com.saksonik.ConsoleMaze.model.AlternatingCell;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlternatingCellTest {
    static List<AlternatingCell> createAlternatingCells(int height, int width) {
        List<AlternatingCell> alternatingCells = new ArrayList<>();
        IntStream.range(0, height * width).forEach(idx -> alternatingCells.add(new AlternatingCell(idx)));
        return alternatingCells;
    }

    @Test
    @DisplayName("Инициализация AlternatingCells")
    void initAlternatingCells() {
        List<AlternatingCell> expected = createAlternatingCells(2, 2);
        expected.get(0).addNeighbor(expected.get(1));
        expected.get(0).addNeighbor(expected.get(2));
        expected.get(1).addNeighbor(expected.get(3));
        expected.get(2).addNeighbor(expected.get(3));
        assertThat(expected).hasSameElementsAs(AlternatingCell.initAlternatingCells(2, 2));

        expected = createAlternatingCells(3, 4);
        expected.get(0).addNeighbor(expected.get(1));
        expected.get(0).addNeighbor(expected.get(4));
        expected.get(1).addNeighbor(expected.get(2));
        expected.get(1).addNeighbor(expected.get(5));
        expected.get(2).addNeighbor(expected.get(3));
        expected.get(2).addNeighbor(expected.get(6));
        expected.get(3).addNeighbor(expected.get(7));
        expected.get(4).addNeighbor(expected.get(5));
        expected.get(4).addNeighbor(expected.get(8));
        expected.get(5).addNeighbor(expected.get(6));
        expected.get(5).addNeighbor(expected.get(9));
        expected.get(6).addNeighbor(expected.get(7));
        expected.get(6).addNeighbor(expected.get(10));
        expected.get(7).addNeighbor(expected.get(11));
        expected.get(8).addNeighbor(expected.get(9));
        expected.get(9).addNeighbor(expected.get(10));
        expected.get(10).addNeighbor(expected.get(11));
        assertThat(expected).hasSameElementsAs(AlternatingCell.initAlternatingCells(4, 3));
    }

    @Test
    @DisplayName("Перемешывание соседей у AlternatingCells")
    void shuffleNeighborsOfAlternatingCells() {
        List<AlternatingCell> expected = createAlternatingCells(2, 2);
        expected.get(0).addNeighbor(expected.get(1));
        expected.get(0).addNeighbor(expected.get(2));
        expected.get(1).addNeighbor(expected.get(3));
        expected.get(2).addNeighbor(expected.get(3));
        List<AlternatingCell> actual = expected;

        Collections.shuffle(expected, new Random(1));
        AlternatingCell.shuffleNeighborsOfAlternatingCells(actual, new Random(1));

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Получение первого непосещенного соседа")
    void getFirstUnvisitedNeighbor() {
        List<AlternatingCell> alternatingCells = createAlternatingCells(2, 2);
        alternatingCells.get(0).addNeighbor(alternatingCells.get(1));
        alternatingCells.get(0).addNeighbor(alternatingCells.get(3));
        alternatingCells.get(0).addNeighbor(alternatingCells.get(2));
        alternatingCells.get(1).addNeighbor(alternatingCells.get(3));
        alternatingCells.get(2).addNeighbor(alternatingCells.get(3));

        alternatingCells.get(1).makeVisited();
        assertEquals(alternatingCells.get(3), alternatingCells.get(0).getFirstUnvisitedNeighbor().get());

        alternatingCells.get(3).makeVisited();
        assertEquals(alternatingCells.get(2), alternatingCells.get(0).getFirstUnvisitedNeighbor().get());
    }
}
