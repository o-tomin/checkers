package com.chess.analytics;

public class GameMath {

    // public methods
    public static int[] calculateNextCellBasedOnTwoCells(int[] firstCell, int[] secondCell) {
        int[] ort = ortVector(vector(firstCell, secondCell));
        return new int[]{secondCell[0] + ort[0], secondCell[1] + ort[1]};
    }

    public static int[] calculateNextCellBasedOnOrtVector(int[] cell, int[] ort) {
        return new int[]{cell[0] + ort[0], cell[1] + ort[1]};
    }

    public static int[][] calculateInBetweenCells(int[] from, int[] to) {
        int[][] inBetweenCells = null;
        int[] vector = vector(from, to);
        if (isDiagonalVector(vector)) {
            int inBetweenCellsLength = Math.abs(vector[0]) - 1;
            inBetweenCells = new int[inBetweenCellsLength][2];
            int[] ort = ortVector(vector);
            int[] next = from;
            for (int i = 0; i < inBetweenCellsLength; i++) {
                    next = inBetweenCells[i] = calculateNextCellBasedOnOrtVector(next, ort);
            }
        }
        return inBetweenCells;
    }

    // public boolean methods
    public static boolean isOnOneDiagonal(int[] first, int[] second) {
        return isDiagonalVector(vector(first, second));
    }

    private static boolean isDiagonalVector(int[] vector) {
        return Math.abs(vector[0]) == Math.abs(vector[1]);
    }

    // private methods
    private static int[] vector(int[] pointA, int[] pointB) {
        return new int[] {pointB[0] - pointA[0], pointB[1] - pointA[1]};
    }

    private static int[] ortVector(int[] vector) {
        int lineStep = vector[0] > 0 ? 1 : -1;
        int cellStep = vector[1] > 0 ? 1 : -1;
        return new int[] {lineStep, cellStep};
    }


}
