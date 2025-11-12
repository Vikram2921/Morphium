package com.morphium.runtime;

import com.morphium.parser.ast.Expression;

import java.util.List;

public class UserFunction {
    private final String name;
    private final List<String> parameters;
    private final Expression body;

    public UserFunction(String name, List<String> parameters, Expression body) {
        this.name = name;
        this.parameters = parameters;
        this.body = body;
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
