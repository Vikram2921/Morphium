package com.morphium.parser.ast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.morphium.runtime.Context;

import java.util.ArrayList;
import java.util.List;

public class ArrayExpr implements Expression {
    private final List<Expression> elements;

    public ArrayExpr() {
        this.elements = new ArrayList<>();
    }

    public void addElement(Expression expr) {
        elements.add(expr);
    }

    @Override
    public JsonElement evaluate(Context context) {
        JsonArray result = new JsonArray();
        for (Expression expr : elements) {
            result.add(expr.evaluate(context));
        }
        return result;
    }

    public List<Expression> getElements() {
        return elements;
    }
}
