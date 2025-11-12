package com.morphium.parser.ast;

import com.google.gson.JsonElement;
import com.morphium.runtime.Context;

import java.util.ArrayList;
import java.util.List;

public class FunctionDefExpr implements Expression {
    private final String name;
    private final List<String> parameters;
    private final Expression body;

    public FunctionDefExpr(String name, List<String> parameters, Expression body) {
        this.name = name;
        this.parameters = parameters;
        this.body = body;
    }

    @Override
    public JsonElement evaluate(Context context) {
        // Register the function in the context
        context.defineFunction(name, parameters, body);
        // Function definitions don't return a value
        return com.google.gson.JsonNull.INSTANCE;
    }

    public String getName() {
        return name;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public Expression getBody() {
        return body;
    }
}
