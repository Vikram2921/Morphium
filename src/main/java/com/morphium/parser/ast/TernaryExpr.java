package com.morphium.parser.ast;

import com.fasterxml.jackson.databind.JsonNode;
import com.morphium.runtime.Context;

public class TernaryExpr implements Expression {
    private final Expression condition;
    private final Expression thenExpr;
    private final Expression elseExpr;

    public TernaryExpr(Expression condition, Expression thenExpr, Expression elseExpr) {
        this.condition = condition;
        this.thenExpr = thenExpr;
        this.elseExpr = elseExpr;
    }

    @Override
    public JsonNode evaluate(Context context) {
        JsonNode condValue = condition.evaluate(context);
        if (isTruthy(condValue)) {
            return thenExpr.evaluate(context);
        } else {
            return elseExpr.evaluate(context);
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
}
