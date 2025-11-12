package com.morphium.parser.ast;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.*;
import com.morphium.runtime.Context;

public class LiteralExpr implements Expression {
    private final Object value;

    public LiteralExpr(Object value) {
        this.value = value;
    }

    @Override
    public JsonNode evaluate(Context context) {
        if (value == null) {
            return NullNode.getInstance();
        } else if (value instanceof Boolean) {
            return BooleanNode.valueOf((Boolean) value);
        } else if (value instanceof Number) {
            if (value instanceof Integer || value instanceof Long) {
                return IntNode.valueOf(((Number) value).intValue());
            }
            return DoubleNode.valueOf(((Number) value).doubleValue());
        } else if (value instanceof String) {
            return TextNode.valueOf((String) value);
        }
        throw new RuntimeException("Unknown literal type: " + value.getClass());
    }

    public Object getValue() {
        return value;
    }
}
