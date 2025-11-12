package com.morphium.parser.ast;

import com.google.gson.JsonElement;
import com.morphium.runtime.Context;

import java.util.ArrayList;
import java.util.List;

public class BlockExpr implements Expression {
    private final List<Expression> expressions;

    public BlockExpr() {
        this.expressions = new ArrayList<>();
    }

    public void addExpression(Expression expr) {
        expressions.add(expr);
    }

    @Override
    public JsonElement evaluate(Context context) {
        JsonElement result = null;
        for (Expression expr : expressions) {
            result = expr.evaluate(context);
        }
        return result;
    }

    public List<Expression> getExpressions() {
        return expressions;
    }
}
