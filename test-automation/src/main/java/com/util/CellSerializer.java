package com.util;

import com.chess.entity.Cell;
import com.google.gson.*;

import java.lang.reflect.Type;

public class CellSerializer implements JsonSerializer<Cell> {

    @Override
    public JsonElement serialize(Cell cell, Type type, JsonSerializationContext jsonSerializationContext) {
        if (cell != null) {
            return new JsonPrimitive(cell.toString());
        }
        return null;
    }
}
