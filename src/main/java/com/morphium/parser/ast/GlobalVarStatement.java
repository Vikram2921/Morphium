package com.morphium.parser.ast;

import com.google.gson.JsonElement;
import com.morphium.runtime.Context;

public class GlobalVarStatement implements Expression {
    private final String name;
    private final Expression value;

    public GlobalVarStatement(String name, Expression value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public JsonElement evaluate(Context context) {
        JsonElement val = value.evaluate(context);
        context.defineGlobal(name, val);
        return com.google.gson.JsonNull.INSTANCE;
    }

    public String getName() {
        return name;
    }

    public Expression getValue() {
        return value;
    }
}
