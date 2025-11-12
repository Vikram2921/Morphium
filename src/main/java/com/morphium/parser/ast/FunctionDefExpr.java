package com.morphium.parser.ast;

import com.fasterxml.jackson.databind.node.*;

import com.fasterxml.jackson.databind.JsonNode;
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
    public JsonNode evaluate(Context context) {
        // Register the function in the context
        context.defineFunction(name, parameters, body);
        // Function definitions don't return a value
        return com.fasterxml.jackson.databind.node.NullNode.getInstance();
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
