package com.morphium.parser.ast;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.morphium.runtime.Context;

public class UnaryExpr implements Expression {
    public enum Operator {
        NOT, MINUS
    }

    private final Operator operator;
    private final Expression operand;

    public UnaryExpr(Operator operator, Expression operand) {
        this.operator = operator;
        this.operand = operand;
    }

    @Override
    public JsonElement evaluate(Context context) {
        JsonElement value = operand.evaluate(context);
        
        switch (operator) {
            case NOT:
                return new JsonPrimitive(!isTruthy(value));
            case MINUS:
                return new JsonPrimitive(-asNumber(value));
            default:
                throw new RuntimeException("Unknown unary operator: " + operator);
        }
    }

    private boolean isTruthy(JsonElement value) {
        if (value == null || value.isJsonNull()) return false;
        if (value.isJsonPrimitive()) {
            if (value.getAsJsonPrimitive().isBoolean()) {
                return value.getAsBoolean();
            }
            if (value.getAsJsonPrimitive().isNumber()) {
                return value.getAsDouble() != 0;
            }
            if (value.getAsJsonPrimitive().isString()) {
                return !value.getAsString().isEmpty();
            }
        }
        return true;
    }

    private double asNumber(JsonElement value) {
        if (value == null || value.isJsonNull()) return 0;
        if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isNumber()) {
            return value.getAsDouble();
        }
        return 0;
    }
}
