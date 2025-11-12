package com.morphium.parser.ast;

import com.google.gson.JsonElement;
import com.morphium.runtime.Context;

public class LetStatement implements Expression {
    private final String name;
    private final Expression value;
    private final Expression body;

    public LetStatement(String name, Expression value, Expression body) {
        this.name = name;
        this.value = value;
        this.body = body;
    }

    @Override
    public JsonElement evaluate(Context context) {
        JsonElement val = value.evaluate(context);
        Context newContext = new Context(context);
        newContext.define(name, val);
        return body.evaluate(newContext);
    }
}
