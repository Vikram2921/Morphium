package com.morphium.parser.ast;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.morphium.runtime.Context;

public class LiteralExpr implements Expression {
    private final Object value;

    public LiteralExpr(Object value) {
        this.value = value;
    }

    @Override
    public JsonElement evaluate(Context context) {
        if (value == null) {
            return com.google.gson.JsonNull.INSTANCE;
        } else if (value instanceof Boolean) {
            return new JsonPrimitive((Boolean) value);
        } else if (value instanceof Number) {
            return new JsonPrimitive((Number) value);
        } else if (value instanceof String) {
            return new JsonPrimitive((String) value);
        }
        throw new RuntimeException("Unknown literal type: " + value.getClass());
    }

    public Object getValue() {
        return value;
    }
}
