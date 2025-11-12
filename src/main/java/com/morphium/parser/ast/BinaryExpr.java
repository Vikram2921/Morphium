package com.morphium.parser.ast;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.morphium.runtime.Context;

public class BinaryExpr implements Expression {
    public enum Operator {
        ADD, SUBTRACT, MULTIPLY, DIVIDE, MODULO,
        EQ, EQ_STRICT, NE, NE_STRICT, LT, LE, GT, GE,
        AND, OR, NULL_COALESCE
    }

    private final Expression left;
    private final Operator operator;
    private final Expression right;

    public BinaryExpr(Expression left, Operator operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public JsonElement evaluate(Context context) {
        switch (operator) {
            case AND:
                return evaluateAnd(context);
            case OR:
                return evaluateOr(context);
            case NULL_COALESCE:
                return evaluateNullCoalesce(context);
            default:
                JsonElement leftVal = left.evaluate(context);
                JsonElement rightVal = right.evaluate(context);
                return evaluateOperator(leftVal, rightVal);
        }
    }

    private JsonElement evaluateAnd(Context context) {
        JsonElement leftVal = left.evaluate(context);
        if (!isTruthy(leftVal)) return leftVal;
        return right.evaluate(context);
    }

    private JsonElement evaluateOr(Context context) {
        JsonElement leftVal = left.evaluate(context);
        if (isTruthy(leftVal)) return leftVal;
        return right.evaluate(context);
    }

    private JsonElement evaluateNullCoalesce(Context context) {
        JsonElement leftVal = left.evaluate(context);
        if (leftVal != null && !leftVal.isJsonNull()) return leftVal;
        return right.evaluate(context);
    }

    private JsonElement evaluateOperator(JsonElement leftVal, JsonElement rightVal) {
        switch (operator) {
            case ADD:
                if (isString(leftVal) || isString(rightVal)) {
                    return new JsonPrimitive(asString(leftVal) + asString(rightVal));
                }
                return new JsonPrimitive(asNumber(leftVal) + asNumber(rightVal));
            case SUBTRACT:
                return new JsonPrimitive(asNumber(leftVal) - asNumber(rightVal));
            case MULTIPLY:
                return new JsonPrimitive(asNumber(leftVal) * asNumber(rightVal));
            case DIVIDE:
                return new JsonPrimitive(asNumber(leftVal) / asNumber(rightVal));
            case MODULO:
                return new JsonPrimitive(asNumber(leftVal) % asNumber(rightVal));
            case EQ:
            case EQ_STRICT:
                return new JsonPrimitive(areEqual(leftVal, rightVal));
            case NE:
            case NE_STRICT:
                return new JsonPrimitive(!areEqual(leftVal, rightVal));
            case LT:
                return new JsonPrimitive(asNumber(leftVal) < asNumber(rightVal));
            case LE:
                return new JsonPrimitive(asNumber(leftVal) <= asNumber(rightVal));
            case GT:
                return new JsonPrimitive(asNumber(leftVal) > asNumber(rightVal));
            case GE:
                return new JsonPrimitive(asNumber(leftVal) >= asNumber(rightVal));
            default:
                throw new RuntimeException("Unknown operator: " + operator);
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

    private boolean areEqual(JsonElement a, JsonElement b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        if (a.isJsonNull() && b.isJsonNull()) return true;
        if (a.isJsonNull() || b.isJsonNull()) return false;
        
        if (a.isJsonPrimitive() && b.isJsonPrimitive()) {
            return a.getAsString().equals(b.getAsString());
        }
        
        return a.equals(b);
    }

    private boolean isString(JsonElement value) {
        return value != null && value.isJsonPrimitive() && value.getAsJsonPrimitive().isString();
    }

    private String asString(JsonElement value) {
        if (value == null || value.isJsonNull()) return "null";
        if (value.isJsonPrimitive()) return value.getAsString();
        return value.toString();
    }

    private double asNumber(JsonElement value) {
        if (value == null || value.isJsonNull()) return 0;
        if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isNumber()) {
            return value.getAsDouble();
        }
        if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isString()) {
            try {
                return Double.parseDouble(value.getAsString());
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }
}
