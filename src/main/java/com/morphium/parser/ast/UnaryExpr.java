package com.morphium.parser.ast;

import com.morphium.util.JsonUtil;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.*;
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
    public JsonNode evaluate(Context context) {
        JsonNode value = operand.evaluate(context);
        
        switch (operator) {
            case NOT:
                return JsonUtil.createPrimitive(!isTruthy(value));
            case MINUS:
                return JsonUtil.createPrimitive(-asNumber(value));
            default:
                throw new RuntimeException("Unknown unary operator: " + operator);
        }
    }

    private boolean isTruthy(JsonNode value) {
        if (value == null || value.isNull()) return false;
        if (value.isValueNode()) {
            if (value.isBoolean()) {
                return value.asBoolean();
            }
            if (value.isNumber()) {
                return value.asDouble() != 0;
            }
            if (value.isTextual()) {
                return !value.asText().isEmpty();
            }
        }
        return true;
    }

    private double asNumber(JsonNode value) {
        if (value == null || value.isNull()) return 0;
        if (value.isValueNode() && value.isNumber()) {
            return value.asDouble();
        }
        return 0;
    }
}
