package com.util;

import com.chess.entity.Cell;

public class Step {
    private Cell from;
    private Cell to;

    public Step(Cell from, Cell to) {
        this.from = from;
        this.to = to;
    }

    public Step(String from, String to) throws Exception {
        this.from = Cell.fromString(from);
        this.to = Cell.fromString(to);
    }

    public Cell getFrom() {
        return from;
    }

    public Cell getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "Step{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
