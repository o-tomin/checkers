package com.chess.entity;

import com.chess.analytics.FieldStateAnalyzer;

import java.io.Serializable;

public class GameContext implements Serializable {
    private Field field;
    private FieldStateAnalyzer stateAnalyzer;
    private boolean isWhitesTurn;

    // setters
    public void setField(Field field) {
        this.field = field;
    }

    public void setWhitesTurn(boolean whitesTurn) {
        this.isWhitesTurn = whitesTurn;
    }

    public void setStateAnalyzer(FieldStateAnalyzer fieldStateAnalyzer) {
        this.stateAnalyzer = fieldStateAnalyzer;
    }

    // booleans
    public boolean isWhitesTurn() {
        return isWhitesTurn;
    }

    // getters
    public Field getField() {
        return field;
    }

    public FieldStateAnalyzer getStateAnalyzer() {
        return stateAnalyzer;
    }

}
