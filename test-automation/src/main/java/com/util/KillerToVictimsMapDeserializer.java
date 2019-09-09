package com.util;

import com.chess.entity.Cell;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KillerToVictimsMapDeserializer implements JsonDeserializer<Map<Cell, List<Cell>>> {
    private Gson gson = new Gson();

    @Override
    public Map<Cell, List<Cell>> deserialize(JsonElement jsonElement,
                                               Type type,
                                               JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException
    {
        return jsonElement.getAsJsonObject().entrySet().stream()
                .collect(Collectors.toMap(e -> mapToCell(e.getKey()), e -> mapToCells(e.getValue().getAsJsonArray())));
    }

    private Cell mapToCell(String cell) {
        try {
            return Cell.fromString(cell);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Cell> mapToCells(JsonArray asJsonArray) {
        List<Cell> list = new ArrayList<>();
        for (JsonElement element : asJsonArray) {
            list.add(gson.fromJson(element.toString(), Cell.class));
        }
        return list;
    }
}
