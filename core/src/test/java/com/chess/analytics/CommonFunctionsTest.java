package com.chess.analytics;

import com.chess.entity.Cell;
import com.chess.entity.Field;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.assertEquals;


public class CommonFunctionsTest {
    private static final Cell ZERO_ZERO_CELL = new Cell(0,0);
    private static final Cell ONE_ONE_CELL = new Cell(1, 1);
    private static final Cell TWO_TWO_CELL = new Cell(2,2);

    private static final Map<Cell, List<Cell>> CELL_ASSOCIATION_MAP = new HashMap<Cell, List<Cell>>() {
        {
            put(ZERO_ZERO_CELL, Arrays.asList(ZERO_ZERO_CELL));
            put(ONE_ONE_CELL, Arrays.asList(ZERO_ZERO_CELL, ZERO_ZERO_CELL));
            put(TWO_TWO_CELL, Arrays.asList(ZERO_ZERO_CELL, ZERO_ZERO_CELL, ZERO_ZERO_CELL));
        }
    };

    private Field field;
    private FieldStateAnalyzer analyzer;

    @BeforeMethod
    public void init() {
        field = new Field();
        analyzer = new FieldStateAnalyzer(field);
    }

    //White Figure possible game situations
    private static final byte[][] WHITE_FIGURE_CAN_GO_LEFT_FORWARD_FIELD = {
            {0, 1},
            {2, 0},
            {0, 3}};

    @Test
    public void takeStockOfFigurePossibleSteps_WhiteFigureCanGoLeftForwardTest() throws Exception {
        Map<Cell, List<Cell>> figureToPossibleStepCells = new HashMap<>();
        field.magicUpdate(WHITE_FIGURE_CAN_GO_LEFT_FORWARD_FIELD);
        Cell whiteFigure = new Cell(1, 0);
        CommonFunctions.takeStockOfFigurePossibleSteps(
                field,
                whiteFigure,
                Field.WHITE_FIGURE_LEFT_VECTOR,
                Field.WHITE_FIGURE_RIGHT_VECTOR,
                figureToPossibleStepCells);

        assertEquals(figureToPossibleStepCells.get(whiteFigure),
                Arrays.asList(new Cell(0, 1)));
    }

    private static final byte[][] WHITE_FIGURE_CAN_GO_RIGHT_FORWARD_FIELD = {
            {0, 3},
            {2, 0},
            {0, 1}
    };

    @Test
    public void takeStockOfFigurePossibleSteps_whiteFigureCanGoRightForwardTest() throws Exception {
        Map<Cell, List<Cell>> figureToPossibleStepCells = new HashMap<>();
        field.magicUpdate(WHITE_FIGURE_CAN_GO_RIGHT_FORWARD_FIELD);
        Cell whiteFigure = new Cell(1, 0);
        CommonFunctions.takeStockOfFigurePossibleSteps(
                field,
                whiteFigure,
                Field.WHITE_FIGURE_LEFT_VECTOR,
                Field.WHITE_FIGURE_RIGHT_VECTOR,
                figureToPossibleStepCells);

        assertEquals(figureToPossibleStepCells.get(whiteFigure),
                Arrays.asList(new Cell(2, 1)));
    }
    private static final byte[][] WHITE_FIGURE_CAN_GO_LEFT_OR_RIGHT_FORWARD_FIELD = {
            {0, 1},
            {2, 0},
            {0, 1}
    };

    @Test
    public void takeStockOfFigurePossibleSteps_whiteFigureCanGoLeftOrRightForwardTest() throws Exception {
        Map<Cell, List<Cell>> figureToPossibleStepCells = new HashMap<>();
        field.magicUpdate(WHITE_FIGURE_CAN_GO_LEFT_OR_RIGHT_FORWARD_FIELD);
        Cell whiteFigure = new Cell(1, 0);
        CommonFunctions.takeStockOfFigurePossibleSteps(
                field,
                whiteFigure,
                Field.WHITE_FIGURE_LEFT_VECTOR,
                Field.WHITE_FIGURE_RIGHT_VECTOR,
                figureToPossibleStepCells);

        List<Cell> calculatedList = figureToPossibleStepCells.get(whiteFigure);
        List<Cell> expectedList = Arrays.asList(new Cell(0, 1), new Cell(2, 1));
        assertEquals(calculatedList, expectedList);
    }

    private static final byte[][] WHITE_FIGURE_CANT_GO_FORWARD_FIELD = {
            {0, 3},
            {2, 0},
            {0, 3}
    };

    @Test
    public void takeStockOfFigurePossibleSteps_whiteFigureCantGoForwardTest() throws Exception {
        Map<Cell, List<Cell>> figureToPossibleStepCells = new HashMap<>();
        field.magicUpdate(WHITE_FIGURE_CANT_GO_FORWARD_FIELD);
        Cell whiteFigure = new Cell(1, 0);
        CommonFunctions.takeStockOfFigurePossibleSteps(
                field,
                whiteFigure,
                Field.WHITE_FIGURE_LEFT_VECTOR,
                Field.WHITE_FIGURE_RIGHT_VECTOR,
                figureToPossibleStepCells);

        assertEquals(figureToPossibleStepCells.get(whiteFigure), Collections.emptyList());
    }

    private static final byte[][] WHITE_FIGURE_CANT_GO_BACK_FIELD = {
            {1, 0},
            {0, 2},
            {1, 0}
    };

    @Test
    public void takeStockOfFigurePossibleSteps_whiteFigureCantGoBackTest() throws Exception {
        Map<Cell, List<Cell>> figureToPossibleStepCells = new HashMap<>();
        field.magicUpdate(WHITE_FIGURE_CANT_GO_BACK_FIELD);
        Cell whiteFigure = new Cell(1, 0);
        CommonFunctions.takeStockOfFigurePossibleSteps(
                field,
                whiteFigure,
                Field.WHITE_FIGURE_LEFT_VECTOR,
                Field.WHITE_FIGURE_RIGHT_VECTOR,
                figureToPossibleStepCells);

        assertEquals(figureToPossibleStepCells.get(whiteFigure), Collections.emptyList());
    }

    private static final byte[][] WHITE_FIGURE_CAN_ATTACK_BLACK_FIGURE_FORWARD_FIELD = {
            {1, 0, 1},
            {0, 4, 0},
            {2, 0, 1}
    };

    @Test
    public void takeStockOfPossibleAttacks_whiteFigureCanAttackBlackFigureForwardTest() throws Exception {
        Map<Cell, List<Cell>> killerToVictimsMap = new HashMap<>();
        field.magicUpdate(WHITE_FIGURE_CAN_ATTACK_BLACK_FIGURE_FORWARD_FIELD);
        Cell whiteFigure = new Cell(2, 0);
        List<Cell> possibleVictims = Collections.singletonList(new Cell(1,1));
        CommonFunctions.takeStockOfPossibleAttacks(
                whiteFigure,
                possibleVictims,
                analyzer::isReachableEnemyForWhiteFigure,
                analyzer::isAttackPossible,
                killerToVictimsMap);

        assertEquals(killerToVictimsMap.get(whiteFigure), possibleVictims);
    }

    private static final byte[][] WHITE_FIGURE_CANT_ATTACK_BLACK_FIGURE_FORWARD_FIELD = {
            {1, 0, 3},
            {0, 4, 0},
            {2, 0, 1}
    };

    @Test
    public void takeStockOfPossibleAttacks_whiteFigureCantAttackBlackFigureForwardTest() throws Exception {
        Map<Cell, List<Cell>> killerToVictimsMap = new HashMap<>();
        field.magicUpdate(WHITE_FIGURE_CANT_ATTACK_BLACK_FIGURE_FORWARD_FIELD);
        Cell whiteFigure = new Cell(2, 0);
        List<Cell> possibleVictims = Collections.singletonList(new Cell(1,1));
        CommonFunctions.takeStockOfPossibleAttacks(
                whiteFigure,
                possibleVictims,
                analyzer::isReachableEnemyForWhiteFigure,
                analyzer::isAttackPossible,
                killerToVictimsMap);

        assertEquals(killerToVictimsMap.get(whiteFigure), null);
    }

    private static final byte[][] WHITE_FIGURE_CAN_ATTACK_BLACK_FIGURE_BACKWARD_FIELD = {
            {1, 0, 1},
            {0, 4, 0},
            {1, 0, 2}
    };

    @Test
    public void takeStockOfPossibleAttacks_whiteFigureCanAttackBlackFigureBackwardTest() throws Exception {
        Map<Cell, List<Cell>> killerToVictimsMap = new HashMap<>();
        field.magicUpdate(WHITE_FIGURE_CAN_ATTACK_BLACK_FIGURE_BACKWARD_FIELD);
        Cell whiteFigure = new Cell(2, 2);
        List<Cell> possibleVictims = Collections.singletonList(new Cell(1,1));
        CommonFunctions.takeStockOfPossibleAttacks(
                whiteFigure,
                possibleVictims,
                analyzer::isReachableEnemyForWhiteFigure,
                analyzer::isAttackPossible,
                killerToVictimsMap);

        assertEquals(killerToVictimsMap.get(whiteFigure), possibleVictims);
    }

    private static final byte[][] WHITE_FIGURE_CANT_ATTACK_BLACK_FIGURE_BACKWARD_FIELD = {
            {3, 0, 1},
            {0, 4, 0},
            {1, 0, 2}
    };

    @Test
    public void takeStockOfPossibleAttacks_whiteFigureCantAttackBlackFigureBackwardTest() throws Exception {
        Map<Cell, List<Cell>> killerToVictimsMap = new HashMap<>();
        field.magicUpdate(WHITE_FIGURE_CANT_ATTACK_BLACK_FIGURE_BACKWARD_FIELD);
        Cell whiteFigure = new Cell(2, 2);
        List<Cell> possibleVictims = Collections.singletonList(new Cell(1,1));
        CommonFunctions.takeStockOfPossibleAttacks(
                whiteFigure,
                possibleVictims,
                analyzer::isReachableEnemyForWhiteFigure,
                analyzer::isAttackPossible,
                killerToVictimsMap);

        assertEquals(killerToVictimsMap.get(whiteFigure), null);
    }

    private static final byte[][] WHITE_FIGURE_CAN_ATTACK_BLACK_QUEEN_FORWARD_FIELD = {
            {2, 0, 1},
            {0, 5, 0},
            {1, 0, 1}
    };

    @Test
    public void takeStockOfPossibleAttacks_whiteFigureCanAttackBlackQueenForwardTest() throws Exception {
        Map<Cell, List<Cell>> killerToVictimsMap = new HashMap<>();
        field.magicUpdate(WHITE_FIGURE_CAN_ATTACK_BLACK_QUEEN_FORWARD_FIELD);
        Cell whiteFigure = new Cell(0, 0);
        List<Cell> possibleVictims = Collections.singletonList(new Cell(1,1));
        CommonFunctions.takeStockOfPossibleAttacks(
                whiteFigure,
                possibleVictims,
                analyzer::isReachableEnemyForWhiteFigure,
                analyzer::isAttackPossible,
                killerToVictimsMap);

        assertEquals(killerToVictimsMap.get(whiteFigure), possibleVictims);
    }

    private static final byte[][] WHITE_FIGURE_CANT_ATTACK_BLACK_QUEEN_FORWARD_FIELD = {
            {2, 0, 1},
            {0, 5, 0},
            {1, 0, 4}
    };

    @Test
    public void takeStockOfPossibleAttacks_whiteFigureCantAttackBlackQueenForwardTest() throws Exception {
        Map<Cell, List<Cell>> killerToVictimsMap = new HashMap<>();
        field.magicUpdate(WHITE_FIGURE_CANT_ATTACK_BLACK_QUEEN_FORWARD_FIELD);
        Cell whiteFigure = new Cell(0, 0);
        List<Cell> possibleVictims = Collections.singletonList(new Cell(1,1));
        CommonFunctions.takeStockOfPossibleAttacks(
                whiteFigure,
                possibleVictims,
                analyzer::isReachableEnemyForWhiteFigure,
                analyzer::isAttackPossible,
                killerToVictimsMap);

        assertEquals(killerToVictimsMap.get(whiteFigure), null);
    }

    private static final byte[][] WHITE_FIGURE_CAN_ATTACK_BLACK_QUEEN_BACKWARD_FIELD = {
            {1, 0, 2},
            {0, 5, 0},
            {1, 0, 1}
    };

    @Test
    public void takeStockOfPossibleAttacks_whiteFigureCanAttackBlackQueenBackwardTest() throws Exception {
        Map<Cell, List<Cell>> killerToVictimsMap = new HashMap<>();
        field.magicUpdate(WHITE_FIGURE_CAN_ATTACK_BLACK_QUEEN_BACKWARD_FIELD);
        Cell whiteFigure = new Cell(0, 2);
        List<Cell> possibleVictims = Collections.singletonList(new Cell(1,1));
        CommonFunctions.takeStockOfPossibleAttacks(
                whiteFigure,
                possibleVictims,
                analyzer::isReachableEnemyForWhiteFigure,
                analyzer::isAttackPossible,
                killerToVictimsMap);

        assertEquals(killerToVictimsMap.get(whiteFigure), possibleVictims);
    }

    private static final byte[][] WHITE_FIGURE_CANT_ATTACK_BLACK_QUEEN_BACKWARD_FIELD = {
            {1, 0, 2},
            {0, 5, 0},
            {3, 0, 1}
    };

    @Test
    public void takeStockOfPossibleAttacks_whiteFigureCantAttackBlackQueenBackwardTest() throws Exception {
        Map<Cell, List<Cell>> killerToVictimsMap = new HashMap<>();
        field.magicUpdate(WHITE_FIGURE_CANT_ATTACK_BLACK_QUEEN_BACKWARD_FIELD);
        Cell whiteFigure = new Cell(0, 2);
        List<Cell> possibleVictims = Collections.singletonList(new Cell(1,1));
        CommonFunctions.takeStockOfPossibleAttacks(
                whiteFigure,
                possibleVictims,
                analyzer::isReachableEnemyForWhiteFigure,
                analyzer::isAttackPossible,
                killerToVictimsMap);

        assertEquals(killerToVictimsMap.get(whiteFigure), null);
    }

    //White Queen possible game situations
    private static final byte[][] WHITE_QUEEN_CAN_GO_FORWARD_FIELD = {
            {1, 0, 2},
            {0, 1, 0},
            {3, 0, 1}
    };

    @Test
    public void takeStockOfQueenPossibleSteps_whiteQueenCanGoForwardTest() throws Exception {
        Map<Cell, List<Cell>> figureToPossibleStepCells = new HashMap<>();
        field.magicUpdate(WHITE_QUEEN_CAN_GO_FORWARD_FIELD);
        Cell whiteQueen = new Cell(2, 0);
        CommonFunctions.takeStockOfQueenPossibleSteps(
                field,
                whiteQueen,
                Field.WHITE_FIGURE_LEFT_VECTOR,
                Field.WHITE_FIGURE_RIGHT_VECTOR,
                Field.BLACK_FIGURE_LEFT_VECTOR,
                Field.BLACK_FIGURE_RIGHT_VECTOR,
                figureToPossibleStepCells);

        assertEquals(figureToPossibleStepCells.get(whiteQueen),
                Collections.singletonList(new Cell(1,1)));
    }

    private static final byte[][] WHITE_QUEEN_CANT_GO_FORWARD_FIELD = {
            {1, 0, 1},
            {0, 2, 0},
            {3, 0, 1}
    };

    @Test
    public void takeStockOfQueenPossibleSteps_whiteQueenCantGoForwardTest() throws Exception {
        Map<Cell, List<Cell>> figureToPossibleStepCells = new HashMap<>();
        field.magicUpdate(WHITE_QUEEN_CANT_GO_FORWARD_FIELD);
        Cell whiteQueen = new Cell(2, 0);
        CommonFunctions.takeStockOfQueenPossibleSteps(
                field,
                whiteQueen,
                Field.WHITE_FIGURE_LEFT_VECTOR,
                Field.WHITE_FIGURE_RIGHT_VECTOR,
                Field.BLACK_FIGURE_LEFT_VECTOR,
                Field.BLACK_FIGURE_RIGHT_VECTOR,
                figureToPossibleStepCells);

        assertEquals(figureToPossibleStepCells.get(whiteQueen),Collections.emptyList());
    }

    private static final byte[][] WHITE_QUEEN_CAN_ATTACK_BLACK_FIGURE_FIELD = {
            {3, 0, 1, 0, 1},
            {0, 1, 0, 1, 0},
            {1, 0, 1, 0, 1},
            {0, 1, 0, 4, 0},
            {1, 0, 1, 0, 1}
    };

    @Test
    public void takeStockOfPossibleAttacks_whiteQueenCanAttackBlackFigureTest() throws Exception {
        Map<Cell, List<Cell>> killerToVictimsMap = new HashMap<>();
        field.magicUpdate(WHITE_QUEEN_CAN_ATTACK_BLACK_FIGURE_FIELD);
        Cell whiteQueen = new Cell(0, 0);
        List<Cell> possibleVictims = Collections.singletonList(new Cell(3,3));
        CommonFunctions.takeStockOfPossibleAttacks(
                whiteQueen,
                possibleVictims,
                analyzer::isReachableEnemyForWhiteQueen,
                analyzer::isAttackPossible,
                killerToVictimsMap);

        assertEquals(killerToVictimsMap.get(whiteQueen), possibleVictims);
    }

    private static final byte[][] WHITE_QUEEN_CANT_ATTACK_BLACK_FIGURE_FIELD = {
            {3, 0, 1, 0, 1},
            {0, 1, 0, 1, 0},
            {1, 0, 1, 0, 1},
            {0, 1, 0, 4, 0},
            {1, 0, 1, 0, 5}
    };

    @Test
    public void takeStockOfPossibleAttacks_whiteQueenCantAttackBlackFigureTest() throws Exception {
        Map<Cell, List<Cell>> killerToVictimsMap = new HashMap<>();
        field.magicUpdate(WHITE_QUEEN_CANT_ATTACK_BLACK_FIGURE_FIELD);
        Cell whiteQueen = new Cell(0, 0);
        List<Cell> possibleVictims = Collections.singletonList(new Cell(3,3));
        CommonFunctions.takeStockOfPossibleAttacks(
                whiteQueen,
                possibleVictims,
                analyzer::isReachableEnemyForWhiteQueen,
                analyzer::isAttackPossible,
                killerToVictimsMap);

        assertEquals(killerToVictimsMap.get(whiteQueen), null);
    }

    private static final byte[][] WHITE_QUEEN_CAN_ATTACK_BLACK_QUEEN_FIELD = {
            {1, 0, 1, 0, 1},
            {0, 1, 0, 5, 0},
            {1, 0, 1, 0, 1},
            {0, 1, 0, 4, 0},
            {3, 0, 1, 0, 1}
    };

    @Test
    public void takeStockOfPossibleAttacks_whiteQueenCanAttackBlackQueenTest() throws Exception {
        Map<Cell, List<Cell>> killerToVictimsMap = new HashMap<>();
        field.magicUpdate(WHITE_QUEEN_CAN_ATTACK_BLACK_QUEEN_FIELD);
        Cell whiteQueen = new Cell(4, 0);
        List<Cell> possibleVictims = Collections.singletonList(new Cell(1,3));
        CommonFunctions.takeStockOfPossibleAttacks(
                whiteQueen,
                possibleVictims,
                analyzer::isReachableEnemyForWhiteQueen,
                analyzer::isAttackPossible,
                killerToVictimsMap);

        assertEquals(killerToVictimsMap.get(whiteQueen), possibleVictims);
    }

    private static final byte[][] WHITE_QUEEN_CANT_ATTACK_BLACK_QUEEN_FIELD = {
            {1, 0, 1, 0, 4},
            {0, 1, 0, 5, 0},
            {1, 0, 1, 0, 1},
            {0, 1, 0, 4, 0},
            {3, 0, 1, 0, 1}
    };

    @Test
    public void takeStockOfPossibleAttacks_whiteQueenCantAttackBlackQueenTest() throws Exception {
        Map<Cell, List<Cell>> killerToVictimsMap = new HashMap<>();
        field.magicUpdate(WHITE_QUEEN_CANT_ATTACK_BLACK_QUEEN_FIELD);
        Cell whiteQueen = new Cell(0, 0);
        List<Cell> possibleVictims = Collections.singletonList(new Cell(3,3));
        CommonFunctions.takeStockOfPossibleAttacks(
                whiteQueen,
                possibleVictims,
                analyzer::isReachableEnemyForWhiteQueen,
                analyzer::isAttackPossible,
                killerToVictimsMap);

        assertEquals(killerToVictimsMap.get(whiteQueen), null);
    }

    private static final byte[][] WHITE_QUEEN_CANT_ATTACK_BLACK_QUEEN_AND_FIGURE_FIELD = {
            {1, 0, 1, 0, 1},
            {0, 1, 0, 5, 0},
            {1, 0, 1, 0, 3},
            {0, 1, 0, 4, 0},
            {1, 0, 1, 0, 1}
    };

    @Test
    public void takeStockOfPossibleAttacks_whiteQueenCantAttackBlackQueenAndFigureTest() throws Exception {
        Map<Cell, List<Cell>> killerToVictimsMap = new HashMap<>();
        field.magicUpdate(WHITE_QUEEN_CANT_ATTACK_BLACK_QUEEN_AND_FIGURE_FIELD);
        Cell whiteQueen = new Cell(2, 4);
        List<Cell> possibleVictims = Arrays.asList(new Cell(1,3),
                new Cell(3,3));
        CommonFunctions.takeStockOfPossibleAttacks(
                whiteQueen,
                possibleVictims,
                analyzer::isReachableEnemyForWhiteQueen,
                analyzer::isAttackPossible,
                killerToVictimsMap);

        assertEquals(killerToVictimsMap.get(whiteQueen), possibleVictims);
    }

    @Test
    public void countValuesTest() {
        assertEquals(6, CommonFunctions.countValues(CELL_ASSOCIATION_MAP));
    }


}