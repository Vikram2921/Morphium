package com.morphium.parser.ast;

import com.google.gson.JsonElement;
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
    public JsonElement evaluate(Context context) {
        JsonElement condValue = condition.evaluate(context);
        if (isTruthy(condValue)) {
            return thenExpr.evaluate(context);
        } else {
            return elseExpr.evaluate(context);
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
}
