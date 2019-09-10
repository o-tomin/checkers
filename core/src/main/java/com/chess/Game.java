package com.chess;

import com.chess.analytics.FieldStateAnalyzer;
import com.chess.entity.Cell;
import com.chess.entity.Field;
import com.chess.entity.GameContext;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class Game {

    private final GameContext context;

    private Game(GameContext context) {
        this.context = context;
    }

    public static Game newGame() {
        GameContext context = new GameContext();
        Field field = new Field();
        context.setField(field);
        FieldStateAnalyzer stateAnalyzer = new FieldStateAnalyzer(field);
        stateAnalyzer.updateDataForAnalysis();
        context.setWhitesTurn(true);
        context.setStateAnalyzer(stateAnalyzer);
        return new Game(context);
    }

    public static Game savedGame(GameContext context) {
        return new Game(context);
    }

    //todo: in progress
    public void go(Cell from, Cell to) {
        FieldStateAnalyzer analyzer = context.getStateAnalyzer();
        Map<Cell, List<Cell>> cellToStepsMap = isWhitesTurn() ?
                analyzer.getWhitesPossibleSteps() : analyzer.getBlacksPossibleSteps();

        Map<Cell, List<Cell>> killerToVictimsMap = isWhitesTurn() ?
                analyzer.getWhitesPossibleAttacks() : analyzer.getBlacksPossibleAttacks();

        if (Optional.ofNullable(cellToStepsMap.get(from))
                .stream()
                .flatMap(List::stream)
                .anyMatch(cell -> Objects.equals(cell, to))) {
            releasStep(from, to);
        } else {
            List<Cell> victims = killerToVictimsMap.get(from);
            if (victims != null && !victims.isEmpty()) {

            }
        }


    }

    //booleans
    public boolean isWhitesTurn() {
        return context.isWhitesTurn();
    }

    // getters
    public GameContext getContext() {
        return context;
    }

    public byte[][] getField() {
        return context.getField().cloneField();
    }

    public Map<Cell, List<Cell>> getWhitesPossibleAttacks() {
        return context.getStateAnalyzer().getWhitesPossibleAttacks();
    }

    public Map<Cell, List<Cell>> getBlacksPossibleAttacks() {
        return context.getStateAnalyzer().getBlacksPossibleAttacks();
    }

    public Map<Cell, List<Cell>> getBlacksPossibleSteps() {
        return context.getStateAnalyzer().getBlacksPossibleSteps();
    }

    public Map<Cell, List<Cell>> getWhitesPossibleSteps() {
        return context.getStateAnalyzer().getWhitesPossibleSteps();
    }

    public List<Cell> getWhiteFigures() {
        return context.getStateAnalyzer().getWhiteFigures();
    }

    public List<Cell> getWhiteQueens() {
        return context.getStateAnalyzer().getWhiteQueens();
    }

    public List<Cell> getBlackFigures() {
        return context.getStateAnalyzer().getBlackFigures();
    }

    public List<Cell> getBlackQueens() {
        return context.getStateAnalyzer().getBlackQueens();
    }

    private void releasStep(Cell from, Cell to) {
        context.getField().moveFigureToEmptyBlackCell(from, to);
        context.setWhitesTurn(!isWhitesTurn());
        context.getStateAnalyzer().updateDataForAnalysis();
    }
}
