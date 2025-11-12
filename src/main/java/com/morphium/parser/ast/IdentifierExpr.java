package com.morphium.parser.ast;

import com.google.gson.JsonElement;
import com.morphium.runtime.Context;

public class IdentifierExpr implements Expression {
    private final String name;

    public IdentifierExpr(String name) {
        this.name = name;
    }

    @Override
    public JsonElement evaluate(Context context) {
        return context.get(name);
    }

    public String getName() {
        return name;
    }
}
