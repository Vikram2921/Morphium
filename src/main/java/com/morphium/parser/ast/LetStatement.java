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
        
        // If body is not null, evaluate it in the new context
        if (body != null && !(body instanceof LiteralExpr && ((LiteralExpr) body).getValue() == null)) {
            return body.evaluate(newContext);
        }
        
        // Otherwise just update the parent context
        context.define(name, val);
        return com.google.gson.JsonNull.INSTANCE;
    }
}
