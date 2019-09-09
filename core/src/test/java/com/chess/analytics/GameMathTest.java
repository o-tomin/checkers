package com.chess.analytics;

import com.chess.analytics.GameMath;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.*;

public class GameMathTest {

    private static final int[] FIRST_CELL = {4, 4};
    private static final int[] SECOND_CELL = {3, 5};
    private static final int[] THIRD_CELL = {2, 6};
    private static final int[] SOME_CELL = {2, 1};

    private static final int[] ONE_ONE_CELL = {1, 1};
    private static final int[] SEVEN_SEVEN_CELL = {7, 7};
    private static final int[] SEVEN_ONE_CELL = {7, 1};
    private static final int[] ONE_SEVEN_CELL = {1, 7};

    @Test
    public void calculateNextCellIncrementalOrderTest() {
        int[] nextCell = GameMath.calculateNextCellBasedOnTwoCells(FIRST_CELL, SECOND_CELL);
        assertEquals(nextCell, THIRD_CELL);
    }

    @Test
    public void calculateNextCellDecrementalOrderTest() {
        int[] nextCell = GameMath.calculateNextCellBasedOnTwoCells(THIRD_CELL, SECOND_CELL);
        assertEquals(nextCell, FIRST_CELL);
    }

    @Test
    public void calculateInBetweenCellsTest() {
        int[][] cells = GameMath.calculateInBetweenCells(ONE_ONE_CELL, SEVEN_SEVEN_CELL);
        assertTrue(Arrays.deepEquals(cells, array("{2,2}{3,3}{4,4}{5,5}{6,6}")));

        cells = GameMath.calculateInBetweenCells(SEVEN_ONE_CELL, ONE_SEVEN_CELL);
        assertTrue(Arrays.deepEquals(cells, array("{6,2}{5,3}{4,4}{3,5}{2,6}")));

        cells = GameMath.calculateInBetweenCells(ONE_SEVEN_CELL, SEVEN_ONE_CELL);
        assertTrue(Arrays.deepEquals(cells, array("{2,6}{3,5}{4,4}{5,3}{6,2}")));
    }

    @Test
    public void isOnOneDiagonalTrueTest() {
        assertTrue(GameMath.isOnOneDiagonal(FIRST_CELL, THIRD_CELL));
    }

    @Test
    public void isOnOneDiagonalFalseTest() {
        assertFalse(GameMath.isOnOneDiagonal(SOME_CELL, THIRD_CELL));
    }

    private static int[][] array(String arrayString) {
        String[] arrays = arrayString.split("\\{");
        int[][] arraysInt = new int[arrays.length - 1][];
        for (int i = 1; i < arrays.length; i++) {
            String[] splitted = arrays[i].split(",");
            arraysInt[i - 1] = new int[] {Integer.parseInt(splitted[0].trim()),
                    Integer.parseInt(splitted[1].substring(0, 1))};
        }
        return arraysInt;
    }
}
