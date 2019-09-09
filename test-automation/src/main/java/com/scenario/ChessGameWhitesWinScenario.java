package com.scenario;

import com.chess.Game;
import com.chess.entity.Cell;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.util.CellSerializer;
import com.util.Step;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ChessGameWhitesWinScenario {

    private Game game;
    private Gson gson;

    @BeforeTest
    public void setupGame() {
        game = Game.newGame();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Cell.class, new CellSerializer());
        gson = gsonBuilder.create();
    }

    @DataProvider(name = "game-state-data-provider")
    public Object[][] dataProviderMethod() {
        return new Object[][] {
                {//1
                        true, //isWhitesTurn
                        new byte[][] {
                               //1  2  3  4  5  6  7  8
                                {2, 0, 2, 0, 1, 0, 4, 0}, //H
                                {0, 2, 0, 1, 0, 4, 0, 4}, //G
                                {2, 0, 2, 0, 1, 0, 4, 0}, //F
                                {0, 2, 0, 1, 0, 4, 0, 4}, //E
                                {2, 0, 2, 0, 1, 0, 4, 0}, //D
                                {0, 2, 0, 1, 0, 4, 0, 4}, //C
                                {2, 0, 2, 0, 1, 0, 4, 0}, //B
                                {0, 2, 0, 1, 0, 4, 0, 4}},//A
                               //1  2  3  4  5  6  7  8
                        "{\"{F,3}\":[\"{G,4}\",\"{E,4}\"],\"{H,3}\":[\"{G,4}\"],\"{B,3}\":[\"{C,4}\",\"{A,4}\"],\"{D,3}\":[\"{E,4}\",\"{C,4}\"]}", //whitesPossibleSteps
                        "{\"{C,6}\":[\"{B,5}\",\"{D,5}\"],\"{E,6}\":[\"{D,5}\",\"{F,5}\"],\"{G,6}\":[\"{F,5}\",\"{H,5}\"],\"{A,6}\":[\"{B,5}\"]}", //blacksPossibleSteps
                        "{}",  //whitesPossibleAttacks
                        "{}",  //blacksPossibleAttacks
                        step("F,3", "E,4") //step
                },
                {//2
                        false, //isWhitesTurn
                        new byte[][] {
                               //1  2  3  4  5  6  7  8
                                {2, 0, 2, 0, 1, 0, 4, 0}, //H
                                {0, 2, 0, 1, 0, 4, 0, 4}, //G
                                {2, 0, 1, 0, 1, 0, 4, 0}, //F
                                {0, 2, 0, 2, 0, 4, 0, 4}, //E
                                {2, 0, 2, 0, 1, 0, 4, 0}, //D
                                {0, 2, 0, 1, 0, 4, 0, 4}, //C
                                {2, 0, 2, 0, 1, 0, 4, 0}, //B
                                {0, 2, 0, 1, 0, 4, 0, 4}},//A
                               //1  2  3  4  5  6  7  8
                        "{\"{G,2}\":[\"{F,3}\"],\"{E,4}\":[\"{F,5}\",\"{D,5}\"],\"{H,3}\":[\"{G,4}\"],\"{B,3}\":[\"{C,4}\",\"{A,4}\"],\"{E,2}\":[\"{F,3}\"],\"{D,3}\":[\"{C,4}\"]}", //whitesPossibleSteps
                        "{\"{C,6}\":[\"{B,5}\",\"{D,5}\"],\"{E,6}\":[\"{D,5}\",\"{F,5}\"],\"{G,6}\":[\"{F,5}\",\"{H,5}\"],\"{A,6}\":[\"{B,5}\"]}", //blacksPossibleSteps
                        "{}",  //whitesPossibleAttacks
                        "{}",  //blacksPossibleAttacks
                        step("E,6", "D,5")  //step
                },
                {//3
                        true, //isWhitesTurn
                        new byte[][] {
                               //1  2  3  4  5  6  7  8
                                {2, 0, 2, 0, 1, 0, 4, 0}, //H
                                {0, 2, 0, 1, 0, 4, 0, 4}, //G
                                {2, 0, 1, 0, 1, 0, 4, 0}, //F
                                {0, 2, 0, 2, 0, 1, 0, 4}, //E
                                {2, 0, 2, 0, 4, 0, 4, 0}, //D
                                {0, 2, 0, 1, 0, 4, 0, 4}, //C
                                {2, 0, 2, 0, 1, 0, 4, 0}, //B
                                {0, 2, 0, 1, 0, 4, 0, 4}},//A
                               //1  2  3  4  5  6  7  8
                        "{\"{G,2}\":[\"{F,3}\"],\"{E,4}\":[\"{F,5}\"],\"{H,3}\":[\"{G,4}\"],\"{B,3}\":[\"{C,4}\",\"{A,4}\"],\"{E,2}\":[\"{F,3}\"],\"{D,3}\":[\"{C,4}\"]}", //whitesPossibleSteps
                        "{\"{D,5}\":[\"{C,4}\"],\"{C,6}\":[\"{B,5}\"],\"{D,7}\":[\"{E,6}\"],\"{G,6}\":[\"{F,5}\",\"{H,5}\"],\"{F,7}\":[\"{E,6}\"],\"{A,6}\":[\"{B,5}\"]}", //blacksPossibleSteps
                        "{}",  //whitesPossibleAttacks
                        "{\"{D,5}\":[\"{E,4}\"]}",  //blacksPossibleAttacks
                        step("D,5", "E,4")  //step
                },
                {//4
                        false, //isWhitesTurn
                        new byte[][] {
                               //1  2  3  4  5  6  7  8
                                {2, 0, 2, 0, 1, 0, 4, 0}, //H
                                {0, 2, 0, 1, 0, 4, 0, 4}, //G
                                {2, 0, 4, 0, 1, 0, 4, 0}, //F
                                {0, 2, 0, 1, 0, 1, 0, 4}, //E
                                {2, 0, 2, 0, 1, 0, 4, 0}, //D
                                {0, 2, 0, 1, 0, 4, 0, 4}, //C
                                {2, 0, 2, 0, 1, 0, 4, 0}, //B
                                {0, 2, 0, 1, 0, 4, 0, 4}},//A
                               //1  2  3  4  5  6  7  8
                        "{\"{G,2}\":[\"{F,3}\"],\"{E,4}\":[\"{F,5}\"],\"{H,3}\":[\"{G,4}\"],\"{B,3}\":[\"{C,4}\",\"{A,4}\"],\"{E,2}\":[\"{F,3}\"],\"{D,3}\":[\"{C,4}\"]}", //whitesPossibleSteps
                        "{\"{D,5}\":[\"{C,4}\"],\"{C,6}\":[\"{B,5}\"],\"{D,7}\":[\"{E,6}\"],\"{G,6}\":[\"{F,5}\",\"{H,5}\"],\"{F,7}\":[\"{E,6}\"],\"{A,6}\":[\"{B,5}\"]}", //blacksPossibleSteps
                        "{}",  //whitesPossibleAttacks
                        "{\"{D,5}\":[\"{E,4}\"]}",  //blacksPossibleAttacks
                        step("E,6", "D,5")  //step
                }
        };
    }

    @Test(priority = 10, dataProvider = "game-state-data-provider")
    public void gameStateTest(boolean isWhitesTurn,
                              byte[][] field,
                              String whitesPossibleSteps,
                              String blacksPossibleSteps,
                              String whitesPossibleAttacks,
                              String blacksPossibleAttacks,
                              Step step) {
        assertEquals(game.isWhitesTurn(), isWhitesTurn);
        assertTrue(Arrays.deepEquals(game.getField(), field), Arrays.deepToString(game.getField()));
        assertEquals(gson.toJson(game.getWhitesPossibleSteps()), whitesPossibleSteps, "whitesPossibleSteps");
        assertEquals(gson.toJson(game.getBlacksPossibleSteps()), blacksPossibleSteps, "blacksPossibleSteps");
        assertEquals(gson.toJson(game.getWhitesPossibleAttacks()), whitesPossibleAttacks, "whitesPossibleAttacks");
        assertEquals(gson.toJson(game.getBlacksPossibleAttacks()), blacksPossibleAttacks, "blacksPossibleAttacks");
        game.go(step.getFrom(), step.getTo());
    }

    private static Step step(String from, String to) {
        try {
            return new Step(Cell.fromString(from), Cell.fromString(to));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
