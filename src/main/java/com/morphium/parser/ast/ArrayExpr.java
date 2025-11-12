package com.morphium.parser.ast;

import com.morphium.util.JsonUtil;

import com.fasterxml.jackson.databind.node.*;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.JsonNode;
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
    public JsonNode evaluate(Context context) {
        ArrayNode result = JsonUtil.createArray();
        int size = elements.size();
        if (size > 0 && result instanceof com.fasterxml.jackson.databind.node.ArrayNode) {
            // Pre-allocate capacity hint - ArrayNode will grow efficiently
        }
        for (Expression expr : elements) {
            result.add(expr.evaluate(context));
        }
        return result;
    }

    public List<Expression> getElements() {
        return elements;
    }
}
