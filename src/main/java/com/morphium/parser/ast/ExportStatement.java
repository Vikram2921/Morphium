package com.morphium.parser.ast;

import com.google.gson.JsonElement;
import com.morphium.runtime.Context;

public class ExportStatement implements Expression {
    private final String name;
    private final Expression value;

    public ExportStatement(String name, Expression value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public JsonElement evaluate(Context context) {
        JsonElement result = value.evaluate(context);
        context.export(name, result);
        return result;
    }

    public String getName() {
        return name;
    }

    public Expression getValue() {
        return value;
    }
}
