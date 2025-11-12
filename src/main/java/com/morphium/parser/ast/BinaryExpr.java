package com.morphium.parser.ast;

import com.morphium.util.JsonUtil;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.*;
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
    public JsonNode evaluate(Context context) {
        switch (operator) {
            case AND:
                return evaluateAnd(context);
            case OR:
                return evaluateOr(context);
            case NULL_COALESCE:
                return evaluateNullCoalesce(context);
            default:
                JsonNode leftVal = left.evaluate(context);
                JsonNode rightVal = right.evaluate(context);
                return evaluateOperator(leftVal, rightVal);
        }
    }

    private JsonNode evaluateAnd(Context context) {
        JsonNode leftVal = left.evaluate(context);
        if (!isTruthy(leftVal)) return leftVal;
        return right.evaluate(context);
    }

    private JsonNode evaluateOr(Context context) {
        JsonNode leftVal = left.evaluate(context);
        if (isTruthy(leftVal)) return leftVal;
        return right.evaluate(context);
    }

    private JsonNode evaluateNullCoalesce(Context context) {
        JsonNode leftVal = left.evaluate(context);
        if (leftVal != null && !leftVal.isNull()) return leftVal;
        return right.evaluate(context);
    }

    private JsonNode evaluateOperator(JsonNode leftVal, JsonNode rightVal) {
        switch (operator) {
            case ADD:
                if (isString(leftVal) || isString(rightVal)) {
                    return JsonUtil.createPrimitive(asString(leftVal) + asString(rightVal));
                }
                return JsonUtil.createPrimitive(asNumber(leftVal) + asNumber(rightVal));
            case SUBTRACT:
                return JsonUtil.createPrimitive(asNumber(leftVal) - asNumber(rightVal));
            case MULTIPLY:
                return JsonUtil.createPrimitive(asNumber(leftVal) * asNumber(rightVal));
            case DIVIDE:
                return JsonUtil.createPrimitive(asNumber(leftVal) / asNumber(rightVal));
            case MODULO:
                return JsonUtil.createPrimitive(asNumber(leftVal) % asNumber(rightVal));
            case EQ:
            case EQ_STRICT:
                return JsonUtil.createPrimitive(areEqual(leftVal, rightVal));
            case NE:
            case NE_STRICT:
                return JsonUtil.createPrimitive(!areEqual(leftVal, rightVal));
            case LT:
                return JsonUtil.createPrimitive(asNumber(leftVal) < asNumber(rightVal));
            case LE:
                return JsonUtil.createPrimitive(asNumber(leftVal) <= asNumber(rightVal));
            case GT:
                return JsonUtil.createPrimitive(asNumber(leftVal) > asNumber(rightVal));
            case GE:
                return JsonUtil.createPrimitive(asNumber(leftVal) >= asNumber(rightVal));
            default:
                throw new RuntimeException("Unknown operator: " + operator);
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

    private boolean areEqual(JsonNode a, JsonNode b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        if (a.isNull() && b.isNull()) return true;
        if (a.isNull() || b.isNull()) return false;
        
        if (a.isValueNode() && b.isValueNode()) {
            return a.asText().equals(b.asText());
        }
        
        return a.equals(b);
    }

    private boolean isString(JsonNode value) {
        return value != null && value.isValueNode() && value.isTextual();
    }

    private String asString(JsonNode value) {
        if (value == null || value.isNull()) return "null";
        if (value.isValueNode()) return value.asText();
        return value.toString();
    }

    private double asNumber(JsonNode value) {
        if (value == null || value.isNull()) return 0;
        if (value.isValueNode() && value.isNumber()) {
            return value.asDouble();
        }
        if (value.isValueNode() && value.isTextual()) {
            try {
                return Double.parseDouble(value.asText());
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }
}
