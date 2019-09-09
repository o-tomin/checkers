package com.chess.analytics;

import com.chess.entity.Cell;
import com.chess.entity.Field;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

public class FieldStateAnalyzerTest {

    private Field field;
    private FieldStateAnalyzer analyzer;

    @BeforeMethod
    public void init() {
        this.field = spy(new Field());
        this.analyzer = new FieldStateAnalyzer(field);
    }

    @Test
    public void updateDataForAnalysisTest() {
        doReturn(true).when(field).isWhiteFigure(any(Cell.class));
        analyzer.updateDataForAnalysis();
        assertEquals(analyzer.countWhites(), (8 * 8));

        doReturn(false).when(field).isWhiteFigure(any(Cell.class));
        doReturn(true).when(field).isWhiteQueen(any(Cell.class));
        analyzer.updateDataForAnalysis();
        assertEquals(analyzer.countWhites(), (8 * 8));

        doReturn(false).when(field).isWhiteFigure(any(Cell.class));
        doReturn(false).when(field).isWhiteQueen(any(Cell.class));
        doReturn(true).when(field).isBlackFigure(any(Cell.class));
        analyzer.updateDataForAnalysis();
        assertEquals(analyzer.countBlacks(), (8 * 8));

        doReturn(false).when(field).isWhiteFigure(any(Cell.class));
        doReturn(false).when(field).isWhiteQueen(any(Cell.class));
        doReturn(false).when(field).isBlackFigure(any(Cell.class));
        doReturn(true).when(field).isBlackQueen(any(Cell.class));
        analyzer.updateDataForAnalysis();
        assertEquals(analyzer.countBlacks(), (8 * 8));
    }

    private static final byte[][] UPDATE_WHITES_POSSIBLE_ATTACKS_TEST_FIELD = new byte[][] {
           //1  2  3  4  5  6  7  8
            {1, 0, 1, 0, 1, 0, 1, 0}, //A
            {0, 1, 0, 1, 0, 1, 0, 1}, //B
            {1, 0, 4, 0, 4, 0, 5, 0}, //C
            {0, 2, 0, 2, 0, 1, 0, 1}, //D
            {1, 0, 2, 0, 3, 0, 3, 0}, //E
            {0, 1, 0, 5, 0, 5, 0, 1}, //F
            {1, 0, 1, 0, 1, 0, 2, 0}, //G
            {0, 1, 0, 1, 0, 1, 0, 1}};//H
           //1  2  3  4  5  6  7  8

    @Test
    public void updateWhitesPossibleAttacksTest() throws Exception {
        field.magicUpdate(UPDATE_WHITES_POSSIBLE_ATTACKS_TEST_FIELD);
        analyzer.updateDataForAnalysis();
        analyzer.updateWhitesPossibleAttacks();
        assertEquals(analyzer.getWhitesPossibleAttacks(),
                new HashMap<Cell, List<Cell>>() {
                    {
                        put(Cell.fromString("D, 2"), Collections.singletonList(Cell.fromString("C, 3")));
                        put(Cell.fromString("E, 3"), Collections.singletonList(Cell.fromString("F, 4")));
                        put(Cell.fromString("D, 4"), Arrays.asList(Cell.fromString("C, 3"), Cell.fromString("C, 5")));
                        put(Cell.fromString("E, 5"), Arrays.asList(Cell.fromString("C, 7"), Cell.fromString("F, 4")));
                        put(Cell.fromString("E, 7"), Arrays.asList(Cell.fromString("C, 5"), Cell.fromString("F, 6")));
                    }
                });
    }

    private static final byte[][] UPDATE_BLACKS_POSSIBLE_ATTACKS_TEST_FIELD = new byte[][] {
           //1  2  3  4  5  6  7  8
            {4, 0, 1, 0, 4, 0, 1, 0}, //A
            {0, 2, 0, 2, 0, 3, 0, 1}, //B
            {4, 0, 1, 0, 4, 0, 1, 0}, //C
            {0, 2, 0, 3, 0, 2, 0, 1}, //D
            {4, 0, 1, 0, 5, 0, 1, 0}, //E
            {0, 3, 0, 1, 0, 2, 0, 5}, //F
            {4, 0, 1, 0, 1, 0, 1, 0}, //G
            {0, 2, 0, 1, 0, 1, 0, 5}};//H
           //1  2  3  4  5  6  7  8

    @Test
    public void updateBlacksPossibleAttacksTest() throws Exception {
        field.magicUpdate(UPDATE_BLACKS_POSSIBLE_ATTACKS_TEST_FIELD);
        analyzer.updateDataForAnalysis();
        analyzer.updateBlacksPossibleAttacks();
        assertEquals(analyzer.getBlacksPossibleAttacks(),
                new HashMap<Cell, List<Cell>>() {
                    {
                        put(Cell.fromString("A, 1"), Arrays.asList(
                                Cell.fromString("B, 2")));
                        put(Cell.fromString("C, 1"), Arrays.asList(
                                Cell.fromString("B, 2"), Cell.fromString("D, 2")));
                        put(Cell.fromString("E, 1"), Arrays.asList(
                                Cell.fromString("D, 2"), Cell.fromString("F, 2")));
                        put(Cell.fromString("G, 1"), Arrays.asList(
                                Cell.fromString("F, 2")));
                        put(Cell.fromString("A, 5"), Arrays.asList(
                                Cell.fromString("B, 4"), Cell.fromString("B, 6")));
                        put(Cell.fromString("C, 5"), Arrays.asList(
                                Cell.fromString("B, 4"), Cell.fromString("D, 6"),
                                Cell.fromString("B, 6"), Cell.fromString("D, 4")));
                        put(Cell.fromString("E, 5"), Arrays.asList(
                                Cell.fromString("D, 6"), Cell.fromString("F, 6"),
                                Cell.fromString("D, 4")));
                    }
                });
    }
}
